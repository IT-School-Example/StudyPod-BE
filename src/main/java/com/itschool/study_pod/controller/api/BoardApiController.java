package com.itschool.study_pod.controller.api;

import com.itschool.study_pod.controller.base.CrudController;
import com.itschool.study_pod.dto.request.BoardRequest;
import com.itschool.study_pod.dto.response.BoardResponse;
import com.itschool.study_pod.entity.Board;
import com.itschool.study_pod.service.BoardService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/boards")
public class BoardApiController extends CrudController<BoardRequest, BoardResponse, Board> {

    public BoardApiController(BoardService baseService) {
        super(baseService);
    }
}
