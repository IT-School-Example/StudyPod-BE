package com.itschool.study_pod.service;

import com.itschool.study_pod.dto.request.StudyGroupRequest;
import com.itschool.study_pod.dto.response.StudyGroupResponse;
import com.itschool.study_pod.embedable.WeeklySchedule;
import com.itschool.study_pod.entity.StudyGroup;
import com.itschool.study_pod.repository.StudyGroupRepository;
import com.itschool.study_pod.service.base.CrudService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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
}
