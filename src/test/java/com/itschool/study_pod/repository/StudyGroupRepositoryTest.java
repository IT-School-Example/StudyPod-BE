package com.itschool.study_pod.repository;

import com.itschool.study_pod.StudyPodApplicationTests;
import com.itschool.study_pod.entity.StudyGroup;
import com.itschool.study_pod.entity.SubjectArea;
import com.itschool.study_pod.entity.User;
import com.itschool.study_pod.enumclass.MeetingMethod;
import com.itschool.study_pod.enumclass.RecruitmentStatus;
import com.itschool.study_pod.enumclass.Subject;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import com.itschool.study_pod.enumclass.AccountRole;

import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class StudyGroupRepositoryTest {

    @Autowired
    StudyGroupRepository studyGroupRepository;

    @Autowired
    SubjectAreaRepository subjectAreaRepository;

    @Autowired
    UserRepository userRepository;

    private SubjectArea savedSubject;
    private User savedLeader;

    @BeforeEach
    void setUp() {
        SubjectArea subjectArea = SubjectArea.builder()
                .subject(Subject.IT)
                .build();
        savedSubject = subjectAreaRepository.save(subjectArea);

        User leader = User.builder()
                .email("leader@example.com")
                .password("password")
                .role(AccountRole.ROLE_MODERATOR)
                .name("리더")
                .build();
        savedLeader = userRepository.save(leader);
    }

    @AfterEach
    void cleanUp() {
        studyGroupRepository.deleteAll();
        subjectAreaRepository.deleteAll();
        userRepository.deleteAll();
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
                .leader(savedLeader)
                .keywords(Set.of("키워드1", "키워드2"))
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

    @Test
    @DisplayName("findByIdAndIsDeletedFalse - 정상 조회 테스트")
    void findByIdAndIsDeletedFalseTest() {
        StudyGroup entity = StudyGroup.builder()
                .title("스터디1")
                .maxMembers(5)
                .meetingMethod(MeetingMethod.ONLINE)
                .recruitmentStatus(RecruitmentStatus.RECRUITING)
                .subjectArea(savedSubject)
                .leader(savedLeader)
                .build();
        StudyGroup savedEntity = studyGroupRepository.save(entity);

        StudyGroup findEntity = studyGroupRepository.findByIdAndIsDeletedFalse(savedEntity.getId())
                .orElseThrow(EntityNotFoundException::new);

        assertThat(findEntity.getId()).isEqualTo(savedEntity.getId());
    }

    @Test
    @DisplayName("findAllByLeaderIdAndIsDeletedFalse - 리더 기준 조회 테스트")
    void findAllByLeaderIdAndIsDeletedFalseTest() {
        // Given
        StudyGroup entity = StudyGroup.builder()
                .title("스터디2")
                .maxMembers(10)
                .meetingMethod(MeetingMethod.ONLINE)
                .recruitmentStatus(RecruitmentStatus.RECRUITING)
                .subjectArea(savedSubject)
                .leader(savedLeader)
                .build();
        studyGroupRepository.save(entity);

        // When
        List<StudyGroup> studyGroups = studyGroupRepository.findAllByLeader_IdAndIsDeletedFalse(savedLeader.getId());

        // Then
        assertThat(studyGroups).hasSize(1);
    }

    @Test
    @DisplayName("findAllByRecruitmentStatusAndIsDeletedFalse - 모집 상태 조회 테스트")
    void findAllByRecruitmentStatusAndIsDeletedFalseTest() {
        // Given
        StudyGroup entity = StudyGroup.builder()
                .title("스터디3")
                .maxMembers(15)
                .meetingMethod(MeetingMethod.OFFLINE)
                .recruitmentStatus(RecruitmentStatus.RECRUITING)
                .subjectArea(savedSubject)
                .leader(savedLeader)
                .build();
        studyGroupRepository.save(entity);

        // When
        List<StudyGroup> recruitingGroups = studyGroupRepository.findAllByRecruitmentStatusAndIsDeletedFalse(RecruitmentStatus.RECRUITING);

        // Then
        assertThat(recruitingGroups).isNotEmpty();
    }

    @Test
    @DisplayName("findAllByMeetingMethodAndIsDeletedFalse - 스터디 방식 조회 테스트")
    void findAllByMeetingMethodAndIsDeletedFalseTest() {
        // Given
        StudyGroup entity = StudyGroup.builder()
                .title("스터디4")
                .maxMembers(20)
                .meetingMethod(MeetingMethod.OFFLINE)
                .recruitmentStatus(RecruitmentStatus.RECRUITING)
                .subjectArea(savedSubject)
                .leader(savedLeader)
                .build();
        studyGroupRepository.save(entity);

        // When
        List<StudyGroup> offlineGroups = studyGroupRepository.findAllByMeetingMethodAndIsDeletedFalse(MeetingMethod.OFFLINE);

        // Then
        assertThat(offlineGroups).isNotEmpty();
    }

    @Test
    @DisplayName("findAllBySubjectAreaIdAndIsDeletedFalse - 주제 카테고리 조회 테스트")
    void findAllBySubjectAreaIdAndIsDeletedFalseTest() {
        // Given
        StudyGroup entity = StudyGroup.builder()
                .title("스터디5")
                .maxMembers(10)
                .meetingMethod(MeetingMethod.ONLINE)
                .recruitmentStatus(RecruitmentStatus.RECRUITING)
                .subjectArea(savedSubject)
                .leader(savedLeader)
                .build();
        studyGroupRepository.save(entity);

        // When
        List<StudyGroup> subjectGroups = studyGroupRepository.findAllBySubjectArea_IdAndIsDeletedFalse(savedSubject.getId());

        // Then
        assertThat(subjectGroups).isNotEmpty();
    }
}
