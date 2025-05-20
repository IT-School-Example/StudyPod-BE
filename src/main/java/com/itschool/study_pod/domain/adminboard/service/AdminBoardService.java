package com.itschool.study_pod.domain.adminboard.service;

import com.itschool.study_pod.domain.adminboard.dto.request.AdminBoardRequest;
import com.itschool.study_pod.domain.adminboard.dto.response.AdminBoardResponse;
import com.itschool.study_pod.domain.adminboard.entity.AdminBoard;
import com.itschool.study_pod.domain.adminboard.repository.AdminBoardRepository;
import com.itschool.study_pod.global.base.crud.CrudService;
import com.itschool.study_pod.global.base.dto.Header;
import com.itschool.study_pod.global.enumclass.AdminBoardCategory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public Header<List<AdminBoardResponse>> findByAdminIdAndCategory(Long adminId, AdminBoardCategory category) {
        List<AdminBoard> adminBoards = adminBoardRepository.findByAdminIdAndAdminBoardCategory(adminId, category);
        List<AdminBoardResponse> responseList = adminBoards.stream()
                .map(AdminBoard::response)
                .toList();
        return Header.OK(responseList);
    }

    public Header<List<AdminBoardResponse>> findAdminBoardDetail(Long adminId, Long adminBoardId) {
        List<AdminBoard> boards = adminBoardRepository.findByIdAndAdminId(adminBoardId, adminId);

        if (boards.isEmpty()) {
            return Header.ERROR("해당 게시글을 찾을 수 없습니다.");
        }

        List<AdminBoardResponse> responseList = boards.stream()
                .map(AdminBoard::response)
                .toList();

        return Header.OK(responseList);
    }
}