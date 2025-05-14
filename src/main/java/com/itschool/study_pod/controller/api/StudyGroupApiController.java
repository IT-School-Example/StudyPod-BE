package com.itschool.study_pod.controller.api;

import com.itschool.study_pod.controller.base.CrudController;
import com.itschool.study_pod.dto.Header;
import com.itschool.study_pod.dto.request.studygroup.StudyGroupRequest;
import com.itschool.study_pod.dto.request.studygroup.StudyGroupSearchRequest;
import com.itschool.study_pod.dto.response.StudyGroupResponse;
import com.itschool.study_pod.dto.response.UserResponse;
import com.itschool.study_pod.entity.StudyGroup;
import com.itschool.study_pod.enumclass.EnrollmentStatus;
import com.itschool.study_pod.enumclass.MeetingMethod;
import com.itschool.study_pod.enumclass.RecruitmentStatus;
import com.itschool.study_pod.service.EnrollmentService;
import com.itschool.study_pod.service.StudyGroupService;
import com.itschool.study_pod.service.base.CrudService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@Tag(name = "스터디 그룹", description = "스터디 그룹 API")
@RequestMapping("/api/study-groups")
public class StudyGroupApiController extends CrudController<StudyGroupRequest, StudyGroupResponse, StudyGroup> {

    private final StudyGroupService studyGroupService;
    private final EnrollmentService enrollmentService;

    @Override
    protected CrudService<StudyGroupRequest, StudyGroupResponse, StudyGroup> getBaseService() {
        return studyGroupService;
    }

    @PostMapping("/search")
    @Operation(summary = "스터디 그룹 필터링 검색", description = """
            키워드(title 또는 keywords) + 모집 상태 + 스터디 방식 + 주제 영역을 기준으로 스터디 그룹을 필터링합니다.
            searchStr 필드만 입력해도 키워드 검색이 가능합니다.
            """)
    public Header<List<StudyGroupResponse>> findAllByFilters(
            @RequestParam(required = false) String searchStr,
            @RequestBody Header<StudyGroupSearchRequest> request,
            @ParameterObject @PageableDefault(sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        return studyGroupService.findAllByFilters(searchStr, request, pageable);
    }

    @GetMapping("/filter/recruitment")
    @Operation(summary = "모집 상태로 스터디 그룹 조회", description = "RECRUITING 또는 CLOSED 상태로 필터링합니다.")
    public Header<List<StudyGroupResponse>> getByRecruitmentStatus(@RequestParam(name = "recruitmentStatus") RecruitmentStatus recruitmentStatus) {
        try {
            return studyGroupService.findAllByRecruitmentStatus(recruitmentStatus);
        } catch (Exception e) {
            return Header.ERROR("해당 조건의 스터디 그룹을 불러오지 못했습니다.");
        }
    }

    @GetMapping("/filter/meeting")
    @Operation(summary = "스터디 방식으로 스터디 그룹 조회", description = "ONLINE, OFFLINE, BOTH 중 하나의 스터디 방식으로 필터링합니다.")
    public Header<List<StudyGroupResponse>> getByMeetingMethod(@RequestParam(name = "meetingMethod") MeetingMethod meetingMethod) {
        try {
            return studyGroupService.findAllByMeetingMethod(meetingMethod);
        } catch (Exception e) {
            return Header.ERROR("해당 조건의 스터디 그룹을 불러오지 못했습니다.");
        }
    }

    @GetMapping("{id}/users")
    @Operation(summary = "스터디 그룹 내 유저 목록 조회", description = "스터디 그룹 ID와 등록 상태에 따라 소속 유저들을 조회합니다.")
    public Header<List<UserResponse>> findEnrolledUsersByStudyGroupId(
            @PathVariable(name = "id") Long id,
            @RequestParam(name = "enrollmentStatus") EnrollmentStatus enrollmentStatus) {
        return enrollmentService.findEnrolledUsersByStudyGroupId(id, enrollmentStatus);
    }

    @GetMapping("/my")
    @Operation(summary = "회원이 소속된 스터디 그룹 조회", description = "userId로 APPROVED 상태의 스터디 그룹을 조회합니다.")
    public Header<StudyGroupResponse> getMyStudyGroup(@RequestParam(name = "userId") Long userId) {
        return studyGroupService.findStudyGroupByUserId(userId);
    }

    @GetMapping("/user/{userId}/enrolled-groups")
    @Operation(summary = "사용자의 등록된 스터디 그룹 목록 조회", description = "회원 ID로 등록된 스터디 그룹 리스트를 반환합니다.")
    public Header<List<StudyGroupResponse>> getStudyGroupsByUserId(
            @PathVariable Long userId,
            @RequestParam(name = "enrollmentStatus", required = false, defaultValue = "APPROVED") EnrollmentStatus enrollmentStatus) {
        return enrollmentService.findEnrolledStudyGroupsByUserId(userId, enrollmentStatus);
    }

    @GetMapping("/leader/{leaderId}")
    @Operation(summary = "스터디 리더가 개설한 스터디 그룹 조회", description = "리더 ID로 생성한 스터디 그룹을 조회합니다.")
    public Header<List<StudyGroupResponse>> getStudyGroupsByLeaderId(@PathVariable Long leaderId) {
        return studyGroupService.findStudyGroupsByLeaderId(leaderId);
    }

    @GetMapping("/search")
    @Operation(summary = "키워드 기반 간단 검색", description = "스터디 그룹 이름 또는 키워드에 해당 문자열이 포함된 항목을 조회합니다.")
    public Header<List<StudyGroupResponse>> searchByKeyword(@RequestParam(name = "keyword") String keyword) {
        return studyGroupService.searchByKeywordOnly(keyword);
    }

    @GetMapping("/filter/subject")
    @Operation(summary = "주제 영역으로 모집 중인 스터디 그룹 조회",
            description = "ENUM Subject 값 (예: LANGUAGE, IT, EXAM...)을 전달하면 해당 주제의 모집 중인 스터디 그룹만 반환합니다.")
    public Header<List<StudyGroupResponse>> getBySubjectArea(
            @RequestParam(name = "value") String subjectAreaValue
    ) {
        return studyGroupService.findBySubjectAreaAndRecruiting(subjectAreaValue);
    }

    @GetMapping("/filter/location")
    @Operation(summary = "주소 ID로 스터디 그룹 조회", description = "주소 ID(address_id)로 스터디 그룹을 필터링합니다.")
    public Header<List<StudyGroupResponse>> getByAddressId(@RequestParam(name = "value") Long addressId) {
        try {
            return studyGroupService.findByAddressId(addressId);
        } catch (Exception e) {
            return Header.ERROR("해당 조건의 스터디 그룹을 불러오지 못했습니다.");
        }
    }


}
