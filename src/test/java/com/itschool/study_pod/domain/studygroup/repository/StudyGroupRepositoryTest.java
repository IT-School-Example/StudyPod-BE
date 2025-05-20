package com.itschool.study_pod.domain.studygroup.repository;

import com.itschool.study_pod.StudyPodApplicationTests;
import com.itschool.study_pod.global.embedable.WeeklySchedule;
import com.itschool.study_pod.domain.studygroup.entity.StudyGroup;
import com.itschool.study_pod.domain.subjectarea.entity.SubjectArea;
import com.itschool.study_pod.domain.user.entity.User;
import com.itschool.study_pod.global.address.entity.Sgg;
import com.itschool.study_pod.global.address.entity.Sido;
import com.itschool.study_pod.domain.subjectarea.repository.SubjectAreaRepository;
import com.itschool.study_pod.domain.user.repository.UserRepository;
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
class StudyGroupRepositoryTest extends StudyPodApplicationTests {

    @Autowired
    StudyGroupRepository studyGroupRepository;

    @Autowired
    SubjectAreaRepository subjectAreaRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SggRepository sggRepository;

    @Autowired
    private SidoRepository sidoRepository;


    private SubjectArea savedSubject;

    private User savedUser;

    private Sido savedSido;

    private Sgg savedSgg;

    @BeforeEach
    public void beforeSetUp() {
        SubjectArea subjectArea = SubjectArea.builder()
                .subject(Subject.IT)
                .build();

        savedSubject = subjectAreaRepository.save(subjectArea);

        User user = User.builder()
                .email(UUID.randomUUID() + "@subject.com")
                .password("1234")
                .role(AccountRole.ROLE_USER)
                .name("abc")
                .nickname(UUID.randomUUID().toString())
                .build();

        savedUser = userRepository.save(user);

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
    }

    @AfterEach
    public void afterCleanUp() {
        // studyGroupRepository.deleteAll();
        // subjectAreaRepository.deleteAll();
    }

    @Test
    @DisplayName("저장 테스트")
    void create() {
        StudyGroup entity = StudyGroup.builder()
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

        StudyGroup savedEntity = studyGroupRepository.save(entity);

        assertThat(savedEntity.getTitle()).isEqualTo("자바 스터디");
    }

    @Test
    @DisplayName("조회 테스트")
    void read() {
        StudyGroup entity = StudyGroup.builder()
                .title("Python 스터디")
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
                .description("설명")
                .maxMembers(5)
                .meetingMethod(MeetingMethod.ONLINE)
                .recruitmentStatus(RecruitmentStatus.RECRUITING)
                .feeType(FeeType.MONTHLY)
                .amount(10000L)
                .leader(savedUser)
                .address(savedSgg)
                .subjectArea(savedSubject)
                /*.keywords(Set.of("키워드1", "키워드2"))
                .weeklySchedules(Set.of(WeeklySchedule.builder()
                        .dayOfWeek(DayOfWeek.MONDAY)
                        .startTime(LocalTime.of(9, 0))
                        .endTime(LocalTime.of(10, 0))
                        .build()))*/
                .build();

        StudyGroup savedEntity = studyGroupRepository.save(entity);

        savedEntity.softDelete();

        // savedEntity.setKeywords(Set.of("수정"));

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

        StudyGroup savedEntity = studyGroupRepository.save(entity);

        studyGroupRepository.findById(savedEntity.getId())
                .ifPresent(studyGroupRepository::delete);

        long afterCount = studyGroupRepository.count();

        assertThat(afterCount).isEqualTo(beforeCount);
    }
}
