package com.itschool.study_pod.repository;

import com.itschool.study_pod.StudyPodApplicationTests;
import com.itschool.study_pod.entity.StudyGroup;
import com.itschool.study_pod.entity.SubjectArea;
import com.itschool.study_pod.enumclass.MeetingMethod;
import com.itschool.study_pod.enumclass.RecruitmentStatus;
import com.itschool.study_pod.enumclass.Subject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
class StudyGroupRepositoryTest extends StudyPodApplicationTests {

    @Autowired
    StudyGroupRepository studyGroupRepository;

    @Autowired
    SubjectAreaRepository subjectAreaRepository;



    private SubjectArea savedSubject;


    
    @BeforeEach
    public void beforeSetUp() {
        SubjectArea subjectArea = SubjectArea.builder()
                .subject(Subject.IT)
                .build();

        savedSubject = subjectAreaRepository.save(subjectArea);
    }

    @AfterEach
    public void afterCleanUp() {
        studyGroupRepository.deleteAll();
        subjectAreaRepository.deleteAll();
    }

    @Test
    @DisplayName("저장 테스트")
    void create() {
        StudyGroup entity = StudyGroup.builder()
                .title("자바 스터디")
                .maxMembers(5)
                .meetingMethod(MeetingMethod.ONLINE)
                .recruitmentStatus(RecruitmentStatus.RECRUITING)
                .subjectArea(savedSubject)
                .keywords(Set.of("키워드1", "키워드2"))
                /*.weeklySchedules((Set<WeeklySchedule>) WeeklySchedule.builder()
                        .dayOfWeek(DayOfWeek.MONDAY)
                        .timeRange(new TimeRange(LocalTime.of(9, 0), LocalTime.of(10, 0)))
                        .build())*/
                .build();

        StudyGroup savedEntity = studyGroupRepository.save(entity);

        assertThat(savedEntity.getTitle()).isEqualTo("자바 스터디");
    }

    @Test
    @DisplayName("조회 테스트")
    void read() {
    }

    @Test
    @DisplayName("수정 테스트")
    void update() {
    }

    @Test
    @DisplayName("삭제 테스트")
    void delete() {
    }
}
