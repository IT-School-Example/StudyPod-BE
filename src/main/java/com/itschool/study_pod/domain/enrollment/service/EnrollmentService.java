package com.itschool.study_pod.domain.enrollment.service;

import com.itschool.study_pod.global.base.dto.Header;
import com.itschool.study_pod.domain.enrollment.dto.request.EnrollmentRequest;
import com.itschool.study_pod.domain.enrollment.dto.response.EnrollmentResponse;
import com.itschool.study_pod.domain.studygroup.dto.response.StudyGroupResponse;
import com.itschool.study_pod.domain.user.dto.response.UserResponse;
import com.itschool.study_pod.domain.enrollment.entity.Enrollment;
import com.itschool.study_pod.global.enumclass.EnrollmentStatus;
import com.itschool.study_pod.domain.enrollment.repository.EnrollmentRepository;
import com.itschool.study_pod.domain.studygroup.repository.StudyGroupRepository;
import com.itschool.study_pod.domain.user.repository.UserRepository;
import com.itschool.study_pod.global.base.crud.CrudService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EnrollmentService extends CrudService<EnrollmentRequest, EnrollmentResponse, Enrollment> {

    private final EnrollmentRepository enrollmentRepository;

    private final StudyGroupRepository studyGroupRepository;

    private final UserRepository userRepository;

    @Override
    protected JpaRepository<Enrollment, Long> getBaseRepository() {
        return enrollmentRepository;
    }

    @Override
    protected Enrollment toEntity(EnrollmentRequest requestEntity) {
        return Enrollment.of(requestEntity);
    }

    /*
     * 회원id와 등록상태로 스터디 내역 조회
     * */
    public Header<List<StudyGroupResponse>> findEnrolledStudyGroupsByUserId(Long userId, EnrollmentStatus status) {
        List<Enrollment> enrollments = enrollmentRepository.findWithStudyGroupByUserIdAndStatus(userId, status);

        if (enrollments.isEmpty()) {
            return Header.ERROR("신청한 스터디가 없습니다.");
        }

        List<StudyGroupResponse> responses = enrollments.stream()
                .map(e -> e.getStudyGroup().response())
                .toList();

        return Header.OK(responses);
    }


    /*
     * 스터디그룹별 등록 회원 조회
     * */
    public Header<List<UserResponse>> findEnrolledUsersByStudyGroupId(Long studyGroupId, EnrollmentStatus enrollmentStatus) {
        List<Enrollment> enrollmentList = enrollmentRepository
                .findWithUserByStudyGroupIdAndStatus(studyGroupId, enrollmentStatus);

        List<UserResponse> userResponses = enrollmentList.stream()
                .map(enrollment -> enrollment.getUser().response())
                .collect(Collectors.toList());

        return Header.OK(userResponses);
    }


    @Transactional
    public Header<EnrollmentResponse> kickMember(Long id) {

        Enrollment enrollment = enrollmentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("해당 id " + id + "에 해당하는 객체가 없습니다."));

        enrollment.kickMember();
        return apiResponse(enrollment);
    }

    public Header<EnrollmentResponse> enroll(EnrollmentRequest request) {
        Enrollment enrollment = Enrollment.of(request);         // DTO → Entity
        Enrollment saved = enrollmentRepository.save(enrollment); // DB 저장
        return Header.OK(saved.response());                     // Entity → Response DTO
    }

    @Transactional
    public Header<EnrollmentResponse> update(Long id, EnrollmentRequest request) {
        Enrollment enrollment = enrollmentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("해당 신청 내역이 존재하지 않습니다."));

        enrollment.update(request); // 상태 및 소개글 업데이트
        return Header.OK(enrollment.response());
    }

    @Transactional(readOnly = true)
    public Header<List<EnrollmentResponse>> findEnrollmentsByStudyGroupIdAndStatus(Long studyGroupId, EnrollmentStatus status) {
        List<Enrollment> list = enrollmentRepository.findWithUserByStudyGroupIdAndStatus(studyGroupId, status);
        List<EnrollmentResponse> responseList = list.stream()
                .map(Enrollment::response)
                .toList();

        return Header.OK(responseList);
    }



}