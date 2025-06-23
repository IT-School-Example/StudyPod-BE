package com.itschool.study_pod.domain.admin.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.itschool.study_pod.MockMvcTest;
import com.itschool.study_pod.domain.admin.dto.request.AdminRequest;
import com.itschool.study_pod.domain.admin.entity.Admin;
import com.itschool.study_pod.domain.admin.repository.AdminRepository;
import com.itschool.study_pod.domain.studygroup.dto.request.StudyGroupRequest;
import com.itschool.study_pod.domain.studygroup.entity.StudyGroup;
import com.itschool.study_pod.domain.studygroup.repository.StudyGroupRepository;
import com.itschool.study_pod.domain.subjectarea.dto.response.SubjectAreaResponse;
import com.itschool.study_pod.domain.subjectarea.entity.SubjectArea;
import com.itschool.study_pod.domain.subjectarea.repository.SubjectAreaRepository;
import com.itschool.study_pod.domain.user.dto.response.UserResponse;
import com.itschool.study_pod.domain.user.entity.User;
import com.itschool.study_pod.domain.user.repository.UserRepository;
import com.itschool.study_pod.global.address.dto.request.SidoRequest;
import com.itschool.study_pod.global.address.dto.response.SidoResponse;
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
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.RequestEntity.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Transactional
class AdminApiControllerTest extends MockMvcTest {

    @Autowired
    private StudyGroupRepository studyGroupRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private SubjectAreaRepository subjectAreaRepository;
    @Autowired
    private SidoRepository sidoRepository;
    @Autowired
    private AdminRepository adminRepository;
    @Autowired
    private ObjectMapper objectMapper;

    private User leader;
    private SubjectArea subjectArea;
    private Sido sido;
    private StudyGroup savedGroup;

    @BeforeEach
    void setUp() {
        leader = userRepository.save(User.builder()
                .email("testuser_" + UUID.randomUUID() + "@example.com")
                .password("pass1234")
                .name("테스트유저")
                .role(AccountRole.ROLE_USER)
                .build());

        subjectArea = subjectAreaRepository.save(SubjectArea.builder()
                .subject(Subject.IT)
                .build());

        sido = sidoRepository.save(Sido.builder()
                .sidoCd("11")
                .sidoNm("서울특별시")
                .build());

        savedGroup = studyGroupRepository.save(StudyGroup.builder()
                .title("테스트 스터디")
                .description("설명")
                .leader(leader)
                .subjectArea(subjectArea)
                .sido(sido)
                .meetingMethod(MeetingMethod.ONLINE)
                .recruitmentStatus(RecruitmentStatus.RECRUITING)
                .maxMembers(5)
                .feeType(FeeType.MONTHLY)
                .amount(0L)
                .build());
    }

    @Test
    @DisplayName("저장 테스트")
    void create() throws Exception {
        final String url = "/api/admin";
        final String email = UUID.randomUUID() + "@example.com";

        final AdminRequest userRequest = AdminRequest.builder()
                .email(email)
                .password("abcd1234!@#$")
                .name("저장테스트")
                .build();

        final String request = objectMapper.writeValueAsString(
                Header.<AdminRequest>builder().data(userRequest).build());

        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.post(url)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(request));

