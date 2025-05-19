package com.itschool.study_pod.domain.board.controller;

import com.itschool.study_pod.global.base.crud.CrudController;
import com.itschool.study_pod.domain.board.dto.request.BoardRequest;
import com.itschool.study_pod.domain.board.dto.response.BoardResponse;
import com.itschool.study_pod.domain.board.entity.Board;
import com.itschool.study_pod.domain.board.service.BoardService;
import com.itschool.study_pod.global.base.crud.CrudService;
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
public class BoardApiController extends CrudController<BoardRequest, BoardResponse, Board> {

    private final BoardService boardService;

    @Override
    protected CrudService<BoardRequest, BoardResponse, Board> getBaseService() {
        return boardService;
    }
}
