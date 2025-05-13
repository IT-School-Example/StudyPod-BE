package com.itschool.study_pod.repository;

import com.itschool.study_pod.entity.Introduce;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IntroduceRepository extends JpaRepository<Introduce, Long> {
}
