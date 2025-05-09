package com.itschool.study_pod.repository;

import com.itschool.study_pod.entity.StudyGroup;
import com.itschool.study_pod.enumclass.MeetingMethod;
import com.itschool.study_pod.enumclass.RecruitmentStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudyGroupRepository extends JpaRepository<StudyGroup, Long> {

    // 리더가 생성한 스터디 목록 조회
    List<StudyGroup> findAllByLeaderId(Long leaderId);

    // 동적 쿼리로 스터디 그룹 목록 조회
    @Query("""
    SELECT sg FROM StudyGroup sg
    WHERE (
        (:keyword IS NULL OR LOWER(sg.title) LIKE LOWER(CONCAT('%', :keyword, '%')))
        OR (:keyword IS NULL OR :keyword IN elements(sg.keywords))
    )
    AND (:recruitmentStatus IS NULL OR sg.recruitmentStatus = :recruitmentStatus)
    AND (:meetingMethod IS NULL OR sg.meetingMethod = :meetingMethod)
    AND (:subjectAreaId IS NULL OR sg.subjectArea.id = :subjectAreaId)
    """)
    Page<StudyGroup> findAllByFilters(@Param("keyword") String keyword,
                                      @Param("recruitmentStatus") RecruitmentStatus recruitmentStatus,
                                      @Param("meetingMethod") MeetingMethod meetingMethod,
                                      @Param("subjectAreaId") Long subjectAreaId,
                                      Pageable pageable);

    // 모집 상태로 조회
    List<StudyGroup> findAllByRecruitmentStatus(RecruitmentStatus recruitmentStatus);

    //  스터디 방식으로 조회
    List<StudyGroup> findAllByMeetingMethod(MeetingMethod meetingMethod);
}
