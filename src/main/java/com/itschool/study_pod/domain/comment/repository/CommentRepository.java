package com.itschool.study_pod.domain.comment.repository;

import com.itschool.study_pod.domain.comment.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findAllByStudyBoardId(Long studyBoardId);
    List<Comment> findAllByParentCommentId(Long parentCommentId);
}
