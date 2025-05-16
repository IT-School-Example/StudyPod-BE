package com.itschool.study_pod.service;

import com.itschool.study_pod.dto.Header;
import com.itschool.study_pod.dto.request.studyboard.StudyBoardRequest;
import com.itschool.study_pod.dto.response.AdminBoardResponse;
import com.itschool.study_pod.dto.response.StudyBoardResponse;
import com.itschool.study_pod.entity.AdminBoard;
import com.itschool.study_pod.entity.StudyBoard;
import com.itschool.study_pod.enumclass.AdminBoardCategory;
import com.itschool.study_pod.enumclass.StudyBoardCategory;
import com.itschool.study_pod.repository.StudyBoardRepository;
import com.itschool.study_pod.service.base.CrudService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StudyBoardService extends CrudService<StudyBoardRequest, StudyBoardResponse, StudyBoard> {

    private final StudyBoardRepository studyBoardRepository;

    @Override
    protected JpaRepository<StudyBoard, Long> getBaseRepository() {
        return studyBoardRepository;
    }

    @Override
    protected StudyBoard toEntity(StudyBoardRequest requestEntity) {
        return StudyBoard.of(requestEntity);
    }

    public Header<List<StudyBoardResponse>> findByStudyGroupIdAndCategory(Long studyGroupId, StudyBoardCategory studyBoardCategory) {
        List<StudyBoard> studyBoards = studyBoardRepository.findByStudyGroupIdAndStudyBoardCategory(studyGroupId, studyBoardCategory);
        List<StudyBoardResponse> responseList = studyBoards.stream()
                .map(StudyBoard::response)
                .toList();

        return Header.OK(responseList);
    }

    public Header<List<StudyBoardResponse>> findAdminBoardDetail(Long studyGroupId, Long studyBoardId) {
        List<StudyBoard> studyBoards = studyBoardRepository.findByIdAndStudyGroupId(studyBoardId, studyGroupId);

        if (studyBoards.isEmpty()) {
            return Header.ERROR("해당 게시글을 찾을 수 없습니다.");
        }

        List<StudyBoardResponse> responseList = studyBoards.stream()
                .map(StudyBoard::response)
                .toList();

        return Header.OK(responseList);
    }


    public Optional<StudyBoard> findById(Long id) {
        return studyBoardRepository.findById(id);
    }
}
