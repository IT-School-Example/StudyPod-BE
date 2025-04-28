package com.itschool.study_pod.service;

import com.itschool.study_pod.dto.request.Board.BoardCreateRequest;
import com.itschool.study_pod.dto.request.Board.BoardRequest;
import com.itschool.study_pod.dto.response.BoardResponse;
import com.itschool.study_pod.entity.Board;
import com.itschool.study_pod.repository.BoardRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;

    public BoardResponse create(BoardCreateRequest request) {
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
    }
}
