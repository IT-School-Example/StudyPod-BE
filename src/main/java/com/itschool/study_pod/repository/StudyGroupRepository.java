package com.itschool.study_pod.repository;

import com.itschool.study_pod.entity.StudyGroup;
import com.itschool.study_pod.enumclass.MeetingMethod;
import com.itschool.study_pod.enumclass.RecruitmentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudyGroupRepository extends JpaRepository<StudyGroup, Long> {

    // 삭제되지 않은 스터디 단건 조회
    Optional<StudyGroup> findByIdAndIsDeletedFalse(Long id);

    // 리더가 생성한 스터디 목록 조회
    List<StudyGroup> findAllByLeader_IdAndIsDeletedFalse(Long leaderId);

    // 모집 상태로 조회
    List<StudyGroup> findAllByRecruitmentStatusAndIsDeletedFalse(RecruitmentStatus recruitmentStatus);

    // 스터디 방식으로 조회
    List<StudyGroup> findAllByMeetingMethodAndIsDeletedFalse(MeetingMethod meetingMethod);

    // 주제 영역(subject area)으로 조회
    List<StudyGroup> findAllBySubjectArea_IdAndIsDeletedFalse(Long subjectAreaId);
}
