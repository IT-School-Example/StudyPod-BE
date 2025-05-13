package com.itschool.study_pod.repository;

import com.itschool.study_pod.entity.StudyGroup;
import com.itschool.study_pod.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    List<User> findByStudyGroup(StudyGroup studyGroup);
}
