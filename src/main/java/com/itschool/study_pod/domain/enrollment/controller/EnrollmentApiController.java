package com.itschool.study_pod.domain.enrollment.controller;

import com.itschool.study_pod.global.base.crud.CrudController;
import com.itschool.study_pod.global.base.dto.Header;
import com.itschool.study_pod.domain.enrollment.dto.request.EnrollmentRequest;
import com.itschool.study_pod.domain.enrollment.dto.response.EnrollmentResponse;
import com.itschool.study_pod.domain.enrollment.entity.Enrollment;
import com.itschool.study_pod.domain.enrollment.service.EnrollmentService;
import com.itschool.study_pod.global.base.crud.CrudService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@Tag(name = "스터디 등록 내역", description = "스터디 등록 내역 API")
@RequestMapping("/api/enrollments")
public class EnrollmentApiController extends CrudController<EnrollmentRequest, EnrollmentResponse, Enrollment> {

    private final EnrollmentService enrollmentService;

    @Override
    protected CrudService<EnrollmentRequest, EnrollmentResponse, Enrollment> getBaseService() {
        return enrollmentService;
    }

    @Operation(summary = "스터디 참여 상태 변경", description = "스터디 신청자의 상태를 승인/거절로 변경합니다.")
    @PatchMapping("/{id}")
    public Header<EnrollmentResponse> updateEnrollmentStatus(
            @PathVariable Long id,
            @RequestBody EnrollmentRequest request
    ) {
        return enrollmentService.update(id, request);
    }

    @Operation(summary = "스터디 강제 퇴장", description = "등록 상태를 추방으로 수정")
    @PatchMapping("member-kick/{id}")
    public Header<EnrollmentResponse> memberKick(@PathVariable(name = "id") Long id) {
        return enrollmentService.kickMember(id);
    }
}
