package com.itschool.study_pod.repository;

import com.itschool.study_pod.entity.InterestedSubject;
import com.itschool.study_pod.entity.SubjectArea;
import com.itschool.study_pod.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SubjectAreaRepository extends JpaRepository<SubjectArea, Long> {
//    Optional<SubjectArea> findById(Long subjectArea);
//
//    List<InterestedSubject> findByName(String name);
//
//    Optional<SubjectArea> getFirstByOrderByIdDesc();
//
//    Optional<Long> countBy();
}
