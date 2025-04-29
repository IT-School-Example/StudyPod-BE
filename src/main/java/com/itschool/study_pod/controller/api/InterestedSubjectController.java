package com.itschool.study_pod.controller.api;

import com.itschool.study_pod.controller.base.CrudController;
import com.itschool.study_pod.dto.request.InterestedSubjectRequest;
import com.itschool.study_pod.dto.response.InterestedSubjectResponse;
import com.itschool.study_pod.entity.InterestedSubject;
import com.itschool.study_pod.service.InterestedSubjectService;
import com.itschool.study_pod.service.base.CrudService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Tag(name = "관심 주제", description = "관심 주제 API")
@RequestMapping("/api/interested-Subjects")
public class InterestedSubjectController extends CrudController<InterestedSubjectRequest, InterestedSubjectResponse, InterestedSubject> {

    private final InterestedSubjectService interestedSubjectService;

    @Override
    protected CrudService<InterestedSubjectRequest, InterestedSubjectResponse, InterestedSubject> getBaseService() {
        return interestedSubjectService;
    }
}
