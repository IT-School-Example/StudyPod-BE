package com.itschool.study_pod.domain.admin.controller;

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
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
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
}
