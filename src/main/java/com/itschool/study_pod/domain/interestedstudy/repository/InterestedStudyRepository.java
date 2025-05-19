package com.itschool.study_pod.domain.interestedstudy.repository;

import com.itschool.study_pod.domain.interestedstudy.entity.InterestedStudy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface InterestedStudyRepository extends JpaRepository<InterestedStudy, Long> {
    Optional<InterestedStudy> findByStudyGroupAndUser(StudyGroup studyGroup, User user);
//    Optional<InterestedStudy> findById(Long interestedStudyList);
//
//    List<InterestedStudy> findByName(String name);
//
//    Optional<InterestedStudy> getFirstByOrderByIdDesc();
//
//    Optional<Long> countBy();
}
