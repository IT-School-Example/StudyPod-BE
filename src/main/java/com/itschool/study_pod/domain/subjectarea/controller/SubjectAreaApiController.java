package com.itschool.study_pod.domain.subjectarea.controller;

import com.itschool.study_pod.domain.studygroup.dto.response.StudyGroupResponse;
import com.itschool.study_pod.domain.studygroup.service.StudyGroupService;
import com.itschool.study_pod.domain.subjectarea.dto.request.SubjectAreaRequest;
import com.itschool.study_pod.domain.subjectarea.dto.response.SubjectAreaResponse;
import com.itschool.study_pod.domain.subjectarea.entity.SubjectArea;
import com.itschool.study_pod.domain.subjectarea.service.SubjectAreaService;
import com.itschool.study_pod.global.base.crud.CrudController;
import com.itschool.study_pod.global.base.crud.CrudService;
import com.itschool.study_pod.global.base.dto.Header;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@Tag(name = "주제 영역", description = "주제 영역 API")
@RequestMapping("/api/subject-areas")
public class SubjectAreaApiController extends CrudController<SubjectAreaRequest, SubjectAreaResponse, SubjectArea> {

    private final SubjectAreaService subjectAreaService;
    private final StudyGroupService studyGroupService;

    @Override
    protected CrudService<SubjectAreaRequest, SubjectAreaResponse, SubjectArea> getBaseService() {
        return subjectAreaService;
    }

    @GetMapping("/filter/subject")
    @Operation(summary = "주제 영역으로 모집 중인 스터디 그룹 조회", description = "subject_area를 기준으로 모집 중인 스터디 그룹 필터링")
    public Header<List<StudyGroupResponse>> getBySubjectArea(
            @RequestParam(name = "value") String subjectAreaValue,
            @ParameterObject @PageableDefault(sort = "id", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        return studyGroupService.findBySubjectAreaAndRecruiting(subjectAreaValue, pageable);
    }
}
