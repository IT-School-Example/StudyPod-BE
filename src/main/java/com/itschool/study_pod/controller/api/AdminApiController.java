package com.itschool.study_pod.controller.api;

import com.itschool.study_pod.controller.base.CrudController;
import com.itschool.study_pod.dto.request.AdminRequest;
import com.itschool.study_pod.dto.response.AdminResponse;
import com.itschool.study_pod.entity.Admin;
import com.itschool.study_pod.service.AdminService;
import com.itschool.study_pod.service.base.CrudService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Tag(name = "관리자", description = "관리자 API")
@RequestMapping("/api/admins")
public class AdminApiController extends CrudController<AdminRequest, AdminResponse, Admin> {

    private final AdminService adminService;

    // Create / POST : /api/admins
    // Read / GET : /api/admins/{id}
    // Update / PUT : /api/admins/{id}
    // Delete / DELETE : /api/admins/{id}

    @Override
    protected CrudService<AdminRequest, AdminResponse, Admin> getBaseService() {
        return adminService;
    }
}
