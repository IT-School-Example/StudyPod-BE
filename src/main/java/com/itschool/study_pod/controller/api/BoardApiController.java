package com.itschool.study_pod.controller.api;

import com.itschool.study_pod.controller.base.CrudController;
import com.itschool.study_pod.dto.request.BoardRequest;
import com.itschool.study_pod.dto.response.BoardResponse;
import com.itschool.study_pod.entity.Board;
import com.itschool.study_pod.service.BoardService;
import com.itschool.study_pod.service.base.CrudService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
