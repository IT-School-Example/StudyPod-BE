package com.itschool.study_pod.repository;

import com.itschool.study_pod.StudyPodApplicationTests;
import com.itschool.study_pod.entity.Enrollment;
import com.itschool.study_pod.entity.StudyGroup;
import com.itschool.study_pod.entity.User;
import com.itschool.study_pod.enumclass.EnrollmentStatus;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class EnrollmentRepositoryTest extends StudyPodApplicationTests {

    @Autowired
    private EnrollmentRepository enrollmentRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private StudyGroupRepository studyGroupRepository; // 수정 해야함

    private User user;
    private StudyGroup studyGroup;

    @BeforeEach
    public void beforeCleanUp() {
        enrollmentRepository.deleteAll();
        studyGroupRepository.deleteAll(); // 수정 해야함
        userRepository.deleteAll();

        user = userRepository.save(
                User.builder()
                        .email("test@example.com")
                        .nickname("테스터")
                        .password("password123")
                        .build()
        );

        // 수정 해야함
        studyGroup = studyGroupRepository.save(
                StudyGroup.builder()
                        .title("자바 스터디")
                        .description("자바 기초 공부")
                        .build()
        );
    }

    @AfterEach
    public void afterCleanUp() {
        enrollmentRepository.deleteAll();
        studyGroupRepository.deleteAll(); // 수정 해야함
        userRepository.deleteAll();
    }

    @Test
    void create() {
        Enrollment enrollment = Enrollment.builder()
                .appliedAt(LocalDateTime.now())
                .introduce("안녕하세요. 참여 신청합니다.")
                .status(EnrollmentStatus.PENDING)
                .user(user)
                .studyGroup(studyGroup)
                .build();

        enrollmentRepository.save(enrollment);

        assertThat(enrollmentRepository.count()).isEqualTo(1);
    }

    @Test
    void findByUser() {
        Enrollment enrollment = Enrollment.builder()
                .appliedAt(LocalDateTime.now())
                .introduce("참여합니다.")
                .status(EnrollmentStatus.PENDING)
                .user(user)
                .studyGroup(studyGroup)
                .build();
        enrollmentRepository.save(enrollment);

        List<Enrollment> result = enrollmentRepository.findByUser(user);
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getUser().getId()).isEqualTo(user.getId());
    }

    @Test
    void findByStudyGroupId() {
        Enrollment enrollment = Enrollment.builder()
                .appliedAt(LocalDateTime.now())
                .introduce("스터디 신청합니다.")
                .status(EnrollmentStatus.PENDING)
                .user(user)
                .studyGroup(studyGroup)
                .build();
        enrollmentRepository.save(enrollment);

        List<Enrollment> result = enrollmentRepository.findByStudyGroupId(studyGroup.getId());
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getStudyGroup().getId()).isEqualTo(studyGroup.getId());
    }

    @Test
    void findByUserIdAndStudyGroupId() {
        Enrollment enrollment = Enrollment.builder()
                .appliedAt(LocalDateTime.now())
                .introduce("신청!")
                .status(EnrollmentStatus.PENDING)
                .user(user)
                .studyGroup(studyGroup)
                .build();
        enrollmentRepository.save(enrollment);

        Optional<Enrollment> result = enrollmentRepository.findByUserIdAndStudyGroupId(user.getId(), studyGroup.getId());
        assertThat(result).isPresent();
        assertThat(result.get().getUser().getId()).isEqualTo(user.getId());
        assertThat(result.get().getStudyGroup().getId()).isEqualTo(studyGroup.getId());
    }
}
