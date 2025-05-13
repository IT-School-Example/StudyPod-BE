package com.itschool.study_pod.repository;

import com.itschool.study_pod.entity.StudyBoard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudyBoardRepository extends JpaRepository<StudyBoard, Long> {
}
