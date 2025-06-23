package com.itschool.study_pod.domain.comment.service;

import com.itschool.study_pod.domain.comment.dto.request.CommentRequest;
import com.itschool.study_pod.domain.comment.dto.response.CommentResponse;
import com.itschool.study_pod.domain.comment.entity.Comment;
import com.itschool.study_pod.domain.comment.repository.CommentRepository;
import com.itschool.study_pod.domain.studyboard.entity.StudyBoard;
import com.itschool.study_pod.domain.studyboard.service.StudyBoardService;
import com.itschool.study_pod.global.base.crud.CrudService;
import com.itschool.study_pod.global.base.dto.Header;
import com.itschool.study_pod.global.enumclass.StudyBoardCategory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentService extends CrudService<CommentRequest, CommentResponse, Comment> {

    private final CommentRepository commentRepository;
    private final StudyBoardService studyBoardService;


    @Override
    protected JpaRepository<Comment, Long> getBaseRepository() {
        return commentRepository;
    }

    @Override
    protected Comment toEntity(CommentRequest requestEntity) {
        return Comment.of(requestEntity);
    }

    // 자유게시판 여부 검증
    private void validateFreeBoard(Long studyBoardId) {
        StudyBoard board = studyBoardService.findById(studyBoardId)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시판이 존재하지 않습니다."));
        if (board.getStudyBoardCategory() != StudyBoardCategory.FREE) {
            throw new IllegalStateException("댓글은 자유게시판에만 작성할 수 있습니다.");
        }
    }

    // 댓글 생성
    public Header<CommentResponse> createCommentIfFreeBoard(Long studyBoardId, CommentRequest request) {
        validateFreeBoard(studyBoardId);
        request.getStudyBoard().setId(studyBoardId);
        Comment comment = commentRepository.save(Comment.of(request));
        return Header.OK(comment.response());
    }

    // 댓글 목록 조회
    public Header<List<CommentResponse>> getCommentsByFreeBoardId(Long studyBoardId) {
        validateFreeBoard(studyBoardId);
        List<Comment> comments = commentRepository.findAllByStudyBoardId(studyBoardId);
        List<CommentResponse> responseList = comments.stream()
                .map(Comment::response)
                .collect(Collectors.toList());
        return Header.OK(responseList);
    }

    // 댓글 수정
    public Header<CommentResponse> updateCommentIfFreeBoard(Long studyBoardId, Long commentId, CommentRequest request) {
        validateFreeBoard(studyBoardId);
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("댓글이 존재하지 않습니다."));
        comment.update(request);
        Comment updated = commentRepository.save(comment);
        return Header.OK(updated.response());
    }

    // 댓글 삭제
    public Header<CommentResponse> deleteCommentIfFreeBoard(Long studyBoardId, Long commentId) {
        validateFreeBoard(studyBoardId);
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("댓글이 존재하지 않습니다."));

        // 대댓글까지 모두 삭제
        deleteCommentAndChildren(commentId);

        return Header.OK(comment.response());
    }

    // 댓글과 대댓글들까지 삭제
    @Transactional
    public void deleteCommentAndChildren(Long commentId) {

        List<Comment> childComments = commentRepository.findAllByParentCommentId(commentId);

        // 자식 댓글들에 대해 재귀 삭제 호출
        for (Comment child : childComments) {
            deleteCommentAndChildren(child.getId());
        }

        // 현재 댓글 삭제
        commentRepository.deleteById(commentId);
    }
}