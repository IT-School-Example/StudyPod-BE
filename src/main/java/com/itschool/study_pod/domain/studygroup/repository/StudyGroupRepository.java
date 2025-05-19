package com.itschool.study_pod.repository;

import com.itschool.study_pod.entity.StudyGroup;
import com.itschool.study_pod.enumclass.MeetingMethod;
import com.itschool.study_pod.enumclass.RecruitmentStatus;
import com.itschool.study_pod.enumclass.Subject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface StudyGroupRepository extends JpaRepository<StudyGroup, Long> {

    Page<StudyGroup> findAllByLeaderId(Long leaderId, Pageable pageable);

    // POST /search 필터링용
    @Query("""
                SELECT DISTINCT sg FROM StudyGroup sg
                WHERE (
                    :keyword IS NULL OR LOWER(sg.title) LIKE LOWER(CONCAT('%', :keyword, '%'))
                    OR EXISTS (
                        SELECT 1 FROM sg.keywords k
                        WHERE LOWER(k) LIKE LOWER(CONCAT('%', :keyword, '%'))
                    )
                )
                AND (:recruitmentStatus IS NULL OR sg.recruitmentStatus = :recruitmentStatus)
                AND (:meetingMethod IS NULL OR sg.meetingMethod = :meetingMethod)
                AND (:subjectAreaId IS NULL OR sg.subjectArea.id = :subjectAreaId)
            """)
    Page<StudyGroup> findAllByFilters(
            @Param("keyword") String keyword,
            @Param("recruitmentStatus") RecruitmentStatus recruitmentStatus,
            @Param("meetingMethod") MeetingMethod meetingMethod,
            @Param("subjectAreaId") Long subjectAreaId,
            Pageable pageable
    );

    @Query("""
                SELECT sg FROM StudyGroup sg
                WHERE LOWER(sg.title) LIKE LOWER(CONCAT('%', :kw, '%'))
                   OR EXISTS (
                       SELECT 1 FROM StudyGroup s2
                       JOIN s2.keywords k
                       WHERE s2.id = sg.id AND LOWER(k) LIKE LOWER(CONCAT('%', :kw, '%'))
                   )
            """)
    Page<StudyGroup> searchByKeyword(@Param("kw") String keyword, Pageable pageable);

    // 모집 상태로 조회
    Page<StudyGroup> findAllByRecruitmentStatus(RecruitmentStatus recruitmentStatus, Pageable pageable);

    // 스터디 방식으로 조회
    Page<StudyGroup> findAllByMeetingMethod(MeetingMethod meetingMethod, Pageable pageable);


    @Query("""
                SELECT sg FROM StudyGroup sg
                WHERE sg.recruitmentStatus = com.itschool.study_pod.enumclass.RecruitmentStatus.RECRUITING
                AND sg.subjectArea.subject = :subjectEnum
            """)
    Page<StudyGroup> findBySubjectAreaAndRecruiting(@Param("subjectEnum") Subject subjectEnum, Pageable pageable);


    @Query("""
                SELECT sg FROM StudyGroup sg
                WHERE sg.address.id = :addressId
            """)
    Page<StudyGroup> findByAddressId(@Param("addressId") Long addressId, Pageable pageable);

}