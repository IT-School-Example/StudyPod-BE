package com.itschool.study_pod.service;

import com.itschool.study_pod.dto.request.Comment.CommentRequest;
import com.itschool.study_pod.dto.response.CommentResponse;
import com.itschool.study_pod.entity.Board;
import com.itschool.study_pod.entity.Comment;
import com.itschool.study_pod.entity.User;
import com.itschool.study_pod.repository.BoardRepository;
import com.itschool.study_pod.repository.CommentRepository;
import com.itschool.study_pod.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;

    private final BoardRepository boardRepository;

    private final UserRepository userRepository;

    public CommentResponse create(CommentRequest request) {

        Board board = boardRepository.findById(request.getBoardId())
                .orElseThrow(()-> new EntityNotFoundException());

        User user = userRepository.findById(request.getUserId())
                .orElseThrow(()-> new EntityNotFoundException());

        Long commentId = request.getParentCommentId();
        Comment comment = null;

        if(request.getParentCommentId() != null) {
            comment = commentRepository.findById(commentId)
                    .orElseThrow(()-> new EntityNotFoundException());
        }

        return commentRepository.save(Comment.of(request, board, user, comment))
                .response();
    }

    public CommentResponse read(Long id) {
        return commentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException())
                .response();
    }

    @Transactional
    public CommentResponse update(Long id, CommentRequest commentRequest) {

        Comment entity = commentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException());

        entity.update(commentRequest);

        return entity.response();
    }

    public void delete(long id) {
        // commentRepository.deleteById(id);

        Comment findEntity = commentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException());

        commentRepository.delete(findEntity);
    }
}
