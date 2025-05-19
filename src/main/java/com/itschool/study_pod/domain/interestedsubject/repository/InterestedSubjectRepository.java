package com.itschool.study_pod.domain.interestedsubject.repository;

import com.itschool.study_pod.domain.interestedsubject.entity.InterestedSubject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InterestedSubjectRepository extends JpaRepository<InterestedSubject, Long> {
//    Optional<InterestedSubject> findById(Long interestedSubject);
//
//    List<InterestedSubject> findByName(String name);
//
//    Optional<InterestedSubject> getFirstByOrderByIdDesc();
//
//    Optional<Long> countBy();
}
