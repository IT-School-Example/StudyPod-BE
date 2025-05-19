package com.itschool.study_pod.domain.adminboard.service;

import com.itschool.study_pod.domain.adminboard.dto.request.AdminBoardRequest;
import com.itschool.study_pod.domain.adminboard.dto.response.AdminBoardResponse;
import com.itschool.study_pod.domain.adminboard.entity.AdminBoard;
import com.itschool.study_pod.domain.adminboard.repository.AdminBoardRepository;
import com.itschool.study_pod.global.base.crud.CrudService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminBoardService extends CrudService<AdminBoardRequest, AdminBoardResponse, AdminBoard> {

    private final AdminBoardRepository adminBoardRepository;

    @Override
    protected JpaRepository<AdminBoard, Long> getBaseRepository() {
        return adminBoardRepository;
    }

    @Override
    protected AdminBoard toEntity(AdminBoardRequest request) {
        return AdminBoard.of(request);
    }
}
