package com.itschool.study_pod.controller.api;

import com.itschool.study_pod.controller.base.CrudController;
import com.itschool.study_pod.dto.request.board.StudyBoardRequest;
import com.itschool.study_pod.dto.response.StudyBoardResponse;
import com.itschool.study_pod.entity.StudyBoard;
import com.itschool.study_pod.service.BoardService;
import com.itschool.study_pod.service.base.CrudService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@Tag(name = "게시판", description = "게시판 API")
@RequestMapping("/api/boards")
public class BoardApiController extends CrudController<StudyBoardRequest, StudyBoardResponse, StudyBoard> {

    private final BoardService boardService;

    @Override
    protected CrudService<StudyBoardRequest, StudyBoardResponse, StudyBoard> getBaseService() {
        return boardService;
    }
}
