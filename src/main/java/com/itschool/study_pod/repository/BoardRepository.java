package com.itschool.study_pod.repository;

import com.itschool.study_pod.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<Board, Long> {
}
