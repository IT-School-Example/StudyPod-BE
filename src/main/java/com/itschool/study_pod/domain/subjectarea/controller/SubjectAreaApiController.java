package com.itschool.study_pod.domain.subjectarea.controller;

import com.itschool.study_pod.global.base.crud.CrudController;
import com.itschool.study_pod.global.base.crud.CrudService;
import com.itschool.study_pod.domain.subjectarea.dto.request.SubjectAreaRequest;
import com.itschool.study_pod.domain.subjectarea.dto.response.SubjectAreaResponse;
import com.itschool.study_pod.domain.subjectarea.entity.SubjectArea;
import com.itschool.study_pod.domain.subjectarea.service.SubjectAreaService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@Tag(name = "주제 영역", description = "주제 영역 API")
@RequestMapping("/api/subject-areas")
public class SubjectAreaApiController extends CrudController<SubjectAreaRequest, SubjectAreaResponse, SubjectArea> {

    private final SubjectAreaService subjectAreaService;

    @Override
    protected CrudService<SubjectAreaRequest, SubjectAreaResponse, SubjectArea> getBaseService() {
        return subjectAreaService;
    }
}
