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
import java.util.Optional;

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

    // 관리자 공지사항, FAQ 게시글 목록 조회
    public Header<List<AdminBoardResponse>> findByCategory(AdminBoardCategory adminBoardCategory) {
        List<AdminBoard> boards = adminBoardRepository.findByAdminBoardCategory(adminBoardCategory);

        List<AdminBoardResponse> responses = boards.stream()
                .map(AdminBoard::response)
                .toList();

        return Header.OK(responses);
    }


    // 관리자 공지사항, FAQ 게시글 상세 보기
    public Header<AdminBoardResponse> findByAdminBoardIdAndCategory(Long adminBoardId, AdminBoardCategory adminBoardCategory) {
        Optional<AdminBoard> optionalBoard = adminBoardRepository.findByIdAndAdminBoardCategory(adminBoardId, adminBoardCategory);

        if (optionalBoard.isEmpty()) {
            return Header.ERROR("해당 게시글을 찾을 수 없습니다.");
        }

        AdminBoardResponse response = optionalBoard.get().response();

        return Header.OK(response);
    }


    // region 보류
    // 관리자 ID로 공지사항, FAQ 게시글 목록 조회
    /*public Header<List<AdminBoardResponse>> findByAdminIdAndCategory(Long adminId, AdminBoardCategory category) {
        List<AdminBoard> adminBoards = adminBoardRepository.findByAdminIdAndAdminBoardCategory(adminId, category);
        List<AdminBoardResponse> responseList = adminBoards.stream()
                .map(AdminBoard::response)
                .toList();
        return Header.OK(responseList);
    }

    // 관리자 ID로 공지사항, FAQ 게시글 상세 보기
    public Header<AdminBoardResponse> findAdminBoardDetail(Long adminId, Long adminBoardId, AdminBoardCategory adminBoardCategory) {
        Optional<AdminBoard> optionalBoard = adminBoardRepository.findByIdAndAdminIdAndAdminBoardCategory(adminBoardId, adminId, adminBoardCategory);

        if (optionalBoard.isEmpty()) {
            return Header.ERROR("해당 게시글을 찾을 수 없습니다.");
        }

        AdminBoardResponse response = optionalBoard.get().response();

        return Header.OK(response);
    }*/
    // endregion
}