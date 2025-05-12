package com.itschool.study_pod.repository;

import com.itschool.study_pod.entity.InterestedStudy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InterestedStudyRepository extends JpaRepository<InterestedStudy, Long> {
//    Optional<InterestedStudy> findById(Long interestedStudyList);
//
//    List<InterestedStudy> findByName(String name);
//
//    Optional<InterestedStudy> getFirstByOrderByIdDesc();
//
//    Optional<Long> countBy();
}
