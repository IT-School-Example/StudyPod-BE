package com.itschool.study_pod.service;

import com.itschool.study_pod.dto.Header;
import com.itschool.study_pod.dto.request.studygroup.StudyGroupRequest;
import com.itschool.study_pod.dto.request.studygroup.StudyGroupSearchRequest;
import com.itschool.study_pod.dto.response.StudyGroupResponse;
import com.itschool.study_pod.entity.StudyGroup;
import com.itschool.study_pod.entity.User;
import com.itschool.study_pod.enumclass.MeetingMethod;
import com.itschool.study_pod.enumclass.RecruitmentStatus;
import com.itschool.study_pod.repository.StudyGroupRepository;
import com.itschool.study_pod.repository.UserRepository;
import com.itschool.study_pod.service.base.CrudService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.nio.file.AccessDeniedException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StudyGroupService extends CrudService<StudyGroupRequest, StudyGroupResponse, StudyGroup> {

    private final StudyGroupRepository studyGroupRepository;

    private final UserRepository userRepository;


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

//    @Autowired
//    public StudyGroupService(UserRepository userRepository) {
//        this.userRepository = userRepository;
//    }
//
//    public List<User> getUsersByStudyGroup(StudyGroup studyGroup) {
//        return userRepository.findByStudyGroup(studyGroup);
//    }
    // 스터디 그룹 회원만 접근가능(스터디그룹의 상세정보)
    public StudyGroup studyGroupDetail(Long studyGroupId, Long userId) throws AccessDeniedException {

        StudyGroup studyGroup = studyGroupRepository.findById(studyGroupId)
                .orElseThrow(() -> new IllegalArgumentException("해당 스터디 그룹이 존재하지 않습니다."));

        // 스터디그룹의 멤버
        // 이 그룹의 사용자 중에서, userId와 같은 ID를 가진 사용자가 있는지 확인
        // StudyGroup 객체 안에 있는 List<User> user를 스트림으로 변환
        List<User> userList = userRepository.findByStudyGroup(studyGroup);


        boolean isMember = userList.stream()
                // 사용자ID(userId)가 그룹 사용자 중 하나라도 일치하면 true를 반환
                .anyMatch(user -> user.getId().equals(userId));

        // 그룹에 속해있지 않다면 AccessDeniedException 던져 접근을 막음
        // AccessDeniedException : 접근 권한이 없는 리소스를 요청했을때(인증은 되었지만, 인가가 안된경우)
        // 로그인은 했지만 권한 없음
        if (!isMember) {
            throw new AccessDeniedException("이 그룹에 접근할 수 없습니다.");
        }

        return studyGroup;
    }
}
