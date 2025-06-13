package com.itschool.study_pod.domain.studygroup.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.itschool.study_pod.domain.enrollment.dto.request.EnrollmentRequest;
import com.itschool.study_pod.domain.enrollment.dto.response.EnrollmentResponse;
import com.itschool.study_pod.domain.enrollment.service.EnrollmentService;
import com.itschool.study_pod.domain.introduce.dto.response.IntroduceResponse;
import com.itschool.study_pod.domain.introduce.service.IntroduceService;
import com.itschool.study_pod.domain.studygroup.dto.request.StudyGroupRequest;
import com.itschool.study_pod.domain.studygroup.dto.request.StudyGroupSearchRequest;
import com.itschool.study_pod.domain.studygroup.dto.response.StudyGroupResponse;
import com.itschool.study_pod.domain.studygroup.dto.response.StudyGroupSummaryResponse;
import com.itschool.study_pod.domain.studygroup.entity.StudyGroup;
import com.itschool.study_pod.domain.studygroup.service.StudyGroupService;
import com.itschool.study_pod.domain.user.dto.response.UserResponse;
import com.itschool.study_pod.global.base.account.Account;
import com.itschool.study_pod.global.base.crud.CrudWithFileController;
import com.itschool.study_pod.global.base.crud.CrudWithFileService;
import com.itschool.study_pod.global.base.dto.Header;
import com.itschool.study_pod.global.base.dto.ReferenceDto;
import com.itschool.study_pod.global.enumclass.EnrollmentStatus;
import com.itschool.study_pod.global.enumclass.MeetingMethod;
import com.itschool.study_pod.global.enumclass.RecruitmentStatus;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@Tag(name = "스터디 그룹", description = "스터디 그룹 API")
@RequestMapping("/api/study-groups")
public class StudyGroupApiController extends CrudWithFileController<StudyGroupRequest, StudyGroupResponse, StudyGroup> {

    private final StudyGroupService studyGroupService;

    private final EnrollmentService enrollmentService;

    private final IntroduceService introduceService;

    @Override
    protected CrudWithFileService<StudyGroupRequest, StudyGroupResponse, StudyGroup> getBaseService() {
        return studyGroupService;
    }

