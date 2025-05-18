package com.itschool.study_pod.controller.api;

import com.itschool.study_pod.controller.base.CrudController;
import com.itschool.study_pod.dto.Header;
import com.itschool.study_pod.dto.request.studygroup.StudyGroupRequest;
import com.itschool.study_pod.dto.request.studygroup.StudyGroupSearchRequest;
import com.itschool.study_pod.dto.response.StudyGroupResponse;
import com.itschool.study_pod.dto.response.UserResponse;
import com.itschool.study_pod.entity.StudyGroup;
import com.itschool.study_pod.entity.User;
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

    @PostMapping("search")
    @Operation(summary = "검색 기능", description = "")
    public Header<List<StudyGroupResponse>> findAllByFilters(@RequestParam(required = false) String searchStr,
                                                             @RequestBody Header<StudyGroupSearchRequest> request,
                                                             @ParameterObject @PageableDefault(sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {

        return studyGroupService.findAllByFilters(searchStr, request, pageable);
    }

    @GetMapping("/filter/recruitment")
    @Operation(summary = "모집 상태로 스터디 그룹 조회", description = "RECRUITING 또는 CLOSED 상태로 필터링")
    public Header<List<StudyGroupResponse>> getByRecruitmentStatus(@RequestParam(name = "recruitmentStatus") RecruitmentStatus recruitmentStatus) {
        try {
            return studyGroupService.findAllByRecruitmentStatus(recruitmentStatus);
        } catch (IllegalArgumentException e) {
            return Header.ERROR("요청에 실패했습니다.");
        } catch (RuntimeException e) {
            return Header.ERROR("해당 조건의 스터디 그룹을 불러오지 못했습니다.");
        }
    }

    @GetMapping("/filter/meeting")
    @Operation(summary = "스터디 방식으로 스터디 그룹 조회", description = "ONLINE, OFFLINE, BOTH 중 하나의 스터디 방식으로 필터링합니다.")
    public Header<List<StudyGroupResponse>> getByMeetingMethod(@RequestParam(name = "meetingMethod") MeetingMethod meetingMethod) {
        try {
            return studyGroupService.findAllByMeetingMethod(meetingMethod);
        } catch (IllegalArgumentException e) {
            return Header.ERROR("요청에 실패했습니다.");
        } catch (RuntimeException e) {
            return Header.ERROR("해당 조건의 스터디 그룹을 불러오지 못했습니다.");
        }
    }

    @Operation(summary = "스터디그룹 id와 등록 상태로 회원 목록 조회", description = "study_group_id와 등록 상태에 따른 회원 목록을 조회")
    @GetMapping("{id}/users")
    public Header<List<UserResponse>> findEnrolledUsersByStudyGroupId(@PathVariable(name = "id") Long id,
                                                                      @RequestParam(name = "enrollmentStatus") EnrollmentStatus enrollmentStatus) {
        log.info("스터디그룹별 스터디 등록 내역 조회 : {}에서 id={}로 조회 요청", this.getClass().getSimpleName(), id);
        return enrollmentService.findEnrolledUsersByStudyGroupId(id, enrollmentStatus);
    }

    @GetMapping("/my")
    @Operation(summary = "회원이 소속된 승인된 스터디 그룹 조회", description = "userId로 APPROVED 상태의 스터디 그룹 정보를 조회합니다.")
    public Header<StudyGroupResponse> getMyStudyGroup(@RequestParam(name = "userId") Long userId) {
        return studyGroupService.findStudyGroupByUserId(userId);
    }


    @GetMapping("/user/{userId}/enrolled-groups")
    @Operation(summary = "사용자의 등록된 스터디 그룹 목록 조회", description = "회원 ID와 등록 상태로 스터디 그룹 리스트를 반환합니다.")
    public Header<List<StudyGroupResponse>> getStudyGroupsByUserId(
            @PathVariable Long userId,
            @RequestParam(name = "enrollmentStatus", required = false, defaultValue = "APPROVED") EnrollmentStatus enrollmentStatus
    ) {
        return enrollmentService.findEnrolledStudyGroupsByUserId(userId, enrollmentStatus);
    }

    // 스터디 리더 개설한 스터디 그룹 조회
    @GetMapping("/leader/{leaderId}")
    @Operation(summary = "스터디 리더가 개설한 스터디 그룹 조회", description = "리더 ID로 해당 사용자가 생성한 스터디 그룹 목록을 조회합니다.")
    public Header<List<StudyGroupResponse>> getStudyGroupsByLeaderId(@PathVariable Long leaderId) {
        return studyGroupService.findStudyGroupsByLeaderId(leaderId);
    }

}
