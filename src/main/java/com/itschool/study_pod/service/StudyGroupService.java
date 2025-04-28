package com.itschool.study_pod.service;

import com.itschool.study_pod.dto.request.StudyGroup.StudyGroupRequest;
import com.itschool.study_pod.dto.response.StudyGroupResponse;
import com.itschool.study_pod.entity.StudyGroup;
import com.itschool.study_pod.repository.StudyGroupRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class StudyGroupService {

    private final StudyGroupRepository studyGroupRepository;

    /**
     * 스터디 그룹 생성
     */
    public StudyGroupResponse createStudyGroup(StudyGroupRequest request) {
        StudyGroup studyGroup = StudyGroup.of(request); // StudyGroup 엔티티의 of() 이용
        StudyGroup saved = studyGroupRepository.save(studyGroup);
        return toResponse(saved);
    }

    /**
     * 특정 스터디 그룹 조회
     */
    @Transactional(readOnly = true)
    public StudyGroupResponse getStudyGroup(Long id) {
        StudyGroup studyGroup = studyGroupRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("스터디 그룹을 찾을 수 없습니다. id = " + id));
        return toResponse(studyGroup);
    }

    /**
     * 전체 스터디 그룹 리스트 조회
     */
    @Transactional(readOnly = true)
    public List<StudyGroupResponse> getAllStudyGroups() {
        return studyGroupRepository.findAll().stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    /**
     * 스터디 그룹 수정
     */
    public StudyGroupResponse updateStudyGroup(Long id, StudyGroupRequest request) {
        StudyGroup studyGroup = studyGroupRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("스터디 그룹을 찾을 수 없습니다. id = " + id));
        studyGroup.update(request); // StudyGroup 엔티티에 있는 update 메서드 이용
        return toResponse(studyGroup);
    }

    /**
     * 스터디 그룹 삭제
     */
    public void deleteStudyGroup(Long id) {
        StudyGroup studyGroup = studyGroupRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("스터디 그룹을 찾을 수 없습니다. id = " + id));
        studyGroupRepository.delete(studyGroup);
    }

    /**
     * Entity -> Response 변환 메소드
     */
    private StudyGroupResponse toResponse(StudyGroup studyGroup) {
        return StudyGroupResponse.builder()
                .id(studyGroup.getId())
                .title(studyGroup.getTitle())
                .description(studyGroup.getDescription())
                .maxMembers(studyGroup.getMaxMembers())
                .meetingMethod(studyGroup.getMeetingMethod() != null ? studyGroup.getMeetingMethod().name() : null)
                .recruitmentStatus(studyGroup.getRecruitmentStatus() != null ? studyGroup.getRecruitmentStatus().name() : null)
                .feeType(studyGroup.getFeeType() != null ? studyGroup.getFeeType().name() : null)
                .amount(studyGroup.getAmount())
                .leaderId(studyGroup.getLeader() != null ? studyGroup.getLeader().getId() : null)
                .addressId(studyGroup.getAddressId() != null ? studyGroup.getAddressId().getId() : null)
                .subjectAreaId(studyGroup.getSubjectArea() != null ? studyGroup.getSubjectArea().getId() : null)
                .keywords(studyGroup.getKeywords())
                .weeklySchedules(toWeeklySchedules(studyGroup.getWeeklySchedules()))
                .build();
    }

    /**
     * WeeklySchedule 변환 메소드
     */
    private Set<StudyGroupResponse.WeeklyScheduleResponse> toWeeklySchedules(Set<com.itschool.study_pod.embedable.WeeklySchedule> weeklySchedules) {
        if (weeklySchedules == null) return null;
        return weeklySchedules.stream()
                .map(ws -> StudyGroupResponse.WeeklyScheduleResponse.builder()
                        .dayOfWeek(ws.getDayOfWeek() != null ? ws.getDayOfWeek().name() : null)  // DayOfWeek → String
                        .startTime(ws.getTimeRange() != null && ws.getTimeRange().getStartTime() != null ? ws.getTimeRange().getStartTime().toString() : null)  // TimeRange 내부 접근
                        .endTime(ws.getTimeRange() != null && ws.getTimeRange().getEndTime() != null ? ws.getTimeRange().getEndTime().toString() : null)      // TimeRange 내부 접근
                        .build())
                .collect(Collectors.toSet());
    }

}
