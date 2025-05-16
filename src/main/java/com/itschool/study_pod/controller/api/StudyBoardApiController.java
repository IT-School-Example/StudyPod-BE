package com.itschool.study_pod.controller.api;

import com.itschool.study_pod.controller.base.CrudController;
import com.itschool.study_pod.dto.Header;
import com.itschool.study_pod.dto.request.comment.CommentRequest;
import com.itschool.study_pod.dto.request.studyboard.StudyBoardRequest;
import com.itschool.study_pod.dto.response.CommentResponse;
import com.itschool.study_pod.dto.response.StudyBoardResponse;
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
@Tag(name = "스터디 게시판", description = "스터디 게시판 API")
@RequestMapping("/api/study-boards")
public class StudyBoardApiController extends CrudController<StudyBoardRequest, StudyBoardResponse, StudyBoard> {

    private final StudyBoardService studyBoardService;

    private final CommentService commentService;

    @Override
    protected CrudService<StudyBoardRequest, StudyBoardResponse, StudyBoard> getBaseService() {
        return studyBoardService;
    }

    @GetMapping("/study-groups/{studyGroupId}")
    @Operation(summary = "스터디 공지사항/자유 게시글 목록 조회", description = "스터디 그룹 ID와 스터디 게시판 카테고리로 게시글 목록 조회")
    public Header<List<StudyBoardResponse>> getStudyBoardsByStudyGroupIdAndCategory(
            @PathVariable(name = "studyGroupId") Long studyGroupId,
            @RequestParam(name = "studyBoardCategory") StudyBoardCategory studyBoardCategory
    ) {
        return studyBoardService.findByStudyGroupIdAndCategory(studyGroupId, studyBoardCategory);
    }

    @GetMapping("/study-groups/{studyGroupId}/{studyBoardId}")
    @Operation(summary = "스터디 공지사항/자유 게시글 상세 조회", description = "스터디 그룹 ID와 게시글 ID로 게시글 상세 정보를 조회")
    public Header<List<StudyBoardResponse>> getStudyBoardDetail(
            @PathVariable(name = "studyGroupId") Long studyGroupId,
            @PathVariable(name = "studyBoardId") Long studyBoardId
    ) {
        return studyBoardService.findAdminBoardDetail(studyGroupId, studyBoardId);
    }
}
