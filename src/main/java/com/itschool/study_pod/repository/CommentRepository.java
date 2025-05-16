package com.itschool.study_pod.repository;

import com.itschool.study_pod.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findAllByStudyBoardId(Long studyBoardId);
}
