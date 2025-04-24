package com.itschool.study_pod.repository;

import com.itschool.study_pod.entity.Enrollment;
import com.itschool.study_pod.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {
    List<Enrollment> findByUser(User user);
    List<Enrollment> findByStudyGroupId(Long studyGroupId);
    Optional<Enrollment> findByUserIdAndStudyGroupId(Long userId, Long studyGroupId);
}
