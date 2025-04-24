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
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

import static org.hibernate.query.results.Builders.entity;

@Transactional
class InterestedSubjectRepositoryTest extends StudyPodApplicationTests {
    @Autowired
    private InterestedSubjectRepository interestedSubjectRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SubjectAreaRepository subjectAreaRepository;

//    @BeforeEach // 테스트 실행 전 실행하는 메서드
//    public void BeforeCleanUp() {
//        System.out.println(this.getClass().getName() + " 테이블 전부 delete 시작");
//        interestedSubjectRepository.deleteAll();
//        System.out.println(this.getClass().getName() + " 테이블 전부 delete 완료");
//    }

//    @AfterEach // 테스트 실행 후 실핼하는 메서드
//    public void AfterCleanUp() {
//        System.out.println(this.getClass().getName() + " 테이블 전부 delete 시작");
//        interestedSubjectRepository.deleteAll();
//        System.out.println(this.getClass().getName() + " 테이블 전부 delete 완료");
//    }

    // region CRUD
    @Test
    void create() {
        User user = User.builder()
                        .email("create-test@example.com")
                        .password("1234")
                        .role(AccountRole.ROLE_SUPER)
                        .name("abc")
                        .nickname("create-subject-test")
                        .build();

        SubjectArea subjectArea = SubjectArea.builder()
                .subject(Subject.IT)
                .build();

        // 사용자 저장
        User savedUser = userRepository.save(user); // 보류

        // 주제 저장
        SubjectArea savedSubject = subjectAreaRepository.save(subjectArea);

        // 관심 주제 저장
        InterestedSubject entity = InterestedSubject.builder()
                .user(savedUser)
                .subjectArea(savedSubject)
                .build();


        InterestedSubject savedEntity = interestedSubjectRepository.save(entity);

        InterestedSubject findEntity = interestedSubjectRepository.findById(savedEntity.getId())
                .orElseThrow(() -> new EntityNotFoundException());
        System.out .println("entity : " + entity); // entity도 마찬가지로 영속상태로 등록
        System.out.println("newEntity" + savedEntity); // 영속성 컨텍스트에서 관리
        // ...???
        assertThat(entity == savedEntity).isTrue(); // 결과값 true, 영속성 컨텍스트가 같은 엔티티는 같은 주소값을 반환

        assertThat(savedEntity.isDeleted()).isFalse();
    }

    @Test
    void read() {
        // 사용자 객체 생성
        User user = User.builder()
                .email("read-test@example.com")
                .password("1234")
                .role(AccountRole.ROLE_SUPER)
                .name("abc")
                .nickname("read-subject-test")
                .build();

        // 주제 영역 객체 생성
        SubjectArea subjectArea = SubjectArea.builder()
                .subject(Subject.IT)
                .build();

        // 저장 후 저장된 객체 return
        // 사용자 저장
        User savedUser = userRepository.save(user); // 보류

        // 주제 저장
        SubjectArea savedSubject = subjectAreaRepository.save(subjectArea);

        // 관심 주제 저장
        InterestedSubject entity = InterestedSubject.builder()
                .user(savedUser)
                .subjectArea(savedSubject)
                .build();


        InterestedSubject savedEntity = interestedSubjectRepository.save(entity);

        InterestedSubject findEntity = interestedSubjectRepository.findById(savedEntity.getId())
                .orElseThrow(() -> new EntityNotFoundException());
//        System.out .println("entity의 id값 : " + entity.get()); // entity도 마찬가지로 영속상태로 등록
//        System.out.println("newEntity의 id값 : " + newEntity.getInterestedSubjectId()); // 영속성 컨텍스트에서 관리
//        System.out.println(entity == newEntity); // 결과값 true, 영속성 컨텍스트가 같은 엔티티는 같은 주소값을 반환

        assertThat(entity == savedEntity).isTrue();
        assertThat(savedEntity.isDeleted()).isFalse();
    }

    @Test
    void update() {
        User user = User.builder()
                .email("update-test@example.com")
                .password("1234")
                .role(AccountRole.ROLE_SUPER)
                .name("abc")
                .nickname("update-subject-test")
                .build();

        // 주제 영역 객체 생성
        SubjectArea subjectArea = SubjectArea.builder()
                .subject(Subject.IT)
                .build();

        // 저장 후 저장된 객체 return
        // 사용자 저장
        User savedUser = userRepository.save(user); // 보류

        // 주제 저장
        SubjectArea savedSubject = subjectAreaRepository.save(subjectArea);

        // 관심 주제 저장
        InterestedSubject entity = InterestedSubject.builder()
                .user(savedUser)
                .subjectArea(savedSubject)
                .build();


        InterestedSubject savedEntity = interestedSubjectRepository.save(entity);

        InterestedSubject findEntity = interestedSubjectRepository.findById(savedEntity.getId())
                .orElseThrow(() -> new EntityNotFoundException());

        assertThat(entity == savedEntity).isTrue();
        assertThat(savedEntity.isDeleted()).isFalse();
    }

    @Test
    void delete() {
        long beforeCount = interestedSubjectRepository.count();
        User user = User.builder()
                .email("delete-test@example.com")
                .password("1234")
                .role(AccountRole.ROLE_SUPER)
                .name("abc")
                .nickname("delete-subject-test")
                .build();

        // 주제 영역 객체 생성
        SubjectArea subjectArea = SubjectArea.builder()
                .subject(Subject.IT)
                .build();

        // 저장 후 저장된 객체 return
        // 사용자 저장
        User savedUser = userRepository.save(user); // 보류

        // 주제 저장
        SubjectArea savedSubject = subjectAreaRepository.save(subjectArea);

        // 관심 주제 저장
        InterestedSubject entity = InterestedSubject.builder()
                .user(savedUser)
                .subjectArea(savedSubject)
                .build();


        InterestedSubject savedEntity = interestedSubjectRepository.save(entity);

        interestedSubjectRepository.findById(savedEntity.getId())
                .ifPresent(interestedSubjectRepository::delete);

        long afterCount = interestedSubjectRepository.count();

        assertThat(beforeCount).isEqualTo(afterCount);

    }
}