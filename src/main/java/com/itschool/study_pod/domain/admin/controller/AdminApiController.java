package com.itschool.study_pod.domain.admin.controller;

import com.itschool.study_pod.domain.studygroup.dto.response.StudyGroupResponse;
import com.itschool.study_pod.domain.user.dto.response.UserResponse;
import com.itschool.study_pod.global.base.crud.CrudController;
import com.itschool.study_pod.global.base.dto.Header;
import com.itschool.study_pod.domain.admin.dto.request.AdminPasswordUpdateRequest;
import com.itschool.study_pod.domain.admin.dto.request.AdminRequest;
import com.itschool.study_pod.domain.admin.dto.response.AdminResponse;
import com.itschool.study_pod.domain.admin.entity.Admin;
import com.itschool.study_pod.domain.admin.service.AdminService;
import com.itschool.study_pod.global.base.crud.CrudService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@Tag(name = "관리자", description = "관리자 API")
@RequestMapping("/api/admin")
public class AdminApiController extends CrudController<AdminRequest, AdminResponse, Admin> {

    private final AdminService adminService;

    @Override
    protected CrudService<AdminRequest, AdminResponse, Admin> getBaseService() {
        return adminService;
    }

    /*
     * 비밀번호 수정하기
     * */
    @Operation(summary = "관리자 비밀번호 수정", description = "관리자(Admin) 비밀번호 수정하기")
    @ResponseStatus(HttpStatus.NO_CONTENT) // 204, NO_CONTENT
    @PatchMapping("update-pw/{id}")
    public Header<Void> updatePassword(@PathVariable(name = "id") Long id,
                                       @RequestBody @Valid Header<AdminPasswordUpdateRequest> request) {
        log.info("update password : {}에서 전체 조회 요청", this.getClass().getSimpleName());
        return adminService.updatePassword(id, request);
    }

    // ✅ 추가: 회원 전체 조회
    @Operation(summary = "회원 목록 조회", description = "전체 회원 목록을 페이징으로 조회합니다.")
    @GetMapping("/users")
    public Header<Page<UserResponse>> findAllUsers(Pageable pageable) {
        return adminService.findAllUsers(pageable);
    }

    // ✅ 추가: 회원 삭제
    @Operation(summary = "회원 삭제", description = "지정한 회원을 논리적 삭제 처리합니다.")
    @PatchMapping("/deleted-user")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@RequestParam Long userId) {
        adminService.deleteUser(userId);
    }


    // ✅ 추가: 관리자별 스터디 그룹 조회
    @Operation(summary = "전체 스터디 그룹 조회", description = "관리자가 모든 스터디 그룹을 조회합니다.")
    @GetMapping("/study-groups")
    public Header<List<StudyGroupResponse>> getAllStudyGroups() {
        return adminService.getAllStudyGroups();
    }

    // ✅ 추가: 스터디 그룹 삭제
    @Operation(summary = "스터디 그룹 삭제", description = "지정한 스터디 그룹을 삭제 처리합니다.")
    @PatchMapping("/deleted-study")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteStudyGroup(@RequestParam @NotNull Long studyGroupId) {
        adminService.deleteStudyGroup(studyGroupId);
    }

    // ✅ 추가: 부적절한 유저 정지
    @Operation(summary = "불량회원 정지", description = "신고되거나 문제가 있는 스터디 유저를 비활성화합니다.")
    @PatchMapping("/suspend-user")
    public ResponseEntity<Void> suspendUser(@RequestBody @Valid AdminRequest.SuspendUserRequest request) {
        adminService.suspendUser(request.getUserId(), request.getReason());
        return ResponseEntity.noContent().build();
    }

    // 추가 : 부적절한 스터디 그룹 정지
    @Operation(summary = "부적절한 스터디 그룹 정지", description = "신고되거나 문제가 있는 스터디 그룹을 정지합니다.")
    @PatchMapping("/suspend-study-group")
    public ResponseEntity<Void> suspendStudyGroup(@RequestBody @Valid AdminRequest.SuspendStudyGroupRequest request) {
        adminService.suspendStudyGroup(request.getStudyGroupId(), request.getReason());
        return ResponseEntity.noContent().build();
    }

}
