package com.itschool.study_pod.domain.adminboard.controller;

import com.itschool.study_pod.domain.adminboard.dto.request.AdminBoardRequest;
import com.itschool.study_pod.domain.adminboard.dto.response.AdminBoardResponse;
import com.itschool.study_pod.domain.adminboard.entity.AdminBoard;
import com.itschool.study_pod.domain.adminboard.service.AdminBoardService;
import com.itschool.study_pod.global.base.crud.CrudController;
import com.itschool.study_pod.global.base.crud.CrudService;
import com.itschool.study_pod.global.base.dto.Header;
import com.itschool.study_pod.global.enumclass.AdminBoardCategory;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@Tag(name = "관리자 게시판", description = "관리자 게시판 API")
@RequestMapping("/api/admin-boards")
public class AdminBoardApiController extends CrudController<AdminBoardRequest, AdminBoardResponse, AdminBoard> {

    private final AdminBoardService adminBoardService;

    @Override
    protected CrudService<AdminBoardRequest, AdminBoardResponse, AdminBoard> getBaseService() {
        return adminBoardService;
    }

    @GetMapping("/admins/{adminId}/notices")
    @Operation(summary = "관리자 공지사항 게시글 목록 조회", description = "관리자 ID로 공지사항 게시글 목록을 조회")
    public Header<List<AdminBoardResponse>> getNoticesByAdminId(
            @PathVariable(name = "adminId") Long adminId
    ) {
        return adminBoardService.findByAdminIdAndCategory(adminId, AdminBoardCategory.NOTICE);
    }

    @GetMapping("/admins/{adminId}/faqs")
    @Operation(summary = "관리자 FAQ 게시글 목록 조회", description = "관리자 ID로 FAQ 게시글 목록을 조회")
    public Header<List<AdminBoardResponse>> getFaqsByAdminId(
            @PathVariable(name = "adminId") Long adminId
    ) {
        return adminBoardService.findByAdminIdAndCategory(adminId, AdminBoardCategory.FAQ);
    }

    @GetMapping("/admins/{adminId}/notices/{adminBoardId}")
    @Operation(summary = "관리자 공지사항 게시글 상세 보기", description = "관리자 ID와 공지사항 게시판 ID로 게시글 상세 보기")
    public Header<AdminBoardResponse> getNoticeDetail(
            @PathVariable(name = "adminId") Long adminId,
            @PathVariable(name = "adminBoardId") Long adminBoardId
    ) {
        return adminBoardService.findAdminBoardDetail(adminId, adminBoardId, AdminBoardCategory.NOTICE);
    }

    @GetMapping("/admins/{adminId}/faqs/{adminBoardId}")
    @Operation(summary = "관리자 FAQ 게시글 상세 보기", description = "관리자 ID와 FAQ 게시판 ID로 게시글 상세 보기")
    public Header<AdminBoardResponse> getFaqDetail(
            @PathVariable(name = "adminId") Long adminId,
            @PathVariable(name = "adminBoardId") Long adminBoardId
    ) {
        return adminBoardService.findAdminBoardDetail(adminId, adminBoardId, AdminBoardCategory.FAQ);
    }
}
