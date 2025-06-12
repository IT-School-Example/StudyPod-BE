package com.itschool.study_pod.domain.studygroup.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.itschool.study_pod.domain.enrollment.service.EnrollmentService;
import com.itschool.study_pod.domain.introduce.service.IntroduceService;
import com.itschool.study_pod.domain.studygroup.dto.request.StudyGroupSearchRequest;
import com.itschool.study_pod.domain.studygroup.dto.response.StudyGroupResponse;
import com.itschool.study_pod.domain.studygroup.service.StudyGroupService;
import com.itschool.study_pod.domain.user.dto.response.UserResponse;
import com.itschool.study_pod.global.base.dto.Header;
import com.itschool.study_pod.global.enumclass.EnrollmentStatus;
import com.itschool.study_pod.global.enumclass.MeetingMethod;
import com.itschool.study_pod.global.enumclass.RecruitmentStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(StudyGroupApiController.class)
@AutoConfigureMockMvc(addFilters = false)
class StudyGroupApiControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private StudyGroupService studyGroupService;

    @MockBean
    private EnrollmentService enrollmentService;

    @MockBean
    private IntroduceService introduceService;

    @Test
    @DisplayName("스터디 그룹 필터 검색")
    void testFindAllByFilters() throws Exception {
        StudyGroupSearchRequest searchRequest = new StudyGroupSearchRequest();
        Header<StudyGroupSearchRequest> requestHeader = Header.OK(searchRequest);

        StudyGroupResponse response = new StudyGroupResponse();
        response.setId(1L);
        response.setTitle("검색된 스터디");

        Mockito.when(studyGroupService.findAllByFilters(any(), any(), any()))
                .thenReturn(Header.OK(Collections.singletonList(response)));

        mockMvc.perform(post("/api/study-groups/search")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("searchStr", "")
                        .content(objectMapper.writeValueAsString(requestHeader)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data[0].title").value("검색된 스터디"));
    }

    @Test
    @DisplayName("모집 상태로 조회")
    void testGetByRecruitmentStatus() throws Exception {
        StudyGroupResponse response = new StudyGroupResponse();
        response.setId(2L);
        response.setTitle("모집 중");

        Mockito.when(studyGroupService.findAllByRecruitmentStatus(eq(RecruitmentStatus.RECRUITING), any()))
                .thenReturn(Header.OK(List.of(response)));

        mockMvc.perform(get("/api/study-groups/filter/recruitment")
                        .param("recruitmentStatus", "RECRUITING"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data[0].title").value("모집 중"));
    }

    @Test
    @DisplayName("스터디 방식으로 조회")
    void testGetByMeetingMethod() throws Exception {
        StudyGroupResponse response = new StudyGroupResponse();
        response.setId(3L);
        response.setTitle("온라인 스터디");

        Mockito.when(studyGroupService.findAllByMeetingMethod(eq(MeetingMethod.ONLINE), any()))
                .thenReturn(Header.OK(List.of(response)));

        mockMvc.perform(get("/api/study-groups/filter/meeting")
                        .param("meetingMethod", "ONLINE"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data[0].title").value("온라인 스터디"));
    }

    @Test
    @DisplayName("스터디 그룹별 등록 회원 목록")
    void testFindEnrolledUsersByStudyGroupId() throws Exception {
        UserResponse user = new UserResponse();
        user.setId(100L);

        Mockito.when(enrollmentService.findEnrolledUsersByStudyGroupId(eq(10L), eq(EnrollmentStatus.APPROVED)))
                .thenReturn(Header.OK(List.of(user)));

        mockMvc.perform(get("/api/study-groups/10/users")
                        .param("enrollmentStatus", "APPROVED"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data[0].id").value(100));
    }

    @Test
    @DisplayName("본인의 스터디 그룹 조회")
    void testGetMyStudyGroup() throws Exception {
        StudyGroupResponse response = new StudyGroupResponse();
        response.setId(5L);
        response.setTitle("내 스터디");

        Mockito.when(studyGroupService.findStudyGroupByUserId(77L))
                .thenReturn(Header.OK(response));

        mockMvc.perform(get("/api/study-groups/my")
                        .param("userId", "77"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id").value(5));
    }

    @Test
    @DisplayName("사용자의 등록된 스터디 그룹 목록")
    void testGetStudyGroupsByUserId() throws Exception {
        StudyGroupResponse response = new StudyGroupResponse();
        response.setId(6L);

        Mockito.when(enrollmentService.findEnrolledStudyGroupsByUserId(88L, EnrollmentStatus.APPROVED))
                .thenReturn(Header.OK(List.of(response)));

        mockMvc.perform(get("/api/study-groups/user/88/enrolled-groups")
                        .param("enrollmentStatus", "APPROVED"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data[0].id").value(6));
    }

    @Test
    @DisplayName("리더가 만든 스터디 그룹 조회")
    void testGetStudyGroupsByLeaderId() throws Exception {
        StudyGroupResponse response = new StudyGroupResponse();
        response.setId(7L);

        Mockito.when(studyGroupService.findAllByLeaderId(eq(99L), any()))
                .thenReturn(Header.OK(List.of(response)));

        mockMvc.perform(get("/api/study-groups/leader/99"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data[0].id").value(7));
    }

    @Test
    @DisplayName("주제 영역으로 모집 중인 스터디 그룹 조회")
    void testGetBySubjectArea() throws Exception {
        StudyGroupResponse response = new StudyGroupResponse();
        response.setId(8L);

        Mockito.when(studyGroupService.findBySubjectAreaAndRecruiting(eq("LANGUAGE"), any()))
                .thenReturn(Header.OK(List.of(response)));

        mockMvc.perform(get("/api/study-groups/filter/subject")
                        .param("value", "LANGUAGE"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data[0].id").value(8));
    }

    @Test
    @DisplayName("시/도 코드로 스터디 그룹 조회")
    void testGetBySidoCd() throws Exception {
        StudyGroupResponse response = new StudyGroupResponse();
        response.setId(9L);

        Mockito.when(studyGroupService.findBySidoCd(eq("11"), any()))
                .thenReturn(Header.OK(List.of(response)));

        mockMvc.perform(get("/api/study-groups/filter/sido") // 정확한 경로
                        .param("sidoCd", "11"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data[0].id").value(9));
    }


    @Test
    @DisplayName("회원 ID와 상태로 스터디 조회")
    void testGetStudiesByUserIdAndStatus() throws Exception {
        StudyGroupResponse response = new StudyGroupResponse();
        response.setId(11L);

        Mockito.when(studyGroupService.findStudyGroupsByUserIdAndStatus(eq(55L), eq(EnrollmentStatus.APPROVED)))
                .thenReturn(Header.OK(List.of(response)));

        mockMvc.perform(get("/api/study-groups/55/studies")
                        .param("enrollmentStatus", "APPROVED"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data[0].id").value(11));
    }
}