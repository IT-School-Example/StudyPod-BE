package com.itschool.study_pod.domain.adminboard.controller;

import com.itschool.study_pod.global.base.crud.CrudController;
import com.itschool.study_pod.domain.adminboard.dto.request.AdminBoardRequest;
import com.itschool.study_pod.domain.adminboard.dto.response.AdminBoardResponse;
import com.itschool.study_pod.domain.adminboard.entity.AdminBoard;
import com.itschool.study_pod.domain.adminboard.service.AdminBoardService;
import com.itschool.study_pod.global.base.crud.CrudService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
