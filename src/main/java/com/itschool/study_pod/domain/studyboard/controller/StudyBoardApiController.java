package com.itschool.study_pod.domain.studyboard.controller;

import com.itschool.study_pod.domain.comment.dto.request.CommentRequest;
import com.itschool.study_pod.domain.comment.dto.response.CommentResponse;
import com.itschool.study_pod.domain.comment.service.CommentService;
import com.itschool.study_pod.domain.studyboard.dto.request.StudyBoardRequest;
import com.itschool.study_pod.domain.studyboard.dto.response.StudyBoardResponse;
import com.itschool.study_pod.domain.studyboard.entity.StudyBoard;
import com.itschool.study_pod.domain.studyboard.service.StudyBoardService;
import com.itschool.study_pod.global.base.crud.CrudController;
import com.itschool.study_pod.global.base.crud.CrudService;
import com.itschool.study_pod.global.base.dto.Header;
import com.itschool.study_pod.global.enumclass.StudyBoardCategory;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
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
@Tag(name = "스터디 게시판", description = "스터디 게시판 API")
@RequestMapping("/api/study-boards")
public class StudyBoardApiController extends CrudController<StudyBoardRequest, StudyBoardResponse, StudyBoard> {

    private final StudyBoardService studyBoardService;

    private final CommentService commentService;

    @Override
    protected CrudService<StudyBoardRequest, StudyBoardResponse, StudyBoard> getBaseService() {
        return studyBoardService;
    }

    @GetMapping("/notices")
    @Operation(summary = "스터디 공지사항 목록 조회", description = "공지사항 카테고리에 해당하는 게시글 목록 조회")
    public Header<List<StudyBoardResponse>> getStudyNotices(
            @ParameterObject @PageableDefault(sort = "id", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        return studyBoardService.findByCategory(StudyBoardCategory.NOTICE, pageable);
    }

    @GetMapping("/frees")
    @Operation(summary = "스터디 자유 게시글 목록 조회", description = "자유 카테고리에 해당하는 게시글 목록 조회")
    public Header<List<StudyBoardResponse>> getStudyFrees(
            @ParameterObject @PageableDefault(sort = "id", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        return studyBoardService.findByCategory(StudyBoardCategory.FREE, pageable);
    }

    @GetMapping("/notices/{studyBoardId}")
    @Operation(summary = "스터디 공지사항 상세 조회", description = "스터디 게시판 ID로 상세 조회")
    public Header<StudyBoardResponse> getStudyNoticeDetail(
            @PathVariable(name = "studyBoardId") Long studyBoardId
    ) {
        return studyBoardService.findByStudyBoardIdAndCategory(studyBoardId, StudyBoardCategory.NOTICE);
    }

    @GetMapping("/frees/{studyBoardId}")
    @Operation(summary = "스터디 자유 게시글 상세 조회", description = "스터디 게시판 ID로 상세 조회")
    public Header<StudyBoardResponse> getStudyFreeDetail(
            @PathVariable(name = "studyBoardId") Long studyBoardId
    ) {
        return studyBoardService.findByStudyBoardIdAndCategory(studyBoardId, StudyBoardCategory.FREE);
    }

    // region 보류
    @GetMapping("/study-groups/{studyGroupId}/notices")
    @Operation(summary = "스터디 공지사항 목록 조회", description = "스터디 그룹 ID로 공지사항 목록을 조회")
    public Header<List<StudyBoardResponse>> getStudyNotices(
            @PathVariable(name = "studyGroupId") Long studyGroupId
    ) {
        return studyBoardService.findByStudyGroupIdAndCategory(studyGroupId, StudyBoardCategory.NOTICE);
    }

    @GetMapping("/study-groups/{studyGroupId}/frees")
    @Operation(summary = "스터디 자유 게시글 목록 조회", description = "스터디 그룹 ID로 자유 게시글 목록을 조회")
    public Header<List<StudyBoardResponse>> getStudyFrees(
            @PathVariable(name = "studyGroupId") Long studyGroupId
    ) {
        return studyBoardService.findByStudyGroupIdAndCategory(studyGroupId, StudyBoardCategory.FREE);
    }

    @GetMapping("/study-groups/{studyGroupId}/notices/{studyBoardId}")
    @Operation(summary = "스터디 공지사항 상세 조회", description = "스터디 그룹 ID와 공지사항 ID로 상세 조회")
    public Header<StudyBoardResponse> getStudyNoticeDetail(
            @PathVariable(name = "studyGroupId") Long studyGroupId,
            @PathVariable(name = "studyBoardId") Long studyBoardId
    ) {
        return studyBoardService.findStudyBoardDetail(studyGroupId, studyBoardId, StudyBoardCategory.NOTICE);
    }

    @GetMapping("/study-groups/{studyGroupId}/frees/{studyBoardId}")
    @Operation(summary = "스터디 자유 게시글 상세 조회", description = "스터디 그룹 ID와 자유 게시글 ID로 상세 조회")
    public Header<StudyBoardResponse> getStudyFreeDetail(
            @PathVariable(name = "studyGroupId") Long studyGroupId,
            @PathVariable(name = "studyBoardId") Long studyBoardId
    ) {
        return studyBoardService.findStudyBoardDetail(studyGroupId, studyBoardId, StudyBoardCategory.FREE);
    }
    // endregion

    // 자유게시판 댓글 생성
    @PostMapping("/comments")
    @Operation(summary = "자유게시판 댓글 생성")
    public Header<CommentResponse> createFreeBoardComment(
            @Valid @RequestBody CommentRequest request
    ) {
        return commentService.createCommentIfFreeBoard(request);
    }

    // 자유게시판 댓글 전체 조회
    @GetMapping("/{studyBoardId}/comments")
    @Operation(summary = "자유게시판 댓글 목록 조회")
    public Header<List<CommentResponse>> getCommentsByFreeBoard(
            @PathVariable(name = "studyBoardId") Long studyBoardId
    ) {
        return commentService.getCommentsByFreeBoardId(studyBoardId);
    }

    // 자유게시판 댓글 수정
    @PutMapping("/{studyBoardId}/comments/{commentId}")
    @Operation(summary = "자유게시판 댓글 수정")
    public Header<CommentResponse> updateFreeBoardComment(
            @PathVariable(name = "studyBoardId") Long studyBoardId,
            @PathVariable(name = "commentId") Long commentId,
            @Valid @RequestBody CommentRequest request
    ) {
        return commentService.updateCommentIfFreeBoard(studyBoardId, commentId, request);
    }

    // 자유게시판 댓글 삭제
    @DeleteMapping("/{studyBoardId}/comments/{commentId}")
    @Operation(summary = "자유게시판 댓글 삭제")
    public Header<CommentResponse> deleteFreeBoardComment(
            @PathVariable(name = "studyBoardId") Long studyBoardId,
            @PathVariable(name = "commentId") Long commentId
    ) {
        return commentService.deleteCommentIfFreeBoard(studyBoardId, commentId);
    }
    @GetMapping("/study-groups/{studyGroupId}")
    @Operation(summary = "스터디 그룹 전체 게시글 조회", description = "스터디 그룹 ID로 모든 게시글을 카테고리 구분 없이 조회합니다.")
    public Header<List<StudyBoardResponse>> getStudyBoardsByGroup(
            @PathVariable Long studyGroupId,
            @ParameterObject @PageableDefault(sort = "id", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        return studyBoardService.findByStudyGroupId(studyGroupId, pageable);
    }
}
