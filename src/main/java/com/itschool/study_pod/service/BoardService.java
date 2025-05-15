package com.itschool.study_pod.service;

import com.itschool.study_pod.dto.request.board.BoardRequest;
import com.itschool.study_pod.dto.response.BoardResponse;
import com.itschool.study_pod.entity.Board;
import com.itschool.study_pod.repository.BoardRepository;
import com.itschool.study_pod.service.base.CrudService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BoardService extends CrudService<BoardRequest, BoardResponse, Board> {

    private final BoardRepository boardRepository;

    @Override
    protected JpaRepository<Board, Long> getBaseRepository() {
        return boardRepository;
    }

    @Override
    protected Board toEntity(BoardRequest request) {
        return Board.of(request);
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
