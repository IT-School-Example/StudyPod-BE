package com.itschool.study_pod.domain.interestedstudy.repository;

import com.itschool.study_pod.StudyPodApplicationTests;
import com.itschool.study_pod.global.embedable.WeeklySchedule;
import com.itschool.study_pod.domain.interestedstudy.entity.InterestedStudy;
import com.itschool.study_pod.domain.studygroup.entity.StudyGroup;
import com.itschool.study_pod.domain.subjectarea.entity.SubjectArea;
import com.itschool.study_pod.domain.user.entity.User;
import com.itschool.study_pod.domain.studygroup.repository.StudyGroupRepository;
import com.itschool.study_pod.domain.subjectarea.repository.SubjectAreaRepository;
import com.itschool.study_pod.domain.user.repository.UserRepository;
import com.itschool.study_pod.global.address.entity.Sgg;
import com.itschool.study_pod.global.address.entity.Sido;
import com.itschool.study_pod.global.address.repository.SggRepository;
import com.itschool.study_pod.global.address.repository.SidoRepository;
import com.itschool.study_pod.global.enumclass.*;
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

    @Autowired
    private SubjectAreaRepository subjectAreaRepository;

    @Autowired
    private SggRepository sggRepository;

    @Autowired
    private SidoRepository sidoRepository;


    private User savedUser;

    private StudyGroup savedStudyGroup;

    private SubjectArea savedSubject;

    private Sido savedSido;

    private Sgg savedSgg;

    @BeforeEach
    public void beforeSetUp() {

        savedUser = userRepository.save(
                User.builder()
                        .email(UUID.randomUUID() +"@example.com")
                        .password("1234")
                        .role(AccountRole.ROLE_USER)
                        .name("abc")
                        .nickname(UUID.randomUUID().toString())
                        .build()
        );

        savedSubject = subjectAreaRepository.save(SubjectArea.builder()
                .subject(Subject.IT)
                .build());

        savedSubject = subjectAreaRepository.save(SubjectArea.builder()
                .subject(Subject.IT)
                .build());

        Sido sido = Sido.builder()
                .sidoCd("11")
                .sidoNm("서울특별시")
                .build();

        savedSido = sidoRepository.save(sido);

        Sgg sgg = Sgg.builder()
                .sido(savedSido)
                .sggCd("110")
                .sggNm("종로구")
                .build();

        savedSgg = sggRepository.save(sgg);


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
                        .address(savedSgg)
                        .subjectArea(savedSubject)
                        .keywords(Set.of("키워드1", "키워드2"))
                        .weeklySchedules(Set.of(WeeklySchedule.builder()
                                .dayOfWeek(DayOfWeek.MONDAY)
                                .startTime(LocalTime.of(9, 0))
                                .endTime(LocalTime.of(10, 0))
                                .build()))
                        .build()
        );

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
                .studyGroup(savedStudyGroup)
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
                .studyGroup(savedStudyGroup)
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
                .studyGroup(savedStudyGroup)
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
                .studyGroup(savedStudyGroup)
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