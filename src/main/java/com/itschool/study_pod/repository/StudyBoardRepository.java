package com.itschool.study_pod.repository;

import com.itschool.study_pod.entity.StudyBoard;
import com.itschool.study_pod.enumclass.StudyBoardCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudyBoardRepository extends JpaRepository<StudyBoard, Long> {

    List<StudyBoard> findByStudyGroupIdAndStudyBoardCategory(Long studyGroupId, StudyBoardCategory studyBoardCategory);

    List<StudyBoard> findByIdAndStudyGroupId(Long id, Long studyGroupId);
}
