package com.itschool.study_pod.domain.comment.service;

import com.itschool.study_pod.domain.comment.dto.request.CommentRequest;
import com.itschool.study_pod.domain.comment.dto.response.CommentResponse;
import com.itschool.study_pod.domain.comment.entity.Comment;
import com.itschool.study_pod.domain.board.repository.BoardRepository;
import com.itschool.study_pod.domain.comment.repository.CommentRepository;
import com.itschool.study_pod.domain.user.repository.UserRepository;
import com.itschool.study_pod.global.base.crud.CrudService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentService extends CrudService<CommentRequest, CommentResponse, Comment> {

    private final CommentRepository commentRepository;

    private final BoardRepository boardRepository;

    private final UserRepository userRepository;


    @Override
    protected JpaRepository<Comment, Long> getBaseRepository() {
        return commentRepository;
    }

    @Override
    protected Comment toEntity(CommentRequest request) {
        return Comment.of(request);
    }

    /*private final CommentRepository commentRepository;

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
    }*/
}
