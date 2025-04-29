package com.itschool.study_pod.controller.api;

import com.itschool.study_pod.controller.base.CrudController;
import com.itschool.study_pod.dto.request.AdminRequest;
import com.itschool.study_pod.dto.request.address.SggRequest;
import com.itschool.study_pod.dto.response.AdminResponse;
import com.itschool.study_pod.dto.response.address.SggResponse;
import com.itschool.study_pod.entity.Admin;
import com.itschool.study_pod.entity.address.Sgg;
import com.itschool.study_pod.service.AdminService;
import com.itschool.study_pod.service.address.SggService;
import com.itschool.study_pod.service.base.CrudService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admins")
public class AdminController extends CrudController<AdminRequest, AdminResponse, Admin> {

    private final AdminService adminService;

    @Override
    protected CrudService<AdminRequest, AdminResponse, Admin> getBaseService() {
        return adminService;
    }
}
