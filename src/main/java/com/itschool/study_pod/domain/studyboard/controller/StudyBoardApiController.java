package com.itschool.study_pod.domain.studyboard.controller;

import com.itschool.study_pod.global.base.crud.CrudController;
import com.itschool.study_pod.global.base.crud.CrudService;
import com.itschool.study_pod.domain.studyboard.dto.request.StudyBoardRequest;
import com.itschool.study_pod.domain.studyboard.dto.response.StudyBoardResponse;
import com.itschool.study_pod.domain.studyboard.service.StudyBoardService;
import com.itschool.study_pod.domain.studyboard.entity.StudyBoard;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@Tag(name = "스터디 게시판", description = "스터디 게시판 API")
@RequestMapping("/api/study-boards")
public class StudyBoardApiController extends CrudController<StudyBoardRequest, StudyBoardResponse, StudyBoard> {

    private final StudyBoardService studyBoardService;

    @Override
    protected CrudService<StudyBoardRequest, StudyBoardResponse, StudyBoard> getBaseService() {
        return studyBoardService;
    }
}
