package com.itschool.study_pod.domain.studyboard.service;

import com.itschool.study_pod.global.base.crud.CrudService;
import com.itschool.study_pod.domain.studyboard.dto.request.StudyBoardRequest;
import com.itschool.study_pod.domain.studyboard.dto.response.StudyBoardResponse;
import com.itschool.study_pod.domain.studyboard.entity.StudyBoard;
import com.itschool.study_pod.domain.studyboard.repository.StudyBoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StudyBoardService extends CrudService<StudyBoardRequest, StudyBoardResponse, StudyBoard> {

    private final StudyBoardRepository studyBoardRepository;

    @Override
    protected JpaRepository<StudyBoard, Long> getBaseRepository() {
        return studyBoardRepository;
    }

    @Override
    protected StudyBoard toEntity(StudyBoardRequest request) {
        return StudyBoard.of(request);
    }
}
