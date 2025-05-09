package com.itschool.study_pod.service;

import com.itschool.study_pod.dto.request.board.StudyBoardRequest;
import com.itschool.study_pod.dto.response.StudyBoardResponse;
import com.itschool.study_pod.entity.StudyBoard;
import com.itschool.study_pod.repository.BoardRepository;
import com.itschool.study_pod.service.base.CrudService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BoardService extends CrudService<StudyBoardRequest, StudyBoardResponse, StudyBoard> {

    private final BoardRepository boardRepository;

    @Override
    protected JpaRepository<StudyBoard, Long> getBaseRepository() {
        return boardRepository;
    }

    @Override
    protected StudyBoard toEntity(StudyBoardRequest requestEntity) {
        return StudyBoard.of(requestEntity);
    }

    /*private final BoardRepository boardRepository;

    public BoardResponse create(BoardRequest request) {
        return boardRepository.save(Board.of(request))
                .response();
    }

    public BoardResponse read(Long id) {
        return boardRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException())
                .response();
    }

    @Transactional
    public BoardResponse update(Long id, BoardRequest boardRequest) {

        Board entity = boardRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException());

        entity.update(boardRequest);

        return entity.response();
    }

    public void delete(long id) {
        // boardRepository.deleteById(id);

        Board findEntity = boardRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException());

        boardRepository.delete(findEntity);
    }*/
}
