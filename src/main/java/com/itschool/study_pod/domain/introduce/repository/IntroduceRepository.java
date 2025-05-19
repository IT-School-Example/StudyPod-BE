package com.itschool.study_pod.domain.introduce.repository;

import com.itschool.study_pod.domain.introduce.entity.Introduce;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IntroduceRepository extends JpaRepository<Introduce, Long> {
    Optional<Introduce> findByStudyGroupId(Long StudyGroupId);
}
