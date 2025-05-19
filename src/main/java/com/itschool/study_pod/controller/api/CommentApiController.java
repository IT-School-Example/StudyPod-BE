package com.itschool.study_pod.controller.api;

import com.itschool.study_pod.controller.base.CrudController;
import com.itschool.study_pod.dto.Header;
import com.itschool.study_pod.dto.request.comment.CommentRequest;
import com.itschool.study_pod.dto.response.CommentResponse;
import com.itschool.study_pod.entity.Comment;
import com.itschool.study_pod.entity.StudyBoard;
import com.itschool.study_pod.enumclass.StudyBoardCategory;
import com.itschool.study_pod.service.CommentService;
import com.itschool.study_pod.service.StudyBoardService;
import com.itschool.study_pod.service.base.CrudService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@Tag(name = "댓글", description = "댓글 API")
@RequestMapping("/api/comments")
public class CommentApiController extends CrudController<CommentRequest, CommentResponse, Comment> {

    private final CommentService commentService;

    @Override
    protected CrudService<CommentRequest, CommentResponse, Comment> getBaseService() {
        return commentService;
    }
}
