package com.itschool.study_pod.repository;

import com.itschool.study_pod.entity.Enrollment;
import com.itschool.study_pod.entity.User;
import com.itschool.study_pod.enumclass.EnrollmentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {
    List<Enrollment> findByUser(User user);

    // List<Enrollment> findByUserId(Long userId);

    // List<Enrollment> findByStudyGroup(StudyGroup studyGroup);

    List<Enrollment> findByStudyGroupId(Long studyGroupId);

    Optional<Enrollment> findByUserIdAndStudyGroupId(Long userId, Long studyGroupId);

    @Query("""
    SELECT e
    FROM Enrollment e
    JOIN FETCH e.studyGroup
    WHERE e.user.id = :userId
      AND (:status IS NULL OR e.status = :status)
    """)
    List<Enrollment> findWithStudyGroupByUserIdAndStatus(@Param("userId") Long userId, @Param("status") EnrollmentStatus status);

    @Query("""
    SELECT e
    FROM Enrollment e
    JOIN FETCH e.user
    WHERE e.studyGroup.id = :studyGroupId
      AND (:status IS NULL OR e.status = :status)
    """)
    List<Enrollment> findWithUserByStudyGroupIdAndStatus(@Param("studyGroupId") Long studyGroupId, @Param("status") EnrollmentStatus status);

}
