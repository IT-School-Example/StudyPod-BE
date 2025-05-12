package com.itschool.study_pod.controller.api;

import com.itschool.study_pod.controller.base.CrudController;
import com.itschool.study_pod.dto.request.subjectarea.SubjectAreaRequest;
import com.itschool.study_pod.dto.response.SubjectAreaResponse;
import com.itschool.study_pod.entity.SubjectArea;
import com.itschool.study_pod.service.SubjectAreaService;
import com.itschool.study_pod.service.base.CrudService;
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
