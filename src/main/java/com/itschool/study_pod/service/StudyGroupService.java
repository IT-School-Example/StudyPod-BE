package com.itschool.study_pod.service;

import com.itschool.study_pod.dto.Header;
import com.itschool.study_pod.dto.request.studygroup.StudyGroupRequest;
import com.itschool.study_pod.dto.request.studygroup.StudyGroupSearchRequest;
import com.itschool.study_pod.dto.response.StudyGroupResponse;
import com.itschool.study_pod.entity.StudyGroup;
import com.itschool.study_pod.enumclass.MeetingMethod;
import com.itschool.study_pod.enumclass.RecruitmentStatus;
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

    public Header<List<StudyGroupResponse>> findAllByFilters(Header<StudyGroupSearchRequest> request, Pageable pageable) {
        StudyGroupSearchRequest conditions = request.getData();

        RecruitmentStatus recruitmentStatus = conditions.getRecruitmentStatus();

        // BOTH일 경우 meetingMethod 조건 제거
        MeetingMethod meetingMethod = conditions.getMeetingMethod() == MeetingMethod.BOTH ? null : conditions.getMeetingMethod();

        Long subjectAreaId = conditions.getSubjectArea() == null ? null : conditions.getSubjectArea().getId();

        Page<StudyGroup> groups = studyGroupRepository.findAllByFilters(
                request.getSearchStr(),
                recruitmentStatus,
                meetingMethod,
                subjectAreaId,
                pageable
        );

        return convertPageToList(groups);
    }

    public Header<List<StudyGroupResponse>> findAllByRecruitmentStatus(String value) {
        try {
            RecruitmentStatus status = RecruitmentStatus.valueOf(value.toUpperCase());
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
}
