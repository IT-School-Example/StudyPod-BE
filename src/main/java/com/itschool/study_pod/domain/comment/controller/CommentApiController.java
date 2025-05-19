package com.itschool.study_pod.domain.comment.controller;

import com.itschool.study_pod.domain.comment.dto.request.CommentRequest;
import com.itschool.study_pod.domain.comment.dto.response.CommentResponse;
import com.itschool.study_pod.domain.comment.entity.Comment;
import com.itschool.study_pod.domain.comment.service.CommentService;
import com.itschool.study_pod.global.base.crud.CrudController;
import com.itschool.study_pod.global.base.crud.CrudService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
