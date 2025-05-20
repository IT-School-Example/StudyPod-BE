package com.itschool.study_pod.domain.studygroup.service;

import com.itschool.study_pod.domain.enrollment.entity.Enrollment;
import com.itschool.study_pod.domain.enrollment.repository.EnrollmentRepository;
import com.itschool.study_pod.domain.studygroup.dto.request.StudyGroupRequest;
import com.itschool.study_pod.domain.studygroup.dto.request.StudyGroupSearchRequest;
import com.itschool.study_pod.domain.studygroup.dto.response.StudyGroupResponse;
import com.itschool.study_pod.domain.studygroup.entity.StudyGroup;
import com.itschool.study_pod.domain.studygroup.repository.StudyGroupRepository;
import com.itschool.study_pod.global.base.crud.CrudService;
import com.itschool.study_pod.global.base.dto.Header;
import com.itschool.study_pod.global.enumclass.EnrollmentStatus;
import com.itschool.study_pod.global.enumclass.MeetingMethod;
import com.itschool.study_pod.global.enumclass.RecruitmentStatus;
import com.itschool.study_pod.global.enumclass.Subject;
import jakarta.persistence.EntityNotFoundException;
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
    private final EnrollmentRepository enrollmentRepository;

    @Override
    protected JpaRepository<StudyGroup, Long> getBaseRepository() {
        return studyGroupRepository;
    }

    @Override
    protected StudyGroup toEntity(StudyGroupRequest request) {
        return StudyGroup.of(request);
    }

    //  ID로 스터디 그룹을 삭제하는 메서드
    public Header<Void> deleteById(Long id) {
        StudyGroup studyGroup = studyGroupRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("해당 id " + id + "에 해당하는 객체가 없습니다."));
        studyGroupRepository.delete(studyGroup);
        return Header.OK();
    }

    public Header<List<StudyGroupResponse>> findAllByLeaderId(Long leaderId, Pageable pageable) {
        Page<StudyGroup> results = studyGroupRepository.findAllByLeaderId(leaderId, pageable);
        if (results.getTotalElements() == 0) {
            return Header.ERROR("해당 조건의 스터디 그룹을 불러오지 못했습니다.");
        }
        return convertPageToList(results);
    }

    public Header<List<StudyGroupResponse>> findAllByFilters(
            String searchStr,
            Header<StudyGroupSearchRequest> request,
            Pageable pageable
    ) {
        StudyGroupSearchRequest conditions = request.getData();

        RecruitmentStatus recruitmentStatus = conditions.getRecruitmentStatus();

        MeetingMethod meetingMethod = (conditions.getMeetingMethod() == MeetingMethod.BOTH)
                ? null
                : conditions.getMeetingMethod();

        Long subjectAreaId = (conditions.getSubjectArea() != null && conditions.getSubjectArea().getId() != 0)
                ? conditions.getSubjectArea().getId()
                : null;

        Page<StudyGroup> groups = studyGroupRepository.findAllByFilters(
                searchStr,
                recruitmentStatus,
                meetingMethod,
                subjectAreaId,
                pageable
        );

        return convertPageToList(groups);
    }


    public Header<List<StudyGroupResponse>> findAllByRecruitmentStatus(RecruitmentStatus recruitmentStatus, Pageable pageable) {
        Page<StudyGroup> results = studyGroupRepository.findAllByRecruitmentStatus(recruitmentStatus, pageable);
        if (results.getTotalElements() == 0) {
            return Header.ERROR("해당 조건의 스터디 그룹을 불러오지 못했습니다.");
        }
        return convertPageToList(results);
    }

    public Header<List<StudyGroupResponse>> findAllByMeetingMethod(MeetingMethod meetingMethod, Pageable pageable) {
        Page<StudyGroup> results = studyGroupRepository.findAllByMeetingMethod(meetingMethod, pageable);
        if (results.getTotalElements() == 0) {
            return Header.ERROR("해당 조건의 스터디 그룹을 불러오지 못했습니다.");
        }
        return convertPageToList(results);
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

    public Header<List<StudyGroupResponse>> searchByKeywordOnly(String keyword, Pageable pageable) {
        Page<StudyGroup> results = studyGroupRepository.searchByKeyword(keyword, pageable);
        if (results.getTotalElements() == 0) {
            return Header.ERROR("검색 결과가 없습니다.");
        }
        return convertPageToList(results);
    }

    public Header<List<StudyGroupResponse>> findBySubjectAreaAndRecruiting(String subjectValue, Pageable pageable) {
        try {
            Subject subjectEnum = Subject.valueOf(subjectValue.toUpperCase());
            Page<StudyGroup> results = studyGroupRepository.findBySubjectAreaAndRecruiting(subjectEnum, pageable);

            if (results.getTotalElements() == 0) {
                return Header.ERROR("검색 결과가 없습니다.");
            }
            return convertPageToList(results);
        } catch (IllegalArgumentException e) {
            return Header.ERROR("요청에 실패했습니다.");
        }
    }

    public Header<List<StudyGroupResponse>> findByAddressId(Long addressId, Pageable pageable) {
        Page<StudyGroup> results = studyGroupRepository.findByAddressId(addressId, pageable);
        if (results.getTotalElements() == 0) {
            return Header.ERROR("해당 조건의 스터디 그룹을 불러오지 못했습니다.");
        }
        return convertPageToList(results);
    }

    public Header<List<StudyGroupResponse>> findStudyGroupsByUserIdAndStatus(Long userId, EnrollmentStatus status) {
        List<StudyGroup> studyGroups = enrollmentRepository.findByUserIdAndStatus(userId, status).stream()
                .map(Enrollment::getStudyGroup)
                .toList();

        if (studyGroups.isEmpty()) {
            return Header.ERROR("신청한 스터디가 없습니다.");
        }

        List<StudyGroupResponse> responses = studyGroups.stream()
                .map(StudyGroup::response)
                .toList();

        return Header.OK(responses);
    }

}
