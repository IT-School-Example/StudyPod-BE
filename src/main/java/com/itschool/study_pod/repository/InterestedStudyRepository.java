package com.itschool.study_pod.repository;

import com.itschool.study_pod.entity.InterestedStudy;
import com.itschool.study_pod.entity.InterestedSubject;
import com.itschool.study_pod.entity.StudyGroup;
import com.itschool.study_pod.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface InterestedStudyRepository extends JpaRepository<InterestedStudy, Long> {
    Optional<InterestedStudy> findByStudyGroupIdAndUserId(Long studyGroupId, Long userId);
//    Optional<InterestedStudy> findById(Long interestedStudyList);
//
//    List<InterestedStudy> findByName(String name);
//
//    Optional<InterestedStudy> getFirstByOrderByIdDesc();
//
//    Optional<Long> countBy();
}
