package com.itschool.study_pod.domain.adminboard.repository;

import com.itschool.study_pod.domain.adminboard.entity.AdminBoard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminBoardRepository extends JpaRepository<AdminBoard, Long> {
}
