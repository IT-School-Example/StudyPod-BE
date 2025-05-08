package com.itschool.study_pod.controller.api;

import com.itschool.study_pod.controller.base.CrudController;
import com.itschool.study_pod.dto.Header;
import com.itschool.study_pod.dto.request.admin.AdminPasswordUpdateRequest;
import com.itschool.study_pod.dto.request.admin.AdminRequest;
import com.itschool.study_pod.dto.response.AdminResponse;
import com.itschool.study_pod.entity.Admin;
import com.itschool.study_pod.service.AdminService;
import com.itschool.study_pod.service.base.CrudService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@Tag(name = "관리자", description = "관리자 API")
@RequestMapping("/api/admins")
public class AdminApiController extends CrudController<AdminRequest, AdminResponse, Admin> {

    private final AdminService adminService;

    @Override
    protected CrudService<AdminRequest, AdminResponse, Admin> getBaseService() {
        return adminService;
    }

    /*
     * 전체 수정 사용 불가 처리 (임시)
     * */
    @Override
    @Deprecated
    public Header<AdminResponse> update(Long id, Header<AdminRequest> request) {
        throw new RuntimeException("이메일 및 역할 전체 수정 불가하도록 임시 조치");
    }

    /*
     * 비밀번호 수정하기
     * */
    @Operation(summary = "관리자 비밀번호 수정", description = "관리자(Admin) 비밀번호 수정하기")
    @PatchMapping("update-pw/{id}")
    public Header<AdminResponse> updatePassword(@PathVariable(name = "id") Long id,
                                                @RequestBody @Valid Header<AdminPasswordUpdateRequest> request) {
        log.info("update password : {}에서 전체 조회 요청", this.getClass().getSimpleName());
        return adminService.updatePassword(id, request);
    }
}
