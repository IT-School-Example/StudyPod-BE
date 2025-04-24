package com.itschool.study_pod.repository;

import com.itschool.study_pod.entity.StudyGroup;
import com.itschool.study_pod.entity.SubjectArea;
import com.itschool.study_pod.enumclass.MeetingMethod;
import com.itschool.study_pod.enumclass.RecruitmentStatus;
import com.itschool.study_pod.enumclass.Subject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class StudyGroupRepositoryTest {

    @PersistenceContext
    private EntityManager em;

    private SubjectArea subjectArea;

    @BeforeEach
    void setUp() {
        subjectArea = SubjectArea.builder()
                .subject(Subject.IT)
                .build();
        em.persist(subjectArea);
    }

    @Test
    @DisplayName("스터디그룹 저장 테스트")
    void saveStudyGroup() {
        StudyGroup group = StudyGroup.builder()
                .title("자바 스터디")
                .maxMembers(5)
                .meetingMethod(MeetingMethod.ONLINE)
                .recruitmentStatus(RecruitmentStatus.RECRUITING)
                .subjectArea(subjectArea)
                .build();

        em.persist(group);
        em.flush();
        em.clear();

        StudyGroup found = em.find(StudyGroup.class, group.getId());

        assertThat(found).isNotNull();
        assertThat(found.getTitle()).isEqualTo("자바 스터디");
    }

    @Test
    @DisplayName("스터디그룹 전체 조회 테스트")
    void findAllStudyGroups() {
        em.persist(StudyGroup.builder()
                .title("스터디 A")
                .maxMembers(3)
                .meetingMethod(MeetingMethod.OFFLINE)
                .recruitmentStatus(RecruitmentStatus.RECRUITING)
                .subjectArea(subjectArea)
                .build());

        em.persist(StudyGroup.builder()
                .title("스터디 B")
                .maxMembers(6)
                .meetingMethod(MeetingMethod.ONLINE)
                .recruitmentStatus(RecruitmentStatus.CLOSED)
                .subjectArea(subjectArea)
                .build());

        List<StudyGroup> groups = em.createQuery("SELECT sg FROM StudyGroup sg", StudyGroup.class)
                .getResultList();

        assertThat(groups).hasSize(2);
    }
}
