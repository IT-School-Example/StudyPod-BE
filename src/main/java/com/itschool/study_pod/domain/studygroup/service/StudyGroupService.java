package com.itschool.study_pod.domain.studygroup.service;

import com.itschool.study_pod.common.AuthUtil;
import com.itschool.study_pod.common.FileStorageUtil;
import com.itschool.study_pod.domain.enrollment.entity.Enrollment;
import com.itschool.study_pod.domain.enrollment.repository.EnrollmentRepository;
import com.itschool.study_pod.domain.studygroup.dto.request.StudyGroupRequest;
import com.itschool.study_pod.domain.studygroup.dto.request.StudyGroupSearchRequest;
import com.itschool.study_pod.domain.studygroup.dto.response.StudyGroupResponse;
import com.itschool.study_pod.domain.studygroup.dto.response.StudyGroupSummaryResponse;
import com.itschool.study_pod.domain.studygroup.entity.StudyGroup;
import com.itschool.study_pod.domain.studygroup.repository.StudyGroupRepository;
import com.itschool.study_pod.domain.subjectarea.entity.SubjectArea;
import com.itschool.study_pod.domain.subjectarea.repository.SubjectAreaRepository;
import com.itschool.study_pod.domain.user.entity.User;
import com.itschool.study_pod.domain.user.repository.UserRepository;
import com.itschool.study_pod.global.address.entity.Sido;
import com.itschool.study_pod.global.base.crud.CrudWithFileService;
import com.itschool.study_pod.global.base.dto.Header;
import com.itschool.study_pod.global.enumclass.EnrollmentStatus;
import com.itschool.study_pod.global.enumclass.MeetingMethod;
import com.itschool.study_pod.global.enumclass.RecruitmentStatus;
import com.itschool.study_pod.global.enumclass.Subject;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class StudyGroupService extends CrudWithFileService<StudyGroupRequest, StudyGroupResponse, StudyGroup> {

    private final StudyGroupRepository studyGroupRepository;
    private final EnrollmentRepository enrollmentRepository;
    private final UserRepository userRepository;
    private final SubjectAreaRepository subjectAreaRepository;
    private final FileStorageUtil fileStorageUtil;

    @Override
    protected JpaRepository<StudyGroup, Long> getBaseRepository() {
        return studyGroupRepository;
    }

    @Override
    protected StudyGroup toEntity(StudyGroupRequest request) {
        return StudyGroup.of(request);
    }

    @Override
    protected Class<StudyGroupRequest> getRequestClass() {
        return StudyGroupRequest.class;
    }

    @Override
    protected String getDirName() {
        return "study-group";
    }

    public Header<StudyGroupResponse> create(StudyGroupRequest data, MultipartFile file) {
        try {
            User leader = userRepository.findById(data.getLeader().getId())
                    .orElseThrow(() -> new EntityNotFoundException("리더 ID가 존재하지 않습니다."));

            SubjectArea subjectArea = subjectAreaRepository.findById(data.getSubjectArea().getId())
                    .orElseThrow(() -> new EntityNotFoundException("주제영역 ID가 존재하지 않습니다."));

            StudyGroup studyGroup = StudyGroup.builder()
                    .title(data.getTitle())
                    .description(data.getDescription())
                    .maxMembers(data.getMaxMembers())
                    .meetingMethod(data.getMeetingMethod())
                    .recruitmentStatus(data.getRecruitmentStatus())
                    .feeType(data.getFeeType())
                    .amount(data.getAmount())
                    .leader(leader)
                    .sido(Sido.withId(data.getSido().getSidoCd()))
                    .subjectArea(subjectArea)
                    .keywords(data.getKeywords())
                    .weeklySchedules(data.getWeeklySchedules())
                    .build();

            if (file != null && !file.isEmpty()) {
                String uploadDirPath = new File(System.getProperty("user.dir"), "uploads/study-group").getAbsolutePath();
                String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();

                String fileUrl = FileStorageUtil.saveFile(file, uploadDirPath, fileName);
                studyGroup.updateFileUrl("/uploads/study-group/" + fileName); // 웹 접근용 경로
            }

            StudyGroup saved = studyGroupRepository.save(studyGroup);
            return Header.OK(saved.response());

        } catch (Exception e) {
            log.error("스터디 그룹 생성 중 오류", e);
            return Header.ERROR("Unhandled exception: 파일 업로드 오류 발생");
        }
    }

    public Header<Void> deleteById(Long id) {
        StudyGroup studyGroup = studyGroupRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("해당 id " + id + "에 해당하는 객체가 없습니다."));
        studyGroupRepository.delete(studyGroup);
        return Header.OK();
    }

    public Header<List<StudyGroupResponse>> findAllByLeaderId(Long leaderId, Pageable pageable) {
        Page<StudyGroup> results = studyGroupRepository.findAllByLeaderId(leaderId, pageable);
        return convertPageToList(results);
    }

    public Header<List<StudyGroupResponse>> findAllByFilters(String searchStr, Header<StudyGroupSearchRequest> request, Pageable pageable) {
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
        return enrollmentRepository.findByUserIdAndStatus(userId, EnrollmentStatus.APPROVED)
                .map(enrollment -> {
                    StudyGroup group = enrollment.getStudyGroup();
                    return group != null
                            ? Header.OK(group.response())
                            : Header.<StudyGroupResponse>ERROR("스터디 그룹 정보를 찾을 수 없습니다.");
                })
                .orElseGet(() -> Header.<StudyGroupResponse>ERROR("스터디원으로 있는 그룹을 찾을 수 없습니다."));
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

    // ✅ 시도 코드 기준 조회 기능 추가
    public Header<List<StudyGroupResponse>> findBySidoCd(String sidoCd, Pageable pageable) {
        Page<StudyGroup> results = studyGroupRepository.findBySidoCd(sidoCd, pageable);
        if (results.getTotalElements() == 0) {
            return Header.ERROR("해당 시도 코드에 해당하는 스터디 그룹이 존재하지 않습니다.");
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

    // 스터디 그룹 회원만 접근가능(스터디그룹의 상세정보)
    @Override
    public Header<StudyGroupResponse> read(Long studyGroupId) {

        // 인증 정보 받아와서 해당 사용자의 id 값을 받아오는 메서드
        // 추후에 시큐리티 적용해서 로그인 기능이 추가되면 활용 가능
        // Long userId = getCurrentAccountId();

        Long userId = AuthUtil.getCurrentAccountId();
        // 해당 스터디의 멤버인지 확인
        boolean isMember = enrollmentRepository
                .existsByStudyGroupIdAndUserIdAndStatus(studyGroupId, userId, EnrollmentStatus.APPROVED);
        // 멤버가 아닐 경우
        if (!isMember) {
            throw new RuntimeException("해당 그룹에 대한 접근 권한이 없습니다.");
        }
        return super.read(studyGroupId);
    }

    public Header<StudyGroupResponse> update(Long id, StudyGroupRequest data, MultipartFile file) {
        try {
            StudyGroup studyGroup = studyGroupRepository.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException("해당 스터디 그룹이 존재하지 않습니다."));

            User leader = userRepository.findById(data.getLeader().getId())
                    .orElseThrow(() -> new EntityNotFoundException("리더 ID가 존재하지 않습니다."));

            SubjectArea subjectArea = subjectAreaRepository.findById(data.getSubjectArea().getId())
                    .orElseThrow(() -> new EntityNotFoundException("주제영역 ID가 존재하지 않습니다."));

            studyGroup.updateFromRequest(data, leader, data.getSido().getSidoCd(), subjectArea);

            if (file != null && !file.isEmpty()) {
                String uploadDirPath = new File(System.getProperty("user.dir"), "uploads/study-group").getAbsolutePath();
                String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();

                String fileUrl = FileStorageUtil.saveFile(file, uploadDirPath, fileName);
                studyGroup.updateFileUrl("/uploads/study-group/" + fileName);
            }

            StudyGroup saved = studyGroupRepository.save(studyGroup);
            return Header.OK(saved.response());

        } catch (Exception e) {
            log.error("스터디 그룹 수정 중 오류", e);
            return Header.ERROR("Unhandled exception: 파일 업로드 또는 데이터 처리 중 오류 발생");
        }
    }

    public StudyGroupSummaryResponse getSummary(Long studyGroupId) {
        StudyGroup studyGroup = studyGroupRepository.findById(studyGroupId)
                .orElseThrow(() -> new RuntimeException("해당 스터디 그룹이 존재하지 않습니다."));

        return toSummaryResponse(studyGroup);
    }

    private StudyGroupSummaryResponse toSummaryResponse(StudyGroup studyGroup) {
        return StudyGroupSummaryResponse.builder()
                .id(studyGroup.getId())
                .title(studyGroup.getTitle())
                .build();
    }

    public Header<StudyGroupResponse> readPublic(Long studyGroupId) {
        StudyGroup group = studyGroupRepository.findById(studyGroupId)
                .orElseThrow(() -> new RuntimeException("스터디 그룹을 찾을 수 없습니다."));
        return Header.OK(group.response());
    }


}
