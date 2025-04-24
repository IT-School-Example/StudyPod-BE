package com.itschool.study_pod.repository;

import com.itschool.study_pod.entity.InterestedSubject;
import com.itschool.study_pod.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

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
