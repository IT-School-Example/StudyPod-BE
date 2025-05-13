package com.itschool.study_pod.repository;

import com.itschool.study_pod.StudyPodApplicationTests;
import com.itschool.study_pod.embedable.WeeklySchedule;
import com.itschool.study_pod.entity.Introduce;
import com.itschool.study_pod.entity.StudyGroup;
import com.itschool.study_pod.entity.SubjectArea;
import com.itschool.study_pod.entity.User;
import com.itschool.study_pod.entity.address.Sgg;
import com.itschool.study_pod.entity.address.Sido;
import com.itschool.study_pod.enumclass.*;
import com.itschool.study_pod.repository.address.SggRepository;
import com.itschool.study_pod.repository.address.SidoRepository;
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
class IntroduceRepositoryTest extends StudyPodApplicationTests {

    @Autowired
    private IntroduceRepository introduceRepository;

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

    private StudyGroup savedStudyGroup;

    private SubjectArea savedSubject;

    private User savedUser;

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

        StudyGroup studyGroup = StudyGroup.builder()
                .id(1L)
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
                .build();

        savedStudyGroup = studyGroupRepository.save(studyGroup);
    }

    @AfterEach
    public void afterCleanUp() {
    }

    @Test
    @DisplayName("저장 테스트")
    void create() {
        Long beforeCount = introduceRepository.count();

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