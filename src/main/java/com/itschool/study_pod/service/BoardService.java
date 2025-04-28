package com.itschool.study_pod.service;

import com.itschool.study_pod.dto.request.BoardRequest;
import com.itschool.study_pod.dto.response.BoardResponse;
import com.itschool.study_pod.entity.Board;
import com.itschool.study_pod.repository.BoardRepository;
import com.itschool.study_pod.service.base.CrudService;
import org.springframework.stereotype.Service;

@Service
public class BoardService extends CrudService<BoardRequest, BoardResponse, Board> {

    public BoardService(BoardRepository baseRepository) {
        super(baseRepository);
    }

    @Override
    protected Board toEntity(BoardRequest requestEntity) {
        return Board.of(requestEntity);
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
