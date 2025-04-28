package com.itschool.study_pod.controller.api;

import com.itschool.study_pod.controller.base.CrudController;
import com.itschool.study_pod.dto.request.StudyGroupRequest;
import com.itschool.study_pod.dto.response.StudyGroupResponse;
import com.itschool.study_pod.entity.StudyGroup;
import com.itschool.study_pod.service.StudyGroupService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/study-groups")
public class StudyGroupApiController extends CrudController<StudyGroupRequest, StudyGroupResponse, StudyGroup> {

    public StudyGroupApiController(StudyGroupService baseService) {
        super(baseService);
    }
}
