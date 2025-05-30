package com.itschool.study_pod.domain.studyboard.repository;

import com.itschool.study_pod.domain.adminboard.entity.AdminBoard;
import com.itschool.study_pod.domain.studyboard.entity.StudyBoard;
import com.itschool.study_pod.global.enumclass.AdminBoardCategory;
import com.itschool.study_pod.global.enumclass.StudyBoardCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudyBoardRepository extends JpaRepository<StudyBoard, Long> {

    List<StudyBoard> findByStudyBoardCategory(StudyBoardCategory studyBoardCategory);

    Optional<StudyBoard> findByIdAndStudyBoardCategory(Long id, StudyBoardCategory studyBoardCategory);

    // 보류
    // List<StudyBoard> findByStudyGroupIdAndStudyBoardCategory(Long studyGroupId, StudyBoardCategory studyBoardCategory);

    // 보류
    // Optional<StudyBoard> findByIdAndStudyGroupIdAndStudyBoardCategory(Long id, Long studyGroupId, StudyBoardCategory studyBoardCategory);
}
