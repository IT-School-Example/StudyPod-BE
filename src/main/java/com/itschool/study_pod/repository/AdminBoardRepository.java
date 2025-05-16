package com.itschool.study_pod.repository;

import com.itschool.study_pod.entity.AdminBoard;
import com.itschool.study_pod.enumclass.AdminBoardCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdminBoardRepository extends JpaRepository<AdminBoard, Long> {

    List<AdminBoard> findByAdminIdAndAdminBoardCategory(Long adminId, AdminBoardCategory adminBoardCategory);

    //List<AdminBoard> findByAdminIdAndAdminBoardCategory(Long studyBoardId, AdminBoardCategory adminBoardCategory);

    List<AdminBoard> findByIdAndAdminId(Long id, Long adminId);
}
