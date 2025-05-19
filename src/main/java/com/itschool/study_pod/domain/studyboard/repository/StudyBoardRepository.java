package com.itschool.study_pod.domain.studyboard.repository;

import com.itschool.study_pod.domain.studyboard.entity.StudyBoard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudyBoardRepository extends JpaRepository<StudyBoard, Long> {
}
