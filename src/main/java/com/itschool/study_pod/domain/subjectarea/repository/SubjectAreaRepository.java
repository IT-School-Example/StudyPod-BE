package com.itschool.study_pod.domain.subjectarea.repository;

import com.itschool.study_pod.domain.subjectarea.entity.SubjectArea;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

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
