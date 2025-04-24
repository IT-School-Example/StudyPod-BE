package com.itschool.study_pod.repository;

import com.itschool.study_pod.StudyPodApplicationTests;
import com.itschool.study_pod.entity.InterestedSubject;
import com.itschool.study_pod.entity.SubjectArea;
import com.itschool.study_pod.entity.User;
import com.itschool.study_pod.enumclass.AccountRole;
import com.itschool.study_pod.enumclass.Subject;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
class InterestedSubjectRepositoryTest extends StudyPodApplicationTests {

    @Autowired
    private InterestedSubjectRepository interestedSubjectRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SubjectAreaRepository subjectAreaRepository;



    private User savedUser;

    private SubjectArea savedSubject;



    @BeforeEach
    public void beforeSetUp() {

        User user = User.builder()
                .email(UUID.randomUUID() + "@subject.com")
                .password("1234")
                .role(AccountRole.ROLE_USER)
                .name("abc")
                .nickname(UUID.randomUUID().toString())
                .build();

        savedUser = userRepository.save(user);

        SubjectArea subjectArea = SubjectArea.builder()
                .subject(Subject.IT)
                .build();

        savedSubject = subjectAreaRepository.save(subjectArea);
    }

    @AfterEach
    public void afterCleanUp() {
        interestedSubjectRepository.deleteAll();
        userRepository.deleteAll();
        subjectAreaRepository.deleteAll();
    }

    // 생성(Create) 테스트
    @Test
    @DisplayName("저장 테스트")
    void create() {
        // 관심 주제 생성 및 저장
        InterestedSubject entity = InterestedSubject.builder()
                .user(savedUser)
                .subjectArea(savedSubject)
                .build();

        InterestedSubject savedEntity = interestedSubjectRepository.save(entity);

        // 영속성 컨텍스트에서 동일 엔티티로 판단되는지 검증
        assertThat(entity).isEqualTo(savedEntity);
        assertThat(savedEntity.isDeleted()).isFalse(); // 기본값이 false인지 확인
    }

    // 조회(Read) 테스트
    @Test
    @DisplayName("조회 테스트")
    void read() {

        // 관심 주제 저장
        InterestedSubject entity = InterestedSubject.builder()
                .user(savedUser)
                .subjectArea(savedSubject)
                .build();

        InterestedSubject savedEntity = interestedSubjectRepository.save(entity);

        // ID로 조회
        InterestedSubject findEntity = interestedSubjectRepository.findById(savedEntity.getId())
                .orElseThrow(EntityNotFoundException::new);

        // 동일성 검증
        assertThat(entity).isEqualTo(savedEntity);
        assertThat(findEntity).isEqualTo(savedEntity);
        assertThat(savedEntity.isDeleted()).isFalse();
    }

    // 수정(Update) 테스트
    @Test
    void update() {

        // 관심 주제 저장
        InterestedSubject entity = InterestedSubject.builder()
                .user(savedUser)
                .subjectArea(savedSubject)
                .build();
        InterestedSubject savedEntity = interestedSubjectRepository.save(entity);

        // 삭제 처리(soft delete)
        savedEntity.softDelete();

        // 변경 사항 저장
        interestedSubjectRepository.save(savedEntity);

        // 삭제 상태 확인
        assertThat(savedEntity.isDeleted()).isTrue();
    }

    // 삭제(Delete) 테스트
    @Test
    void delete() {
        // 삭제 전 개수 저장
        long beforeCount = interestedSubjectRepository.count();

        // 관심 주제 저장
        InterestedSubject entity = InterestedSubject.builder()
                .user(savedUser)
                .subjectArea(savedSubject)
                .build();
        InterestedSubject savedEntity = interestedSubjectRepository.save(entity);

        // 실제 삭제
        interestedSubjectRepository.findById(savedEntity.getId())
                .ifPresent(interestedSubjectRepository::delete);

        // 삭제 후 개수 비교
        long afterCount = interestedSubjectRepository.count();
        assertThat(beforeCount).isEqualTo(afterCount); // 테스트상 동일하게 유지됨 (삭제 전 insert, 삭제 후 다시 원래 개수)
    }
}
