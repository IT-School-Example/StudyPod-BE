package com.itschool.study_pod.domain.studygroup.repository;

import com.itschool.study_pod.domain.studygroup.entity.StudyGroup;
import com.itschool.study_pod.global.enumclass.MeetingMethod;
import com.itschool.study_pod.global.enumclass.RecruitmentStatus;
import com.itschool.study_pod.global.enumclass.Subject;
import jakarta.persistence.Entity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface StudyGroupRepository extends JpaRepository<StudyGroup, Long> {

    Page<StudyGroup> findAllByLeaderId(Long leaderId, Pageable pageable);

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

    Page<StudyGroup> findAllByRecruitmentStatus(RecruitmentStatus recruitmentStatus, Pageable pageable);

    Page<StudyGroup> findAllByMeetingMethod(MeetingMethod meetingMethod, Pageable pageable);

    @Query("""
                SELECT sg FROM StudyGroup sg
                WHERE sg.recruitmentStatus = 'RECRUITING'
                AND sg.subjectArea.subject = :subjectEnum
            """)
    Page<StudyGroup> findBySubjectAreaAndRecruiting(@Param("subjectEnum") Subject subjectEnum, Pageable pageable);

    // ✅ 추가된 시도 코드 기반 검색
    @EntityGraph(attributePaths = {"address", "address.sido", "weeklySchedules"})
    @Query("""
                SELECT sg FROM StudyGroup sg
                WHERE sg.address.sido.sidoCd = :sidoCd
            """)
    Page<StudyGroup> findBySidoCd(@Param("sidoCd") String sidoCd, Pageable pageable);

    long count();

    long countByCreatedAtAfter(LocalDateTime dateTime);

    long countByCreatedAtBetween(LocalDateTime start, LocalDateTime end);

    @Query("""
                SELECT k, COUNT(k) as cnt
                FROM StudyGroup sg
                JOIN sg.keywords k
                GROUP BY k
                ORDER BY cnt DESC
            """)
    List<Object[]> findTopKeywords();

}

