package com.itschool.study_pod.repository;

import com.itschool.study_pod.StudyPodApplicationTests;
import com.itschool.study_pod.embedable.WeeklySchedule;
import com.itschool.study_pod.entity.StudyGroup;
import com.itschool.study_pod.entity.SubjectArea;
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

import java.time.DayOfWeek;
import java.time.LocalTime;
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
                /*.weeklySchedules(Set.of(WeeklySchedule.builder()
                        .dayOfWeek(DayOfWeek.MONDAY)
                        .timeRange(TimeRange.of(9, 0, 10, 0))
                        .build()))*/
                .build();

        StudyGroup savedEntity = studyGroupRepository.save(entity);

        assertThat(savedEntity.getTitle()).isEqualTo("자바 스터디");
    }

    @Test
    @DisplayName("조회 테스트")
    void read() {
        StudyGroup entity = StudyGroup.builder()
                .title("Python 스터디")
                .maxMembers(10)
                .meetingMethod(MeetingMethod.OFFLINE)
                .recruitmentStatus(RecruitmentStatus.RECRUITING)
                .subjectArea(savedSubject)
                .keywords(Set.of("Python", "기초"))
                /*.weeklySchedules(Set.of(WeeklySchedule.builder()
                        .dayOfWeek(DayOfWeek.FRIDAY)
                        .timeRange(TimeRange.of(10, 0, 11, 0))
                        .build()))*/
                .build();

        StudyGroup saveEntity = studyGroupRepository.save(entity);

        StudyGroup findEntity = studyGroupRepository.findById(saveEntity.getId())
                .orElseThrow(() -> new EntityNotFoundException());

        assertThat(findEntity).isEqualTo(entity);
    }

    @Test
    @DisplayName("수정 테스트")
    void update() {
        StudyGroup entity = StudyGroup.builder()
                .title("초기제목")
                .maxMembers(5)
                .meetingMethod(MeetingMethod.ONLINE)
                .recruitmentStatus(RecruitmentStatus.RECRUITING)
                .subjectArea(savedSubject)
                .keywords(Set.of("초기"))
                /*.weeklySchedules(Set.of(WeeklySchedule.builder()
                        .dayOfWeek(DayOfWeek.FRIDAY)
                        .timeRange(TimeRange.of(10, 0, 11, 0))
                        .build()))*/
                .build();

        StudyGroup savedEntity = studyGroupRepository.save(entity);

        savedEntity.softDelete();

        StudyGroup updatedEntity = studyGroupRepository.save(entity);

        assertThat(updatedEntity.isDeleted()).isTrue();

    }

    @Test
    @DisplayName("삭제 테스트")
    void delete() {
        long beforeCount = studyGroupRepository.count();

        // 저장 엔티티 생성
        StudyGroup entity = StudyGroup.builder()
                .title("삭제할 스터디")
                .maxMembers(5)
                .meetingMethod(MeetingMethod.ONLINE)
                .recruitmentStatus(RecruitmentStatus.RECRUITING)
                .subjectArea(savedSubject)
                .keywords(Set.of("삭제"))
                /*.weeklySchedules(Set.of(WeeklySchedule.builder()
                        .dayOfWeek(DayOfWeek.FRIDAY)
                        .timeRange(TimeRange.of(10, 0, 11, 0))
                        .build()))*/
                .build();

        StudyGroup savedEntity = studyGroupRepository.save(entity);

        studyGroupRepository.findById(savedEntity.getId())
                .ifPresent(studyGroupRepository::delete);

        long afterCount = studyGroupRepository.count();

        assertThat(afterCount).isEqualTo(beforeCount);
    }
}
