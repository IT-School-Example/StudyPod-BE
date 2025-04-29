package com.itschool.study_pod.service;

import com.itschool.study_pod.dto.request.CommentRequest;
import com.itschool.study_pod.dto.response.CommentResponse;
import com.itschool.study_pod.entity.Comment;
import com.itschool.study_pod.repository.BoardRepository;
import com.itschool.study_pod.repository.CommentRepository;
import com.itschool.study_pod.repository.UserRepository;
import com.itschool.study_pod.service.base.CrudService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;

    public CommentResponse create(CommentRequest request) {

        return commentRepository.save(Comment.of(request))
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
