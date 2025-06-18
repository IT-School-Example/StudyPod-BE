package com.itschool.study_pod.domain.studygroup.service;

import com.itschool.study_pod.StudyPodApplicationTests;
import com.itschool.study_pod.domain.studygroup.dto.request.StudyGroupRequest;
import com.itschool.study_pod.domain.studygroup.dto.response.StudyGroupResponse;
import com.itschool.study_pod.domain.studygroup.repository.StudyGroupRepository;
import com.itschool.study_pod.domain.subjectarea.entity.SubjectArea;
import com.itschool.study_pod.domain.subjectarea.repository.SubjectAreaRepository;
import com.itschool.study_pod.domain.user.entity.User;
import com.itschool.study_pod.domain.user.repository.UserRepository;
import com.itschool.study_pod.global.address.dto.request.SidoRequest;
import com.itschool.study_pod.global.address.entity.Sido;
import com.itschool.study_pod.global.address.repository.SidoRepository;
import com.itschool.study_pod.global.base.dto.Header;
import com.itschool.study_pod.global.base.dto.ReferenceDto;
import com.itschool.study_pod.global.embedable.WeeklySchedule;
import com.itschool.study_pod.global.enumclass.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

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
    private SidoRepository sidoRepository;
    @Autowired
    private SubjectAreaRepository subjectAreaRepository;

    private User leader;
    private Sido sido;
    private SubjectArea subjectArea;

    @BeforeEach
    void setUp() {
        leader = userRepository.save(User.builder()
                .email(UUID.randomUUID() + "@test.com")
                .password("pass")
                .name("테스터")
                .nickname("nick" + UUID.randomUUID())
                .role(AccountRole.ROLE_USER)
                .build());

        sido = sidoRepository.save(Sido.builder()
                .sidoCd("11")
                .sidoNm("서울특별시")
                .build());

        subjectArea = subjectAreaRepository.save(SubjectArea.builder()
                .subject(Subject.LANGUAGE)
                .build());
    }


    private StudyGroupRequest buildValidRequest() {
        return StudyGroupRequest.builder()
                .title("테스트 스터디")
                .description("설명입니다")
                .maxMembers(5)
                .meetingMethod(MeetingMethod.ONLINE)
                .recruitmentStatus(RecruitmentStatus.RECRUITING)
                .feeType(FeeType.ONE_TIME) // or FeeType.PER_EVENT
                .amount(0L)
                .leader(ReferenceDto.builder().id(leader.getId()).build())
                .sido(SidoRequest.builder()
                        .sidoCd(sido.getSidoCd())
                        .sidoNm(sido.getSidoNm())
                        .build())
                .subjectArea(ReferenceDto.builder().id(subjectArea.getId()).build())
                .keywords(Set.of("자바", "스프링"))
                .weeklySchedules(Set.of(WeeklySchedule.of(DayOfWeek.MONDAY, 10, 0, 12, 0)))
                .build();
    }

    @Test
    @DisplayName("스터디 그룹 생성 성공")
    void createStudyGroup_success() {
        // given
        StudyGroupRequest request = buildValidRequest();

        // when
        Header<StudyGroupResponse> response = studyGroupService.create(request, null);

        // then
        assertThat(response.getResultCode()).isEqualTo("OK");
        assertThat(response.getData()).isNotNull();
        assertThat(response.getData().getTitle()).isEqualTo("테스트 스터디");
    }

    @Test
    @DisplayName("존재하지 않는 리더 ID로 생성 시 실패")
    void createStudyGroup_invalidLeader() {
        StudyGroupRequest request = buildValidRequest();
        request.setLeader(ReferenceDto.builder().id(999999L).build());

        Header<StudyGroupResponse> response = studyGroupService.create(request, null);

        assertThat(response.getResultCode()).isEqualTo("ERROR");
        assertThat(response.getDescription()).contains("파일 업로드 오류 발생");
    }

    @Test
    @DisplayName("시/도 코드로 스터디 그룹 조회")
    void findBySidoCd() {
        // given
        StudyGroupRequest request = buildValidRequest();
        studyGroupService.create(request, null);

        // when
        Header<List<StudyGroupResponse>> result = studyGroupService.findBySidoCd("11", PageRequest.of(0, 10));

        // then
        assertThat(result.getData()).isNotEmpty();
        StudyGroupResponse response = result.getData().get(0);

        // 가장 안전하고 명확한 검증
        assertThat(response.getSido().getSidoCd()).isEqualTo("11");
    }
}
