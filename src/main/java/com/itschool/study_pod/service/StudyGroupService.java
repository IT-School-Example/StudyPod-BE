package com.itschool.study_pod.service;

import com.itschool.study_pod.dto.Header;
import com.itschool.study_pod.dto.request.studygroup.StudyGroupRequest;
import com.itschool.study_pod.dto.request.studygroup.StudyGroupSearchRequest;
import com.itschool.study_pod.dto.response.StudyGroupResponse;
import com.itschool.study_pod.entity.Enrollment;
import com.itschool.study_pod.entity.StudyGroup;
import com.itschool.study_pod.enumclass.EnrollmentStatus;
import com.itschool.study_pod.enumclass.MeetingMethod;
import com.itschool.study_pod.enumclass.RecruitmentStatus;
import com.itschool.study_pod.repository.EnrollmentRepository;
import com.itschool.study_pod.repository.StudyGroupRepository;
import com.itschool.study_pod.service.base.CrudService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StudyGroupService extends CrudService<StudyGroupRequest, StudyGroupResponse, StudyGroup> {

    private final StudyGroupRepository studyGroupRepository;

    // 필드 주입 추가
    private final EnrollmentRepository enrollmentRepository;


    @Override
    protected JpaRepository<StudyGroup, Long> getBaseRepository() {
        return studyGroupRepository;
    }

    @Override
    protected StudyGroup toEntity(StudyGroupRequest requestEntity) {
        return StudyGroup.of(requestEntity);
    }

    public Header<List<StudyGroup>> findAllByLeaderId(Long leaderId) {
        return Header.OK(studyGroupRepository.findAllByLeaderId(leaderId));
    }

    public Header<List<StudyGroupResponse>> findAllByFilters(String searchStr,
                                                             Header<StudyGroupSearchRequest> request,
                                                             Pageable pageable) {

        StudyGroupSearchRequest conditions = request.getData();

        RecruitmentStatus recruitmentStatus = conditions.getRecruitmentStatus();

        // BOTH일 경우 meetingMethod 조건 제거
        MeetingMethod meetingMethod = conditions.getMeetingMethod() == MeetingMethod.BOTH ? null : conditions.getMeetingMethod();

        Long subjectAreaId = conditions.getSubjectArea() == null ? null : conditions.getSubjectArea().getId();

        Page<StudyGroup> groups = studyGroupRepository.findAllByFilters(
                searchStr,
                recruitmentStatus,
                meetingMethod,
                subjectAreaId,
                pageable
        );

        return convertPageToList(groups);
    }

    public Header<List<StudyGroupResponse>> findAllByRecruitmentStatus(RecruitmentStatus recruitmentStatus) {
        try {
            RecruitmentStatus status = recruitmentStatus;
            List<StudyGroup> results = studyGroupRepository.findAllByRecruitmentStatus(status);

            if (results.isEmpty()) {
                throw new RuntimeException("해당 조건의 스터디 그룹을 불러오지 못했습니다.");
            }

            // 엔티티를 DTO로 변환해서 반환
            List<StudyGroupResponse> dtoList = results.stream()
                    .map(StudyGroup::response)
                    .toList();

            return Header.OK(dtoList);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("요청에 실패했습니다. 유효하지 않은 모집 상태입니다.");
        }
    }

    public Header<List<StudyGroupResponse>> findAllByMeetingMethod(MeetingMethod meetingMethod) {
        List<StudyGroup> results = studyGroupRepository.findAllByMeetingMethod(meetingMethod);

        if (results.isEmpty()) {
            throw new RuntimeException("해당 조건의 스터디 그룹을 불러오지 못했습니다.");
        }

        List<StudyGroupResponse> dtoList = results.stream()
                .map(StudyGroup::response)
                .toList();

        return Header.OK(dtoList);
    }

    public Header<StudyGroupResponse> findStudyGroupByUserId(Long userId) {
        Enrollment enrollment = enrollmentRepository.findByUserIdAndStatus(userId, EnrollmentStatus.APPROVED)
                .orElseThrow(() -> new RuntimeException("스터디원으로 있는 그룹을 찾을 수 없습니다."));

        StudyGroup studyGroup = enrollment.getStudyGroup();
        if (studyGroup == null) {
            throw new RuntimeException("스터디 그룹 정보를 찾을 수 없습니다.");
        }

        return Header.OK(studyGroup.response());
    }

    public Header<List<StudyGroupResponse>> findStudyGroupsByLeaderId(Long leaderId) {
        List<StudyGroup> groups = studyGroupRepository.findAllByLeaderId(leaderId);

        if (groups.isEmpty()) {
            throw new RuntimeException("스터디 리더로 있는 그룹을 찾을 수 없습니다.");
        }

        List<StudyGroupResponse> responseList = groups.stream()
                .map(StudyGroup::response)
                .toList();

        return Header.OK(responseList);
    }
}
