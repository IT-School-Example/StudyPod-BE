package com.itschool.study_pod.domain.adminboard.repository;

import com.itschool.study_pod.domain.adminboard.entity.AdminBoard;
import com.itschool.study_pod.global.enumclass.AdminBoardCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AdminBoardRepository extends JpaRepository<AdminBoard, Long> {

    Page<AdminBoard> findByAdminBoardCategory(AdminBoardCategory adminBoardCategory, Pageable pageable);

    Optional<AdminBoard> findByIdAndAdminBoardCategory(Long id, AdminBoardCategory adminBoardCategory);

    // 보류
    // List<AdminBoard> findByAdminIdAndAdminBoardCategory(Long adminId, AdminBoardCategory adminBoardCategory);

    // 보류
    // Optional<AdminBoard> findByIdAndAdminIdAndAdminBoardCategory(Long id, Long adminId, AdminBoardCategory adminBoardCategory);
}