        result.andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.email").value(email));
    }

    @Test
    @DisplayName("조회 테스트")
    void read() throws Exception {
        final String email = UUID.randomUUID() + "@example.com";

        Admin savedAdmin = adminRepository.save(Admin.builder()
                .email(email)
                .password("abcd1234!@#$")
                .role(AccountRole.ROLE_USER)
                .name("조회테스트")
                .build());

        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.get("/api/admin/" + savedAdmin.getId())
                .accept(MediaType.APPLICATION_JSON_VALUE));

        result.andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.email").value(email));
    }

    @Test
    @DisplayName("수정 테스트")
    void update() throws Exception {
        Admin savedAdmin = adminRepository.save(Admin.builder()
                .email("old@example.com")
                .password("abcd1234!@#$")
                .role(AccountRole.ROLE_USER)
                .name("수정테스트")
                .build());

        AdminRequest requestDTO = AdminRequest.builder()
                .email("newEmail@example.com")
                .password("Password1234!@#$")
                .name("새이름")
                .build();

        String request = objectMapper.writeValueAsString(
                Header.<AdminRequest>builder().data(requestDTO).build());

        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.put("/api/admin/" + savedAdmin.getId())
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(request));

        result.andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.email").value("newEmail@example.com"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.name").value("새이름"));
    }

    @Test
    @DisplayName("삭제 테스트")
    void delete() throws Exception {
        Admin savedAdmin = adminRepository.save(Admin.builder()
                .email(UUID.randomUUID() + "@example.com")
                .password("abcd1234!@#$")
                .role(AccountRole.ROLE_USER)
                .name("삭제테스트")
                .build());

        long countBefore = adminRepository.count();

        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.delete("/api/admin/" + savedAdmin.getId()));

        result.andExpect(status().isNoContent());
        assertThat(adminRepository.count()).isEqualTo(countBefore - 1);
    }

    @Test
    @DisplayName("회원 목록 조회")
    void testFindAllUsers() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/admin/users?page=0&size=10"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("스터디 그룹 전체 조회")
    void testGetStudyGroupsByAdmin() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/admin/study-groups"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("스터디 그룹 삭제")
    void testDeleteStudyGroup() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.patch("/api/admin/deleted-study")
                        .param("studyGroupId", savedGroup.getId().toString()))
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("스터디 그룹 정지")
    void testSuspendStudyGroup() throws Exception {
        // 1. 선행 데이터 준비
        User user = userRepository.save(User.builder()
                .email("test@example.com")
                .name("테스터")
                .password("1234")
                .role(AccountRole.ROLE_USER)
                .build());

        SubjectArea subjectArea = subjectAreaRepository.save(
                SubjectArea.builder().subject(Subject.IT).build());

        Sido sido = sidoRepository.findById("11")
                .orElseThrow(() -> new RuntimeException("시도 코드 '11' 없음"));

        StudyGroupRequest request = StudyGroupRequest.builder()
                .title("테스트 스터디")
                .description("부적절한 내용")
                .maxMembers(5)
                .meetingMethod(MeetingMethod.ONLINE)
                .recruitmentStatus(RecruitmentStatus.RECRUITING)
                .feeType(FeeType.MONTHLY)
                .amount(0L)
                .leader(ReferenceDto.withId(user.getId()))
                .subjectArea(ReferenceDto.withId(subjectArea.getId()))
                .sido(SidoRequest.withId(sido.getSidoCd()))
                .keywords(Set.of("스프링"))
                .weeklySchedules(new HashSet<>(Set.of(
                        WeeklySchedule.of(DayOfWeek.TUESDAY, 19, 0, 21, 0),
                        WeeklySchedule.of(DayOfWeek.THURSDAY, 20, 0, 22, 0)
                )))
                .build();

        StudyGroup savedGroup = studyGroupRepository.save(StudyGroup.of(request));

        // 2. 정지 요청 객체 생성
        AdminRequest.SuspendStudyGroupRequest suspendRequest = new AdminRequest.SuspendStudyGroupRequest();
        suspendRequest.setStudyGroupId(savedGroup.getId());
        suspendRequest.setReason("부적절한 내용입니다");

        // 3. API 호출 & 검증
        mockMvc.perform(
                        org.springframework.test.web.servlet.request.MockMvcRequestBuilders
                                .patch("/api/admin/suspend-study-group")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(suspendRequest))
                )
                .andExpect(status().isNoContent());
    }


    @Test
    @DisplayName("회원 정지 요청")
    void testSuspendUser() throws Exception {
        AdminRequest.SuspendUserRequest request = new AdminRequest.SuspendUserRequest();
        request.setUserId(leader.getId());
        request.setReason("불법 활동");

        mockMvc.perform(MockMvcRequestBuilders.patch("/api/admin/suspend-user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isNoContent());
    }
}
