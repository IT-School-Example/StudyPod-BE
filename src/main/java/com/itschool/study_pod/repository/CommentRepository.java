package com.itschool.study_pod.repository;

import com.itschool.study_pod.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
