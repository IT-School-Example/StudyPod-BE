package com.itschool.study_pod.repository;

import com.itschool.study_pod.StudyPodApplicationTests;
import com.itschool.study_pod.entity.*;
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
class InterestedStudyRepositoryTest extends StudyPodApplicationTests {

    @Autowired
    private InterestedStudyRepository interestedStudyRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private StudyGroupRepository studyGroupRepository;

    // 보류
    @Autowired
    private SubjectAreaRepository subjectAreaRepository;


    private User savedUser;

    private StudyGroup savedstudyGroup;

    // 보류
    private SubjectArea savedSubject;

    @BeforeEach
    public void beforeSetUp() {

        User user = User.builder()
                .email(UUID.randomUUID() + "@study.com")
                .password("1234")
                .role(AccountRole.ROLE_USER)
                .name("abc")
                .nickname(UUID.randomUUID().toString())
                .build();

        savedUser = userRepository.save(user);

        SubjectArea subjectArea = SubjectArea.builder()
                .subject(Subject.IT)
                .build();

        // subject saved를 따로 만들어야 사용가능?
        savedSubject = subjectAreaRepository.save(subjectArea);

        StudyGroup studyGroup = StudyGroup.builder()
                .title("자바 스터디")
                .maxMembers(7)
                .recruitmentStatus(RecruitmentStatus.RECRUITING)
                .meetingMethod(MeetingMethod.OFFLINE)
                .subjectArea(savedSubject)
                .keywords(Set.of("키워드1", "키워드2"))
                .build();

        savedstudyGroup = studyGroupRepository.save(studyGroup);

    }

    @AfterEach
    public void afterCleanUp() {
        /*interestedStudyRepository.deleteAll();
        userRepository.deleteAll();
        studyGroupRepository.deleteAll();
        subjectAreaRepository.deleteAll();*/
    }

    // 생성(Create) 테스트
    @Test
    @DisplayName("저장 테스트")
    void create() {
        // 관심 스터디 목록 생성 및 저장
        InterestedStudy interestedStudy = InterestedStudy.builder()
                .user(savedUser)
                .studyGroup(savedstudyGroup)
                .build();

        InterestedStudy savedInterestedStudy = interestedStudyRepository.save(interestedStudy);

         assertThat(interestedStudy).isEqualTo(savedInterestedStudy);
         assertThat(savedInterestedStudy.isDeleted()).isFalse();
    }

    // 조회(read) 테스트
    @Test
    @DisplayName("조회테스트")
    void read() {

        // 관심 스터디 목록 저장
        InterestedStudy interestedStudy = InterestedStudy.builder()
                .user(savedUser)
                .studyGroup(savedstudyGroup)
                .build();

        InterestedStudy savedInterestedStudy = interestedStudyRepository.save(interestedStudy);

        // ID로 조회
        InterestedStudy findInterestedStudy = interestedStudyRepository.findById(savedInterestedStudy.getId())
                        .orElseThrow(EntityNotFoundException::new);

        // 동일성 검증
        assertThat(interestedStudy).isEqualTo(savedInterestedStudy);
        assertThat(findInterestedStudy).isEqualTo(savedInterestedStudy);
        assertThat(savedInterestedStudy.isDeleted()).isFalse();
    }

    // 수정(update) 테스트
    @Test
    @DisplayName("수정테스트")
    void update() {

        // 관심 스터디 목록 저장
        InterestedStudy interestedStudy = InterestedStudy.builder()
                .user(savedUser)
                .studyGroup(savedstudyGroup)
                .build();

        InterestedStudy savedInterestedStudy = interestedStudyRepository.save(interestedStudy);

        // 삭제 처리(soft delete)
        savedInterestedStudy.softDelete();

        // 변경 사항 저장
        interestedStudyRepository.save(savedInterestedStudy);

        // 삭제 상태 확인
        assertThat(savedInterestedStudy.isDeleted()).isTrue();
    }

    // 삭제(Delete) 테스트
    @Test
    @DisplayName("삭제테스트")
    void delete() {
        // 삭제 전 개수 저장
        long beforeCount = interestedStudyRepository.count();

        // // 관심 스터디 목록 저장
        InterestedStudy interestedStudy = InterestedStudy.builder()
                .user(savedUser)
                .studyGroup(savedstudyGroup)
                .build();
        InterestedStudy savedInterestedStudy = interestedStudyRepository.save(interestedStudy);

        // 실제 삭제
        interestedStudyRepository.findById(savedInterestedStudy.getId())
                .ifPresent(interestedStudyRepository::delete);

        // 삭제 후 개수 비교
        long afterCount = interestedStudyRepository.count();
        assertThat(beforeCount).isEqualTo(afterCount); // 테스트상 동일하게 유지됨 (삭제 전 insert, 삭제 후 다시 원래 개수)
    }
}