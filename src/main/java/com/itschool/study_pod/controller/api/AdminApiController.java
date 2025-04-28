package com.itschool.study_pod.controller.api;

import com.itschool.study_pod.controller.base.CrudController;
import com.itschool.study_pod.dto.request.AdminRequest;
import com.itschool.study_pod.dto.response.AdminResponse;
import com.itschool.study_pod.entity.Admin;
import com.itschool.study_pod.service.AdminService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admins")
public class AdminApiController extends CrudController<AdminRequest, AdminResponse, Admin> {

    public AdminApiController(AdminService baseService) {
        super(baseService);
    }
}
