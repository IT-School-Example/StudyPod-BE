package com.itschool.study_pod.controller.api;

import com.itschool.study_pod.controller.base.CrudController;
import com.itschool.study_pod.dto.request.EnrollmentRequest;
import com.itschool.study_pod.dto.response.EnrollmentResponse;
import com.itschool.study_pod.entity.Enrollment;
import com.itschool.study_pod.service.EnrollmentService;
import com.itschool.study_pod.service.base.CrudService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/enrollments")
public class EnrollmentController extends CrudController<EnrollmentRequest, EnrollmentResponse, Enrollment> {

    private final EnrollmentService enrollmentService;

    @Override
    protected CrudService<EnrollmentRequest, EnrollmentResponse, Enrollment> getBaseService() {
        return enrollmentService;
    }
}
