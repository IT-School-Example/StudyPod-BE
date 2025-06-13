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
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
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

    @GetMapping("/notices")
    @Operation(summary = "공지사항 게시글 목록 조회", description = "공지사항 카테고리에 해당하는 게시글 목록 조회")
    public Header<List<AdminBoardResponse>> getNotices(
            @ParameterObject @PageableDefault(sort = "id", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        return adminBoardService.findByCategory(AdminBoardCategory.NOTICE, pageable);
    }

    @GetMapping("/faqs")
    @Operation(summary = "공지사항 FAQ 목록 조회", description = "FAQ 카테고리에 해당하는 게시글 목록 조회")
    public Header<List<AdminBoardResponse>> getFaqs(
            @ParameterObject @PageableDefault(sort = "id", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        return adminBoardService.findByCategory(AdminBoardCategory.FAQ, pageable);
    }

    @GetMapping("/notices/{adminBoardId}")
    @Operation(summary = "공지사항 게시글 상세 보기", description = "관리자 게시판 ID로 공지사항 게시글 상세 보기")
    public Header<AdminBoardResponse> getNoticeDetailByAdminBoardId(
            @PathVariable(name = "adminBoardId") Long adminBoardId
    ) {
        return adminBoardService.findByAdminBoardIdAndCategory(adminBoardId, AdminBoardCategory.NOTICE);
    }

    @GetMapping("/faqs/{adminBoardId}")
    @Operation(summary = "FAQ 게시글 상세 보기", description = "관리자 게시판 ID로 FAQ 게시글 상세 보기")
    public Header<AdminBoardResponse> getFaqDetailByAdminBoardId(
            @PathVariable(name = "adminBoardId") Long adminBoardId
    ) {
        return adminBoardService.findByAdminBoardIdAndCategory(adminBoardId, AdminBoardCategory.FAQ);
    }

    // region 보류
    /*@GetMapping("/admins/{adminId}/notices")
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
    }*/

    // endregion
}
