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
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.HashSet;
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
        savedSubject = subjectAreaRepository.save(SubjectArea.builder().subject(Subject.IT).build());
        savedUser = userRepository.save(User.builder()
                .email(UUID.randomUUID() + "@test.com")
                .password("1234")
                .role(AccountRole.ROLE_USER)
                .name("abc")
                .nickname(UUID.randomUUID().toString())
                .build());
        savedSido = sidoRepository.save(Sido.builder().sidoCd("11").sidoNm("서울특별시").build());
        savedSgg = sggRepository.save(Sgg.builder().sido(savedSido).sggCd("110").sggNm("종로구").build());
    }

    @Test
    @DisplayName("저장 테스트")
    void create() {
        StudyGroup savedEntity = studyGroupRepository.save(defaultStudyGroup());
        assertThat(savedEntity.getTitle()).isEqualTo("자바 스터디");
    }

    @Test
    @DisplayName("조회 테스트")
    void read() {
        StudyGroup saved = studyGroupRepository.save(defaultStudyGroup());
        StudyGroup found = studyGroupRepository.findById(saved.getId()).orElseThrow(EntityNotFoundException::new);
        assertThat(found).isEqualTo(saved);
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
                .keywords(new HashSet<>(Set.of("키워드1", "키워드2")))
                .weeklySchedules(new HashSet<>(Set.of(WeeklySchedule.builder()
                        .dayOfWeek(DayOfWeek.MONDAY)
                        .startTime(LocalTime.of(9, 0))
                        .endTime(LocalTime.of(10, 0))
                        .build())))
                .build();

        StudyGroup savedEntity = studyGroupRepository.save(entity);

        StudyGroup updated = StudyGroup.builder()
                .id(savedEntity.getId())  // ID 명시
                .title("수정된 제목")
                .description(savedEntity.getDescription())
                .maxMembers(savedEntity.getMaxMembers())
                .meetingMethod(savedEntity.getMeetingMethod())
                .recruitmentStatus(savedEntity.getRecruitmentStatus())
                .feeType(savedEntity.getFeeType())
                .amount(savedEntity.getAmount())
                .leader(savedEntity.getLeader())
                .address(savedEntity.getAddress())
                .subjectArea(savedEntity.getSubjectArea())
                .keywords(savedEntity.getKeywords())
                .weeklySchedules(savedEntity.getWeeklySchedules())
                .isDeleted(true)
                .build();

        StudyGroup updatedEntity = studyGroupRepository.save(updated);

        assertThat(updatedEntity.getTitle()).isEqualTo("수정된 제목");
        assertThat(updatedEntity.isDeleted()).isTrue();
    }


    @Test
    @DisplayName("삭제 테스트")
    void delete() {
        long before = studyGroupRepository.count();
        StudyGroup saved = studyGroupRepository.save(defaultStudyGroup());
        studyGroupRepository.deleteById(saved.getId());
        long after = studyGroupRepository.count();
        assertThat(after).isEqualTo(before);
    }

    @Test
    @DisplayName("리더 ID로 조회")
    void findAllByLeaderId() {
        studyGroupRepository.save(defaultStudyGroup());
        Page<StudyGroup> result = studyGroupRepository.findAllByLeaderId(savedUser.getId(), PageRequest.of(0, 10));
        assertThat(result).isNotEmpty();
    }

    @Test
    @DisplayName("모집 상태로 조회")
    void findAllByRecruitmentStatus() {
        studyGroupRepository.save(defaultStudyGroup());
        Page<StudyGroup> result = studyGroupRepository.findAllByRecruitmentStatus(RecruitmentStatus.RECRUITING, PageRequest.of(0, 10));
        assertThat(result).isNotEmpty();
    }

    @Test
    @DisplayName("스터디 방식으로 조회")
    void findAllByMeetingMethod() {
        studyGroupRepository.save(defaultStudyGroup());
        Page<StudyGroup> result = studyGroupRepository.findAllByMeetingMethod(MeetingMethod.ONLINE, PageRequest.of(0, 10));
        assertThat(result).isNotEmpty();
    }

    @Test
    @DisplayName("주제 영역 + 모집중 필터")
    void findBySubjectAreaAndRecruiting() {
        studyGroupRepository.save(defaultStudyGroup());
        Page<StudyGroup> result = studyGroupRepository.findBySubjectAreaAndRecruiting(savedSubject.getSubject(), PageRequest.of(0, 10));
        assertThat(result).isNotEmpty();
    }

    @Test
    @DisplayName("주소 ID로 조회")
    void findByAddressId() {
        studyGroupRepository.save(defaultStudyGroup());
        Page<StudyGroup> result = studyGroupRepository.findByAddressId(savedSgg.getId(), PageRequest.of(0, 10));
        assertThat(result).isNotEmpty();
    }

    @Test
    @DisplayName("키워드 검색")
    void searchByKeyword() {
        studyGroupRepository.save(defaultStudyGroup());
        Page<StudyGroup> result = studyGroupRepository.searchByKeyword("키워드1", PageRequest.of(0, 10));
        assertThat(result).isNotEmpty();
    }

    @Test
    @DisplayName("복합 필터링 검색")
    void findAllByFilters() {
        studyGroupRepository.save(defaultStudyGroup());
        Page<StudyGroup> result = studyGroupRepository.findAllByFilters("자바", RecruitmentStatus.RECRUITING, MeetingMethod.ONLINE, savedSubject.getId(), PageRequest.of(0, 10));
        assertThat(result).isNotEmpty();
    }

    private StudyGroup defaultStudyGroup() {
        return StudyGroup.builder()
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
    }
}
