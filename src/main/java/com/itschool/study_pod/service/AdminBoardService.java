package com.itschool.study_pod.service;

import com.itschool.study_pod.dto.request.adminboard.AdminBoardRequest;
import com.itschool.study_pod.dto.response.AdminBoardResponse;
import com.itschool.study_pod.entity.AdminBoard;
import com.itschool.study_pod.repository.AdminBoardRepository;
import com.itschool.study_pod.service.base.CrudService;
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
    protected AdminBoard toEntity(AdminBoardRequest requestEntity) {
        return AdminBoard.of(requestEntity);
    }
}
