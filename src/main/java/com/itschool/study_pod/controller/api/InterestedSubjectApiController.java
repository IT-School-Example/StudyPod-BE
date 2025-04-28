package com.itschool.study_pod.controller.api;

import com.itschool.study_pod.controller.base.CrudController;
import com.itschool.study_pod.dto.request.InterestedSubjectRequest;
import com.itschool.study_pod.dto.response.InterestedSubjectResponse;
import com.itschool.study_pod.entity.InterestedSubject;
import com.itschool.study_pod.service.InterestedSubjectService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/interested-subjects")
public class InterestedSubjectApiController extends CrudController<InterestedSubjectRequest, InterestedSubjectResponse, InterestedSubject> {

    public InterestedSubjectApiController(InterestedSubjectService baseService) {
        super(baseService);
    }
}
