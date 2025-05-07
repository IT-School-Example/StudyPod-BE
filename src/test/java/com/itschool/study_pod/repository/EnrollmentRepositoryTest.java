package com.itschool.study_pod.repository;

import com.itschool.study_pod.StudyPodApplicationTests;
import com.itschool.study_pod.entity.Enrollment;
import com.itschool.study_pod.entity.StudyGroup;
import com.itschool.study_pod.entity.SubjectArea;
import com.itschool.study_pod.entity.User;
import com.itschool.study_pod.enumclass.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
class EnrollmentRepositoryTest extends StudyPodApplicationTests {

    @Autowired
    private EnrollmentRepository enrollmentRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private StudyGroupRepository studyGroupRepository; // 수정 해야함

    @Autowired
    private SubjectAreaRepository subjectAreaRepository;

    private User savedUser;

    private StudyGroup savedStudyGroup;

    private SubjectArea savedSubject;

    @BeforeEach
    public void beforeSetUp() {

        SubjectArea subjectArea = SubjectArea.builder()
                .subject(Subject.IT)
                .build();

        savedSubject = subjectAreaRepository.save(subjectArea);

        savedUser = userRepository.save(
                User.builder()
                        .email(UUID.randomUUID() +"@example.com")
                        .password("1234")
                        .role(AccountRole.ROLE_USER)
                        .name("abc")
                        .nickname(UUID.randomUUID().toString())
                        .build()
        );

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
                .appliedAt(LocalDateTime.now())
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
                .appliedAt(LocalDateTime.now())
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
                .appliedAt(LocalDateTime.now())
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
                .appliedAt(LocalDateTime.now())
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
