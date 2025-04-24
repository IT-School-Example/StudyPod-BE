package com.itschool.study_pod.repository;

import com.itschool.study_pod.StudyPodApplicationTests;
import com.itschool.study_pod.entity.InterestedSubject;
import com.itschool.study_pod.entity.SubjectArea;
import com.itschool.study_pod.enumclass.Subject;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
@Transactional
class SubjectAreaRepositoryTest extends StudyPodApplicationTests {
    @Autowired
    private SubjectAreaRepository subjectAreaRepository;

    @Autowired
    private InterestedSubjectRepository interestedSubjectRepository;

//    @BeforeEach
//    public void BeforeCleanUp() {
//        System.out.println(this.getClass().getName() + " 테이블 전부 delete 시작");
//        subjectAreaRepository.deleteAll();
//        System.out.println(this.getClass().getName() + " 테이블 전부 delete 완료");
//    }

//    @AfterEach
//    public void AfterCleanUp() {
//        System.out.println(this.getClass().getName() + " 테이블 전부 delete 시작");
//        subjectAreaRepository.deleteAll();
//        System.out.println(this.getClass().getName() + " 테이블 전부 delete 완료");
//    }

// 오류는 없는거 같은데.. 이게 맞나..?
    // region CRUD
    @Test
    void create() {

        SubjectArea entity = SubjectArea.builder()
                .subject(Subject.IT)
                .build();

//        InterestedSubject interestedSubject = InterestedSubject.builder()
//                .subjectArea(entity) // ?????????
//                .build();


        // 관심주제
        SubjectArea savedEntity = subjectAreaRepository.save(entity);

        SubjectArea findEntity = subjectAreaRepository.findById(savedEntity.getSubjectAreaId())
                .orElseThrow(() -> new EntityNotFoundException());
        System.out.println("entity : " + entity);
        System.out.println("newEntity : " + savedEntity);

        assertThat(entity == savedEntity).isTrue();

//        assertThat(savedEntity.isDeleted()).isFalse(); // 엥....???
    }

    @Test
    void read() {
        SubjectArea entity = SubjectArea.builder()
                .subject(Subject.IT)
                .build();

//        InterestedSubject interestedSubject = InterestedSubject.builder()
//                .subjectArea(entity) // ?????????
//                .build();


        // 관심주제
        SubjectArea savedEntity = subjectAreaRepository.save(entity);

        SubjectArea findEntity = subjectAreaRepository.findById(savedEntity.getSubjectAreaId())
                .orElseThrow(() -> new EntityNotFoundException());
        System.out.println("entity : " + entity);
        System.out.println("newEntity : " + savedEntity);

        assertThat(entity == savedEntity).isTrue();

//        assertThat(savedEntity.isDeleted()).isFalse(); // 엥....???
    }

    @Test
    void update() {
        SubjectArea entity = SubjectArea.builder()
                .subject(Subject.IT)
                .build();

//        InterestedSubject interestedSubject = InterestedSubject.builder()
//                .subjectArea(entity) // ?????????
//                .build();


        // 관심주제
        SubjectArea savedEntity = subjectAreaRepository.save(entity);

        SubjectArea findEntity = subjectAreaRepository.findById(savedEntity.getSubjectAreaId())
                .orElseThrow(() -> new EntityNotFoundException());
        System.out.println("entity : " + entity);
        System.out.println("newEntity : " + savedEntity);

        assertThat(entity == savedEntity).isTrue();

//        assertThat(savedEntity.isDeleted()).isFalse(); // 엥....???
    }

    @Test
    void delete() {
        long beforeCount = subjectAreaRepository.count();
        SubjectArea entity = SubjectArea.builder()
                .subject(Subject.IT)
                .build();

//        InterestedSubject interestedSubject = InterestedSubject.builder()
//                .subjectArea(entity) // ?????????
//                .build();


        // 관심주제
        SubjectArea savedEntity = subjectAreaRepository.save(entity);

        SubjectArea findEntity = subjectAreaRepository.findById(savedEntity.getSubjectAreaId())
                .orElseThrow(() -> new EntityNotFoundException());
        System.out.println("entity : " + entity);
        System.out.println("newEntity : " + savedEntity);

        assertThat(entity == savedEntity).isTrue();

        // assertThat(savedEntity.isDeleted()).isFalse(); // 엥....???
    }

}