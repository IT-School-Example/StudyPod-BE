package com.itschool.study_pod.domain.enrollment.repository;

import com.itschool.study_pod.JpaRepositoryTest;
import com.itschool.study_pod.StudyPodApplicationTests;
import com.itschool.study_pod.global.config.AuditorAwareImpl;
import com.itschool.study_pod.global.config.JpaAuditingConfig;
import com.itschool.study_pod.global.embedable.WeeklySchedule;
import com.itschool.study_pod.domain.enrollment.entity.Enrollment;
import com.itschool.study_pod.domain.studygroup.entity.StudyGroup;
import com.itschool.study_pod.domain.subjectarea.entity.SubjectArea;
import com.itschool.study_pod.domain.user.entity.User;
import com.itschool.study_pod.global.address.entity.Sido;
import com.itschool.study_pod.domain.studygroup.repository.StudyGroupRepository;
import com.itschool.study_pod.domain.subjectarea.repository.SubjectAreaRepository;
import com.itschool.study_pod.domain.user.repository.UserRepository;
import com.itschool.study_pod.global.address.repository.SidoRepository;
import com.itschool.study_pod.global.enumclass.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class EnrollmentRepositoryTest extends JpaRepositoryTest {

    @Autowired
    private EnrollmentRepository enrollmentRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private StudyGroupRepository studyGroupRepository;

    @Autowired
    private SubjectAreaRepository subjectAreaRepository;

    @Autowired
    private SidoRepository sidoRepository;

    private User savedUser;

    private SubjectArea savedSubject;

    private StudyGroup savedStudyGroup;

    private Sido savedSido;


    @BeforeEach
    public void beforeSetUp() {

        savedUser = userRepository.save(
                User.builder()
                        .email(UUID.randomUUID() + "@example.com")
                        .password("1234")
                        .role(AccountRole.ROLE_USER)
                        .name("abc")
                        .nickname(UUID.randomUUID().toString())
                        .suspended(false)
                        .build()
        );

        savedSubject = subjectAreaRepository.save(SubjectArea.builder()
                .subject(Subject.IT)
                .build());

        Sido sido = Sido.builder()
                .sidoCd("11")
                .sidoNm("서울특별시")
                .build();

        savedSido = sidoRepository.save(sido);

        savedStudyGroup = studyGroupRepository.save(
                StudyGroup.builder()
                        .title("자바 스터디")
                        .description("설명")
                        .maxMembers(5)
                        .meetingMethod(MeetingMethod.ONLINE)
                        .recruitmentStatus(RecruitmentStatus.RECRUITING)
                        .feeType(FeeType.MONTHLY)
                        .amount(10000L)
                        .leader(savedUser)
                        .sido(savedSido)
                        .subjectArea(savedSubject)
                        .keywords(Set.of("키워드1", "키워드2"))
                        .weeklySchedules(Set.of(WeeklySchedule.builder()
                                .dayOfWeek(DayOfWeek.MONDAY)
                                .startTime(LocalTime.of(9, 0))
                                .endTime(LocalTime.of(10, 0))
                                .build()))
                        .suspended(false)
                        .build()
        );
    }

    @AfterEach
    public void afterCleanUp() {
        // enrollmentRepository.deleteAll();
        // studyGroupRepository.deleteAll(); // 수정 해야함
        // subjectAreaRepository.deleteAll();
        // userRepository.deleteAll();
    }

    @Test
    @DisplayName("저장 테스트")
    void create() {
        Long beforeCount = enrollmentRepository.count();

        Enrollment enrollment = Enrollment.builder()
                // .appliedAt(LocalDateTime.now())
                .introduce("안녕하세요. 참여 신청합니다.")
                .status(EnrollmentStatus.PENDING)
                .user(savedUser)
                .studyGroup(savedStudyGroup)
                .build();

        enrollmentRepository.save(enrollment);

        assertThat(enrollmentRepository.count()).isEqualTo(beforeCount + 1);
    }

    @Test
    @DisplayName("유저 객체로 조회 테스트")
    void findByUser() {
        Enrollment enrollment = Enrollment.builder()
                // .appliedAt(LocalDateTime.now())
                .introduce("참여합니다.")
                .status(EnrollmentStatus.PENDING)
                .user(savedUser)
                .studyGroup(savedStudyGroup)
                .build();
        enrollmentRepository.save(enrollment);

        List<Enrollment> result = enrollmentRepository.findByUser(savedUser);
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getUser().getId()).isEqualTo(savedUser.getId());
    }

    @Test
    @DisplayName("스터디 그룹 id로 조회 테스트")
    void findByStudyGroupId() {
        Enrollment enrollment = Enrollment.builder()
                // .appliedAt(LocalDateTime.now())
                .introduce("스터디 신청합니다.")
                .status(EnrollmentStatus.PENDING)
                .user(savedUser)
                .studyGroup(savedStudyGroup)
                .build();
        enrollmentRepository.save(enrollment);

        List<Enrollment> result = enrollmentRepository.findByStudyGroupId(savedStudyGroup.getId());
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getStudyGroup().getId()).isEqualTo(savedStudyGroup.getId());
    }

    @Test
    @DisplayName("사용자id와 그룹 id로 조회 테스트")
    void findByUserIdAndStudyGroupId() {
        Enrollment enrollment = Enrollment.builder()
                // .appliedAt(LocalDateTime.now())
                .introduce("신청!")
                .status(EnrollmentStatus.PENDING)
                .user(savedUser)
                .studyGroup(savedStudyGroup)
                .build();
        enrollmentRepository.save(enrollment);

        Optional<Enrollment> result = enrollmentRepository.findByUserIdAndStudyGroupId(savedUser.getId(), savedStudyGroup.getId());
        assertThat(result).isPresent();
        assertThat(result.get().getUser().getId()).isEqualTo(savedUser.getId());
        assertThat(result.get().getStudyGroup().getId()).isEqualTo(savedStudyGroup.getId());
    }
}
