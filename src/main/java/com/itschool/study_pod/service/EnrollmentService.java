package com.itschool.study_pod.service;

import com.itschool.study_pod.dto.Header;
import com.itschool.study_pod.dto.request.enrollment.EnrollmentRequest;
import com.itschool.study_pod.dto.response.EnrollmentResponse;
import com.itschool.study_pod.dto.response.StudyGroupResponse;
import com.itschool.study_pod.dto.response.UserResponse;
import com.itschool.study_pod.entity.Enrollment;
import com.itschool.study_pod.enumclass.EnrollmentStatus;
import com.itschool.study_pod.repository.EnrollmentRepository;
import com.itschool.study_pod.repository.StudyGroupRepository;
import com.itschool.study_pod.repository.UserRepository;
import com.itschool.study_pod.service.base.CrudService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

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
    public Header<List<StudyGroupResponse>> findEnrolledStudyGroupsByUserId(Long userId, EnrollmentStatus enrollmentStatus) {

        List<Enrollment> enrollmentList = enrollmentRepository.findWithStudyGroupByUserIdAndStatus(userId, EnrollmentStatus.APPROVED);

        List<StudyGroupResponse> studyGroupResponses = enrollmentList.stream()
                .map(enrollment -> enrollment.getStudyGroup().response())
                .collect(Collectors.toList());

        return Header.OK(studyGroupResponses);
    }

    /*
     * 스터디그룹별 등록 회원 조회
     * */
    public Header<List<UserResponse>> findEnrolledUsersByStudyGroupId(Long studyGroupId, EnrollmentStatus enrollmentStatus) {

        List<Enrollment> enrollmentList = enrollmentRepository.findWithUserByStudyGroupIdAndStatus(studyGroupId, EnrollmentStatus.APPROVED);

        List<UserResponse> userResponses = enrollmentList.stream()
                .map(enrollment -> enrollment.getUser().response())
                .collect(Collectors.toList());

        return Header.OK(userResponses);
    }
}