    @PostMapping("/search")
    @Operation(summary = "스터디 그룹 필터링 검색", description = "키워드(title 또는 keywords) + 모집 상태 + 스터디 방식 + 주제 영역을 기준으로 스터디 그룹을 필터링합니다.")
    public Header<List<StudyGroupResponse>> findAllByFilters(
            @RequestParam(required = false) String searchStr,
            @RequestBody(required = false) Header<StudyGroupSearchRequest> request,
            @ParameterObject @PageableDefault(size = 5, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        if (request == null || request.getData() == null) {
            return Header.ERROR("검색 조건이 누락되었습니다.");
        }
        return studyGroupService.findAllByFilters(searchStr, request, pageable);
    }

    @GetMapping("/filter/recruitment")
    @Operation(summary = "모집 상태로 스터디 그룹 조회", description = "RECRUITING 또는 CLOSED 상태로 필터링합니다.")
    public Header<List<StudyGroupResponse>> getByRecruitmentStatus(
            @RequestParam(name = "recruitmentStatus") RecruitmentStatus recruitmentStatus,
            @ParameterObject @PageableDefault(sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        return studyGroupService.findAllByRecruitmentStatus(recruitmentStatus, pageable);
    }

    @GetMapping("/filter/meeting")
    @Operation(summary = "스터디 방식으로 스터디 그룹 조회", description = "ONLINE, OFFLINE, BOTH 중 하나의 스터디 방식으로 필터링합니다.")
    public Header<List<StudyGroupResponse>> getByMeetingMethod(
            @RequestParam(name = "meetingMethod") MeetingMethod meetingMethod,
            @ParameterObject @PageableDefault(sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        return studyGroupService.findAllByMeetingMethod(meetingMethod, pageable);
    }

    @Operation(summary = "스터디 그룹에 지원 신청한 회원 목록 조회", description = "study_group_id와 등록 상태에 따른 회원 목록을 조회")
    @GetMapping("{id}/users")
    public Header<List<UserResponse>> findEnrolledUsersByStudyGroupId(@PathVariable(name = "id") Long id,
                                                                      @RequestParam(name = "enrollmentStatus") EnrollmentStatus enrollmentStatus) {
        log.info("스터디그룹별 등록 회원 목록 조회 : {}에서 id={}로 조회 요청", this.getClass().getSimpleName(), id);

        return enrollmentService.findEnrolledUsersByStudyGroupId(id, enrollmentStatus);
    }

    @GetMapping("/my")
    @Operation(summary = "회원이 소속된 스터디 그룹 조회", description = "userId로 APPROVED 상태의 스터디 그룹을 조회합니다.")
    public Header<StudyGroupResponse> getMyStudyGroup(@RequestParam(name = "userId") Long userId) {
        return studyGroupService.findStudyGroupByUserId(userId);
    }

    @GetMapping("/user/{accountId}/enrolled-groups")
    @Operation(summary = "사용자의 등록된 스터디 그룹 목록 조회", description = "회원 ID와 등록 상태로 스터디 그룹 리스트를 반환합니다.")
    public Header<List<StudyGroupResponse>> getStudyGroupsByUserId(
            @PathVariable(name = "accountId") Long accountId,
            @RequestParam(name = "enrollmentStatus", required = false, defaultValue = "APPROVED") EnrollmentStatus enrollmentStatus
    ) {
        /*Long currentAccountId = getCurrentAccountId();

        if(!accountId.equals(currentAccountId)) {
            return Header.ERROR("가입한 회원만 조회가 가능합니다");
        }*/

        return enrollmentService.findEnrolledStudyGroupsByUserId(accountId, enrollmentStatus);
    }

    @GetMapping("/leader/{leaderId}")
    @Operation(summary = "스터디 리더가 개설한 스터디 그룹 조회", description = "리더 ID로 생성한 스터디 그룹을 조회합니다.")
    public Header<List<StudyGroupResponse>> getStudyGroupsByLeaderId(
            @PathVariable Long leaderId,
            @ParameterObject @PageableDefault(sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        return studyGroupService.findAllByLeaderId(leaderId, pageable);
    }

    @GetMapping("/filter/subject")
    @Operation(summary = "주제 영역으로 모집 중인 스터디 그룹 조회", description = "ENUM Subject 값 (예: LANGUAGE, IT, EXAM...)을 전달하면 해당 주제의 모집 중인 스터디 그룹만 반환합니다.")
    public Header<List<StudyGroupResponse>> getBySubjectArea(
            @RequestParam(name = "value") String subjectAreaValue,
            @ParameterObject @PageableDefault(sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        return studyGroupService.findBySubjectAreaAndRecruiting(subjectAreaValue, pageable);
    }


    // 추가된 시도 코드 기반 필터 API
    @GetMapping("/filter/sido")
    @Operation(summary = "시도 코드로 스터디 그룹 조회", description = "시도 코드(sidoCd)를 기준으로 해당 시도에 속한 스터디 그룹을 조회합니다.")
    public Header<List<StudyGroupResponse>> getBySidoCd(
            @RequestParam(name = "sidoCd") String sidoCd,
            @ParameterObject @PageableDefault(sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        return studyGroupService.findBySidoCd(sidoCd, pageable);
    }

    @GetMapping("/{userId}/studies")
    @Operation(summary = "회원 ID와 등록 상태로 스터디 그룹 목록 조회", description = "특정 회원이 신청한 스터디 내역을 등록 상태로 필터링하여 반환합니다.")
    public Header<List<StudyGroupResponse>> getStudiesByUserIdAndStatus(
            @PathVariable Long userId,
            @RequestParam(name = "enrollmentStatus") EnrollmentStatus enrollmentStatus) {
        return studyGroupService.findStudyGroupsByUserIdAndStatus(userId, enrollmentStatus);
    }

    @GetMapping("/introduce/{studyGroupId}")
    @Operation(summary = "스터디 그룹 소개글 조회", description = "스터디 그룹 ID로 등록된 소개글을 조회합니다.")
    public Header<IntroduceResponse> getIntroduceByStudyGroupId(
            @PathVariable(name = "studyGroupId") Long studyGroupId
    ) {
        return introduceService.findByStudyGroupId(studyGroupId);
    }

    @PostMapping("/enrollments")
    @Operation(summary = "스터디 그룹 신청", description = "스터디 그룹을 신청합니다.")
    public Header<EnrollmentResponse> applyToStudyGroup(
            @RequestBody @Valid EnrollmentRequest request
    ) {
        return enrollmentService.enroll(request);
    }


    @GetMapping("/detail/{studyGroupId}")
    @Operation(summary = "스터디 그룹 상세정보 보기", description = "스터디 그룹의 멤버만 상세정보 조회 가능합니다.")
    public Header<StudyGroupResponse> viewStudyGroupDetail(@PathVariable(name = "studyGroupId") Long studyGroupId) {
        return studyGroupService.read(studyGroupId);
    }


    @GetMapping("/{id}/summary")
    @Operation(summary = "스터디 그룹 요약 정보 조회", description = "스터디 그룹 ID로 해당 그룹의 간단한 정보(id, 제목)를 조회합니다.")
    public ResponseEntity<StudyGroupSummaryResponse> getStudyGroupSummary(@PathVariable Long id) {
        StudyGroupSummaryResponse summaryResponse = studyGroupService.getSummary(id);
        return ResponseEntity.ok(summaryResponse);
    }
}

