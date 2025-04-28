package com.itschool.study_pod.controller.api;

import com.itschool.study_pod.controller.base.CrudController;
import com.itschool.study_pod.dto.request.SubjectAreaRequest;
import com.itschool.study_pod.dto.response.SubjectAreaResponse;
import com.itschool.study_pod.entity.SubjectArea;
import com.itschool.study_pod.service.SubjectAreaService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/subject-areas")
public class SubjectAreaApiController extends CrudController<SubjectAreaRequest, SubjectAreaResponse, SubjectArea> {

    public SubjectAreaApiController(SubjectAreaService baseService) {
        super(baseService);
    }
}
