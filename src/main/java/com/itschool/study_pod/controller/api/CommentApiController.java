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

    private final StudyBoardService studyBoardService;

    @Override
    protected CrudService<CommentRequest, CommentResponse, Comment> getBaseService() {
        return commentService;
    }

    private void validateFreeBoard(Long studyBoardId) {
        StudyBoard board = studyBoardService.findById(studyBoardId)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시판이 존재하지 않습니다."));
        if (board.getStudyBoardCategory() != StudyBoardCategory.FREE) {
            throw new IllegalStateException("댓글은 자유게시판에만 작성할 수 있습니다.");
        }
    }

    // ✅ 자유게시판 댓글 생성
    @PostMapping("/study-board/{studyBoardId}")
    @Operation(summary = "자유게시판 댓글 생성")
    public Header<CommentResponse> createForFreeBoard(
            @PathVariable(name = "studyBoardId") Long studyBoardId,
            @RequestBody @Valid Header<CommentRequest> request
    ) {
        validateFreeBoard(studyBoardId);
        request.getData().getStudyBoard().setId(studyBoardId);
        return commentService.create(request);
    }

    // ✅ 자유게시판 댓글 전체 조회
    @GetMapping("/study-board/{studyBoardId}")
    @Operation(summary = "자유게시판 댓글 전체 조회")
    public Header<List<CommentResponse>> getAllForFreeBoard(
            @PathVariable Long studyBoardId
    ) {
        validateFreeBoard(studyBoardId);
        return commentService.findAllByStudyBoardId(studyBoardId);
    }

    // ✅ 자유게시판 댓글 단건 조회
    @GetMapping("/study-board/{studyBoardId}/{id}")
    @Operation(summary = "자유게시판 댓글 단건 조회")
    public Header<CommentResponse> readForFreeBoard(
            @PathVariable(name = "studyBoardId") Long studyBoardId,
            @PathVariable(name = "id") Long id
    ) {
        validateFreeBoard(studyBoardId);
        return commentService.read(id);
    }

    // ✅ 자유게시판 댓글 수정
    @PutMapping("/study-board/{studyBoardId}/{id}")
    @Operation(summary = "자유게시판 댓글 수정")
    public Header<CommentResponse> updateForFreeBoard(
            @PathVariable(name = "studyBoardId") Long studyBoardId,
            @PathVariable(name = "id") Long id,
            @RequestBody @Valid Header<CommentRequest> request
    ) {
        validateFreeBoard(studyBoardId);
        request.getData().getStudyBoard().setId(studyBoardId);
        return commentService.update(id, request);
    }

    // ✅ 자유게시판 댓글 삭제
    @DeleteMapping("/study-board/{studyBoardId}/{id}")
    @Operation(summary = "자유게시판 댓글 삭제")
    public Header<Void> deleteForFreeBoard(
            @PathVariable(name = "studyBoardId") Long studyBoardId,
            @PathVariable(name = "id") Long id
    ) {
        validateFreeBoard(studyBoardId);
        return commentService.delete(id);
    }
}
