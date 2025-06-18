package com.itschool.study_pod.domain.interestedstudy.repository;

import com.itschool.study_pod.domain.interestedstudy.entity.InterestedStudy;
import com.itschool.study_pod.domain.studygroup.entity.StudyGroup;
import com.itschool.study_pod.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.List;

@Repository
public interface InterestedStudyRepository extends JpaRepository<InterestedStudy, Long> {
    Optional<InterestedStudy> findByStudyGroupAndUser(StudyGroup studyGroup, User user);

    List<InterestedStudy> findAllByUserIdAndIsDeletedFalse(Long userId);
//    Optional<InterestedStudy> findById(Long interestedStudyList);
//
//    List<InterestedStudy> findByName(String name);
//
//    Optional<InterestedStudy> getFirstByOrderByIdDesc();
//
//    Optional<Long> countBy();
}
