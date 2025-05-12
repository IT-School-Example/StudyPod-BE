package com.itschool.study_pod.repository;

import com.itschool.study_pod.StudyPodApplicationTests;
import com.itschool.study_pod.entity.Introduce;
import com.itschool.study_pod.entity.StudyGroup;
import com.itschool.study_pod.entity.SubjectArea;
import com.itschool.study_pod.entity.User;
import com.itschool.study_pod.enumclass.AccountRole;
import com.itschool.study_pod.enumclass.MeetingMethod;
import com.itschool.study_pod.enumclass.RecruitmentStatus;
import com.itschool.study_pod.enumclass.Subject;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
class IntroduceRepositoryTest extends StudyPodApplicationTests {

    @Autowired
    private IntroduceRepository introduceRepository;

    @Autowired
    private StudyGroupRepository studyGroupRepository;

    @Autowired
    private SubjectAreaRepository subjectAreaRepository;

    private StudyGroup savedStudyGroup;

    private SubjectArea savedSubject;

    @BeforeEach
    public void beforeSetUp() {
        SubjectArea subjectArea = SubjectArea.builder()
                .subject(Subject.IT)
                .build();

        savedSubject = subjectAreaRepository.save(subjectArea);

        // 수정 해야함
        savedStudyGroup = studyGroupRepository.save(
                StudyGroup.builder()
                        .title("자바 스터디")
                        .maxMembers(5)
                        .meetingMethod(MeetingMethod.ONLINE)
                        .recruitmentStatus(RecruitmentStatus.RECRUITING)
                        .subjectArea(savedSubject)
                        .keywords(Set.of("키워드1", "키워드2"))
                        .build()
        );
    }

    @AfterEach
    public void afterCleanUp() {
        introduceRepository.deleteAll();
        studyGroupRepository.deleteAll(); // 수정 해야함
        subjectAreaRepository.deleteAll();
    }

    @Test
    @DisplayName("저장 테스트")
    void create() {

        Introduce entity = Introduce.builder()
                .content("내용")
                .studyGroup(savedStudyGroup)
                .isPosted(true)
                .build();

        Introduce savedEntity = introduceRepository.save(entity);

        System.out.println("entity : " + entity);
        System.out.println("savedEntity : " + savedEntity);

        assertThat(entity).isEqualTo(savedEntity);
        assertThat(savedEntity.isDeleted()).isFalse();
    }

    @Test
    @DisplayName("조회 테스트")
    void read() {

        Introduce entity = Introduce.builder()
                .content("내용")
                .studyGroup(savedStudyGroup)
                .isPosted(true)
                .build();

        Introduce savedEntity = introduceRepository.save(entity);

        Introduce findEntity = introduceRepository.findById(savedEntity.getId())
                .orElseThrow(() -> new EntityNotFoundException());

        assertThat(entity).isEqualTo(findEntity);
    }

    @Test
    @DisplayName("수정 테스트")
    void update() {

        Introduce entity = Introduce.builder()
                .content("내용")
                .studyGroup(savedStudyGroup)
                .isPosted(true)
                .build();

        Introduce savedEntity = introduceRepository.save(entity);

        Introduce findEntity = introduceRepository.findById(savedEntity.getId())
                .orElseThrow(() -> new EntityNotFoundException());

        findEntity.softDelete();
        Introduce updatedEntity = introduceRepository.save(findEntity);

        assertThat(updatedEntity.isDeleted()).isTrue();
    }

    @Test
    @DisplayName("삭제 테스트")
    void delete() {
        long beforeCount = introduceRepository.count();

        Introduce entity = Introduce.builder()
                .content("내용")
                .studyGroup(savedStudyGroup)
                .isPosted(true)
                .build();

        Introduce savedEntity = introduceRepository.save(entity);

        introduceRepository.findById(savedEntity.getId())
                .ifPresent(introduceRepository::delete);

        long afterCount = introduceRepository.count();

        assertThat(afterCount).isEqualTo(beforeCount);
    }
}