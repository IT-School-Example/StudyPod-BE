package com.itschool.study_pod.repository;

import com.itschool.study_pod.StudyPodApplicationTests;
import com.itschool.study_pod.dto.request.SubjectAreaRequest;
import com.itschool.study_pod.entity.SubjectArea;
import com.itschool.study_pod.enumclass.Subject;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
class SubjectAreaRepositoryTest extends StudyPodApplicationTests {

    @Autowired
    private SubjectAreaRepository subjectAreaRepository;


    @AfterEach
    public void afterCleanUp() {
        subjectAreaRepository.deleteAll();
    }

    // 생성(Create) 테스트
    @Test
    @DisplayName("저장 테스트")
    void create() {
        // 주제 영역 객체 생성
        SubjectArea entity = SubjectArea.builder()
                .subject(Subject.IT)
                .build();

        // 저장 및 저장된 객체 반환
        SubjectArea savedEntity = subjectAreaRepository.save(entity);

        // 동일성 검증 (영속성 컨텍스트 내에서는 같은 엔티티로 인식)
        assertThat(entity).isEqualTo(savedEntity);
    }

    // 조회(Read) 테스트
    @Test
    @DisplayName("조회 테스트")
    void read() {
        // 주제 영역 객체 생성 및 저장
        SubjectArea entity = SubjectArea.builder()
                .subject(Subject.IT)
                .build();
        SubjectArea savedEntity = subjectAreaRepository.save(entity);

        // ID 기반 조회
        SubjectArea findEntity = subjectAreaRepository.findById(savedEntity.getId())
                .orElseThrow(EntityNotFoundException::new);

        // 조회한 엔티티가 저장된 엔티티와 동일한지 검증
        assertThat(entity).isEqualTo(findEntity);
    }

    // 수정(Update) 테스트
    @Test
    @DisplayName("수정 테스트")
    void update() {
        // 초기 주제 영역 생성 및 저장
        SubjectArea entity = SubjectArea.builder()
                .subject(Subject.ETC) // 수정
                .build();
        SubjectArea savedEntity = subjectAreaRepository.save(entity);

        // 엔티티 다시 조회
        SubjectArea findEntity = subjectAreaRepository.findById(savedEntity.getId())
                .orElseThrow(EntityNotFoundException::new);

        // 변경할 주제 값
        Subject updatedSubject = Subject.ETC; // 수정

        // 요청 DTO 생성
        SubjectAreaRequest dto = SubjectAreaRequest.builder()
                .subject(updatedSubject)
                .build();

        // 업데이트 수행
        findEntity.update(dto);

        // 업데이트 내용 저장
        SubjectArea updatedEntity = subjectAreaRepository.save(findEntity);

        // 변경된 subject 값 검증
        assertThat(updatedEntity.getSubject()).isEqualTo(updatedSubject);
    }

    // 삭제(Delete) 테스트
    @Test
    @DisplayName("삭제 테스트")
    void delete() {
        // 삭제 전 주제 영역 레코드 수 확인
        long beforeCount = subjectAreaRepository.count();

        // 테스트용 주제 영역 생성 및 저장
        SubjectArea entity = SubjectArea.builder()
                .subject(Subject.IT)
                .build();
        SubjectArea savedEntity = subjectAreaRepository.save(entity);

        // 해당 엔티티 존재 시 삭제 수행
        subjectAreaRepository.findById(savedEntity.getId())
                .ifPresent(subjectAreaRepository::delete);

        // 삭제 후 주제 영역 수 확인
        long afterCount = subjectAreaRepository.count(); // 수정: interestedSubjectRepository -> subjectAreaRepository

        // 삭제 전후 개수가 동일해야 함 (1개 추가 후 삭제 → 원래대로)
        assertThat(beforeCount).isEqualTo(afterCount);
    }
}
