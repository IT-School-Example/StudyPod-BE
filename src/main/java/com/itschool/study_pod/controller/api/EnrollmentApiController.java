package com.itschool.study_pod.controller.api;

import com.itschool.study_pod.controller.base.CrudController;
import com.itschool.study_pod.dto.request.EnrollmentRequest;
import com.itschool.study_pod.dto.response.EnrollmentResponse;
import com.itschool.study_pod.entity.Enrollment;
import com.itschool.study_pod.service.EnrollmentService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/enrollments")
public class EnrollmentApiController extends CrudController<EnrollmentRequest, EnrollmentResponse, Enrollment> {

    public EnrollmentApiController(EnrollmentService baseService) {
        super(baseService);
    }
}
