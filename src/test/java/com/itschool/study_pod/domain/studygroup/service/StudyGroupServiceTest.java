package com.itschool.study_pod.domain.studygroup.service;

import com.itschool.study_pod.StudyPodApplicationTests;
import com.itschool.study_pod.domain.studygroup.dto.request.StudyGroupRequest;
import com.itschool.study_pod.domain.studygroup.dto.response.StudyGroupResponse;
import com.itschool.study_pod.domain.studygroup.repository.StudyGroupRepository;
import com.itschool.study_pod.domain.subjectarea.entity.SubjectArea;
import com.itschool.study_pod.domain.subjectarea.repository.SubjectAreaRepository;
import com.itschool.study_pod.domain.user.entity.User;
import com.itschool.study_pod.domain.user.repository.UserRepository;
import com.itschool.study_pod.global.address.entity.Sgg;
import com.itschool.study_pod.global.address.repository.SggRepository;
import com.itschool.study_pod.global.base.dto.Header;
import com.itschool.study_pod.global.base.dto.ReferenceDto;
import com.itschool.study_pod.global.embedable.WeeklySchedule;
import com.itschool.study_pod.global.enumclass.FeeType;
import com.itschool.study_pod.global.enumclass.MeetingMethod;
import com.itschool.study_pod.global.enumclass.RecruitmentStatus;
import com.itschool.study_pod.global.enumclass.Subject;
import com.itschool.study_pod.global.enumclass.AccountRole;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.util.Set;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@Transactional
class StudyGroupServiceTest extends StudyPodApplicationTests {

    @Autowired
    private StudyGroupService studyGroupService;
    @Autowired
    private StudyGroupRepository studyGroupRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private SggRepository sggRepository;
    @Autowired
    private SubjectAreaRepository subjectAreaRepository;

    private User leader;
    private Sgg address;
    private SubjectArea subjectArea;

    @BeforeEach
    void setUp() {
        leader = userRepository.save(User.builder()
                .email(UUID.randomUUID() + "@example.com")
                .password("testpass")
                .name("Test User")
                .nickname(UUID.randomUUID() + "")
                .role(AccountRole.ROLE_USER)
                .build());

        address = sggRepository.save(Sgg.builder()
                .sggCd("11010")
                .sggNm("종로구")
                .sido(null)
                .build());

        subjectArea = subjectAreaRepository.save(SubjectArea.builder()
                .subject(Subject.LANGUAGE)
                .build());
    }

    @Test
    void createStudyGroup() {
        StudyGroupRequest request = buildValidRequest();
        Header<StudyGroupResponse> response = studyGroupService.create(request, null);

        assertThat(response.getData()).isNotNull();
        assertThat(response.getData().getTitle()).isEqualTo("테스트 스터디");
        assertThat(studyGroupRepository.findAll()).hasSize(1);
    }

    /*@Test
    void createStudyGroup_withInvalidLeader_shouldThrowException() {
        StudyGroupRequest request = buildValidRequest();
        request.setLeader(ReferenceDto.builder().id(9999L).build());

        assertThatThrownBy(() -> studyGroupService.create(request, null))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessageContaining("리더 ID가 존재하지 않습니다.");
    }*/

    /*@Test
    void createStudyGroup_withInvalidSubjectArea_shouldThrowException() {
        StudyGroupRequest request = buildValidRequest();
        request.setSubjectArea(ReferenceDto.builder().id(9999L).build());

        assertThatThrownBy(() -> studyGroupService.create(request, null))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessageContaining("주제영역 ID가 존재하지 않습니다.");
    }*/

    @Test
    void findAllByMeetingMethod_shouldReturnFilteredResults() {
        createStudyGroup(); // 미리 하나 생성
        var result = studyGroupService.findAllByMeetingMethod(MeetingMethod.ONLINE, PageRequest.of(0, 10));

        assertThat(result.getData()).hasSize(1);
        assertThat(result.getData().get(0).getMeetingMethod()).isEqualTo(MeetingMethod.ONLINE);
    }

    private StudyGroupRequest buildValidRequest() {
        StudyGroupRequest request = new StudyGroupRequest();
        request.setTitle("테스트 스터디");
        request.setDescription("설명");
        request.setMaxMembers(5);
        request.setMeetingMethod(MeetingMethod.ONLINE);
        request.setRecruitmentStatus(RecruitmentStatus.RECRUITING);
        request.setFeeType(FeeType.MONTHLY);
        request.setAmount(0L);
        request.setLeader(ReferenceDto.builder().id(leader.getId()).build());
        request.setAddress(ReferenceDto.builder().id(address.getId()).build());
        request.setSubjectArea(ReferenceDto.builder().id(subjectArea.getId()).build());
        request.setKeywords(Set.of("자바", "테스트"));
        request.setWeeklySchedules(Set.of(
                WeeklySchedule.of(DayOfWeek.MONDAY, 10, 0, 12, 0)
        ));
        return request;
    }
}
