package com.itschool.study_pod.domain.studyboard.service;

import com.itschool.study_pod.domain.studyboard.repository.StudyBoardRepository;
import com.itschool.study_pod.global.base.crud.CrudService;
import com.itschool.study_pod.domain.studyboard.dto.request.StudyBoardRequest;
import com.itschool.study_pod.domain.studyboard.dto.response.StudyBoardResponse;
import com.itschool.study_pod.domain.studyboard.entity.StudyBoard;
import com.itschool.study_pod.global.base.dto.Header;
import com.itschool.study_pod.global.enumclass.StudyBoardCategory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
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

    public Header<List<StudyBoardResponse>> findByCategory(StudyBoardCategory studyBoardCategory, Pageable pageable) {
        Page<StudyBoard> studyBoards = studyBoardRepository.findByStudyBoardCategory(studyBoardCategory, pageable);

        return convertPageToList(studyBoards);
    }

    public Header<StudyBoardResponse> findByStudyBoardIdAndCategory(Long studyBoardId, StudyBoardCategory studyBoardCategory) {
        Optional<StudyBoard> optionalBoard = studyBoardRepository.findByIdAndStudyBoardCategory(studyBoardId, studyBoardCategory);

        if (optionalBoard.isEmpty()) {
            return Header.ERROR("해당 게시글을 찾을 수 없습니다.");
        }

        StudyBoardResponse response = optionalBoard.get().response();

        return Header.OK(response);
    }

    public Optional<StudyBoard> findById(Long id) {
        return studyBoardRepository.findById(id);
    }


    // 스터디 공지사항, 자유 게시글 목록 조회
    public Header<List<StudyBoardResponse>> findByStudyGroupIdAndCategory(Long studyGroupId, StudyBoardCategory studyBoardCategory) {
        List<StudyBoard> studyBoards = studyBoardRepository.findByStudyGroupIdAndStudyBoardCategory(studyGroupId, studyBoardCategory);
        List<StudyBoardResponse> responseList = studyBoards.stream()
                .map(StudyBoard::response)
                .toList();

        return Header.OK(responseList);
    }
    // 스터디 공지사항, 자유 게시글 상세 조회
    public Header<StudyBoardResponse> findStudyBoardDetail(Long studyGroupId, Long studyBoardId, StudyBoardCategory category) {
        return studyBoardRepository.findByIdAndStudyGroupIdAndStudyBoardCategory(studyBoardId, studyGroupId, category)
                .map(board -> Header.OK(board.response()))
                .orElseGet(() -> Header.ERROR("해당 게시글을 찾을 수 없습니다."));
    }
}