package com.itschool.study_pod.repository;

import com.itschool.study_pod.StudyPodApplicationTests;
import com.itschool.study_pod.entity.InterestedSubject;
import com.itschool.study_pod.entity.SubjectArea;
import com.itschool.study_pod.entity.User;
import com.itschool.study_pod.enumclass.AccountRole;
import com.itschool.study_pod.enumclass.Subject;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
class InterestedSubjectRepositoryTest extends StudyPodApplicationTests {

    @Autowired
    private InterestedSubjectRepository interestedSubjectRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SubjectAreaRepository subjectAreaRepository;

    // region CRUD 테스트

    // 생성(Create) 테스트
    @Test
    void create() {
        // 사용자 생성
        User user = User.builder()
                .email("create-test@example.com")
                .password("1234")
                .role(AccountRole.ROLE_SUPER)
                .name("abc")
                .nickname("create-subject-test")
                .build();

        // 주제 영역 생성
        SubjectArea subjectArea = SubjectArea.builder()
                .subject(Subject.IT)
                .build();

        // 사용자 저장
        User savedUser = userRepository.save(user);

        // 주제 영역 저장
        SubjectArea savedSubject = subjectAreaRepository.save(subjectArea);

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
    void read() {
        // 사용자 생성
        User user = User.builder()
                .email("read-test@example.com")
                .password("1234")
                .role(AccountRole.ROLE_SUPER)
                .name("abc")
                .nickname("read-subject-test")
                .build();

        // 주제 영역 생성
        SubjectArea subjectArea = SubjectArea.builder()
                .subject(Subject.IT)
                .build();

        // 사용자 및 주제 영역 저장
        User savedUser = userRepository.save(user);
        SubjectArea savedSubject = subjectAreaRepository.save(subjectArea);

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
        // 사용자 및 주제 영역 생성 및 저장
        User user = User.builder()
                .email("update-test@example.com")
                .password("1234")
                .role(AccountRole.ROLE_SUPER)
                .name("abc")
                .nickname("update-subject-test")
                .build();
        User savedUser = userRepository.save(user);

        SubjectArea subjectArea = SubjectArea.builder()
                .subject(Subject.IT)
                .build();
        SubjectArea savedSubject = subjectAreaRepository.save(subjectArea);

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

        // 사용자 및 주제 영역 생성 및 저장
        User user = User.builder()
                .email("delete-test@example.com")
                .password("1234")
                .role(AccountRole.ROLE_SUPER)
                .name("abc")
                .nickname("delete-subject-test")
                .build();
        User savedUser = userRepository.save(user);

        SubjectArea subjectArea = SubjectArea.builder()
                .subject(Subject.IT)
                .build();
        SubjectArea savedSubject = subjectAreaRepository.save(subjectArea);

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
