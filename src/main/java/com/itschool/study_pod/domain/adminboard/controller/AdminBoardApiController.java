package com.itschool.study_pod.controller.api;

import com.itschool.study_pod.controller.base.CrudController;
import com.itschool.study_pod.dto.Header;
import com.itschool.study_pod.dto.request.adminboard.AdminBoardRequest;
import com.itschool.study_pod.dto.response.AdminBoardResponse;
import com.itschool.study_pod.entity.AdminBoard;
import com.itschool.study_pod.enumclass.AdminBoardCategory;
import com.itschool.study_pod.service.AdminBoardService;
import com.itschool.study_pod.service.base.CrudService;
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

    @GetMapping("/admins/{adminId}")
    @Operation(summary = "관리자 공지사항/FAQ 게시글 목록 조회", description = "관리자 ID와 관리자 게시판 카테고리로 게시글 목록을 조회")
    public Header<List<AdminBoardResponse>> getAdminBoardsByAdminIdAndCategory(
            @PathVariable(name = "adminId") Long adminId,
            @RequestParam(name = "adminBoardCategory") AdminBoardCategory adminBoardCategory
    ) {
        return adminBoardService.findByAdminIdAndCategory(adminId, adminBoardCategory);
    }

    @GetMapping("/admins/{adminId}/{adminBoardId}")
    @Operation(summary = "관리자 공지사항/FAQ 게시글 상세 보기", description = "관리자 ID와 관리자 게시판 ID로 게시글 상세 보기")
    public Header<List<AdminBoardResponse>> getAdminBoardDetail(
            @PathVariable(name = "adminId") Long adminId,
            @PathVariable(name = "adminBoardId") Long adminBoardId
    ) {
        return adminBoardService.findAdminBoardDetail(adminId, adminBoardId);
    }
}
