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
import com.itschool.study_pod.service.EnrollmentService;
import com.itschool.study_pod.service.StudyGroupService;
import com.itschool.study_pod.service.base.CrudService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.nio.file.AccessDeniedException;
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
    public Header<List<StudyGroupResponse>> findAllByFilters(@RequestParam String searchStr,
                                                             @RequestBody Header<StudyGroupSearchRequest> request,
                                                             @ParameterObject @PageableDefault(sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {

        return studyGroupService.findAllByFilters(searchStr, request, pageable);
    }

    @GetMapping("/filter/recruitment")
    @Operation(summary = "모집 상태로 스터디 그룹 조회", description = "RECRUITING 또는 CLOSED 상태로 필터링")
    public Header<List<StudyGroupResponse>> getByRecruitmentStatus(@RequestParam String value) {
        try {
            return studyGroupService.findAllByRecruitmentStatus(value);
        } catch (IllegalArgumentException e) {
            return Header.ERROR("요청에 실패했습니다.");
        } catch (RuntimeException e) {
            return Header.ERROR("해당 조건의 스터디 그룹을 불러오지 못했습니다.");
        }
    }

    @Operation(summary = "스터디그룹 id와 등록 상태로 회원 목록 조회", description = "study_group_id와 등록 상태에 따른 회원 목록을 조회")
    @GetMapping("{id}/users")
    public Header<List<UserResponse>> findEnrolledUsersByStudyGroupId(@PathVariable(name = "id") Long id,
                                                                      @RequestParam EnrollmentStatus enrollmentStatus) {
        log.info("스터디그룹별 스터디 등록 내역 조회 : {}에서 id={}로 조회 요청", this.getClass().getSimpleName(), id);
        return enrollmentService.findEnrolledUsersByStudyGroupId(id, enrollmentStatus);
    }

    @Operation(summary = "스터디 그룹 상세정보 조회", description = "스터디 그룹에 속해 있는 멤버들만 열람 가능한 상세조회를 조회")
    @GetMapping("{studyGroupId}/detail")
    public Header<StudyGroupResponse> viewStudyGroupDetail(@PathVariable Long studyGroupId,
                                                                 HttpSession session,
                                                                 Model model) throws AccessDeniedException { // ??
        // GrantedAuthority를 이용해 userDetails를 만들지 vs 세션로그인으로 하는지??..  -> Spring Security

        // 세션에서 사용자 정보 가져오기 (로그인 후 세션에 사용자 정보를 저장)
        User user = (User) session.getAttribute("user");

        // ※ return은 html할 때 봐서 변경
        if(user == null) {
            // 로그인 안 되어 있으면 메시지를 표시하고, 그룹 상세 정보를 보여주지 않음
            /*model.addAttribute("errorMessage", "로그인이 필요합니다.");
            return "group/error"; // html 경로 작성*/
            throw new RuntimeException("로그인이 필요합니다.");
        }
        // 로그인한 사용자가 그룹에 포함되어 있는지 확인
        StudyGroup studyGroup = studyGroupService.studyGroupDetail(studyGroupId, user.getId());

        if (studyGroup == null) {
            // 로그인한 사용자만 권한이 있는 그룹
            /*model.addAttribute("errorMessage", "그룹에 접근할 수 없습니다");
            return "group/error"; // html경로 작성*/
            throw new RuntimeException("그룹에 접근할 수 없습니다.");
        }
        // 필요한 필드만 담은
        StudyGroupResponse studyGroupResponse = new StudyGroupResponse(studyGroup);
        // 그룹 정보 모델에 담기
        /*model.addAttribute("studyGroup", studyGroupResponse);
        return "group/detail"; // html 경로 작성*/
        return Header.OK(studyGroupResponse);

    }
}
