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

    public Header<List<StudyGroup>> findAllByLeaderId (Long leaderId) {
        return Header.OK(studyGroupRepository.findAllByLeaderId(leaderId));
    }

    public Header<List<StudyGroupResponse>> findAllByFilters(Header<StudyGroupSearchRequest> request, Pageable pageable) {

        StudyGroupSearchRequest conditions = request.getData();



        RecruitmentStatus recruitmentStatus = conditions.getRecruitmentStatus();

        // BOTH 인 경우 전체 검색되도록 null로 설정
        MeetingMethod meetingMethod = conditions.getMeetingMethod().equals(MeetingMethod.BOTH)? null : conditions.getMeetingMethod();

        // id가 null인 경우 NPE 방지
        Long subjectAreaId = conditions.getSubjectArea() == null? null : conditions.getSubjectArea().getId();



        Page<StudyGroup> groups = studyGroupRepository.findAllByFilters(request.getSearchStr(), recruitmentStatus, meetingMethod,
                                                                        subjectAreaId, pageable);


        return convertPageToList(groups);
    }
}
