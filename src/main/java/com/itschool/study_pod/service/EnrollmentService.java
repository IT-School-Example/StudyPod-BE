package com.itschool.study_pod.service;

import com.itschool.study_pod.dto.Header;
import com.itschool.study_pod.dto.request.enrollment.EnrollmentRequest;
import com.itschool.study_pod.dto.response.EnrollmentResponse;
import com.itschool.study_pod.dto.response.StudyGroupResponse;
import com.itschool.study_pod.dto.response.UserResponse;
import com.itschool.study_pod.entity.Enrollment;
import com.itschool.study_pod.entity.User;
import com.itschool.study_pod.enumclass.EnrollmentStatus;
import com.itschool.study_pod.repository.EnrollmentRepository;
import com.itschool.study_pod.repository.StudyGroupRepository;
import com.itschool.study_pod.repository.UserRepository;
import com.itschool.study_pod.service.base.CrudService;
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

    @Transactional
    public Header<EnrollmentResponse> kickMember(Long id) {
        Enrollment enrollment = enrollmentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("해당 id " + id + "에 해당하는 객체가 없습니다."));



        if (!(EnrollmentStatus.APPROVED.equals(enrollment.getStatus()) ||
                EnrollmentStatus.BANNED.equals(enrollment.getStatus()))){
            throw new IllegalStateException("처리할 수 없습니다.");
            // return Header.ERROR("처리 할 수 없습니다."); // 500에러x(200으로 뜸)
        }


        if (EnrollmentStatus.APPROVED.equals(enrollment.getStatus())) {
            // 강제퇴장
            enrollment.kickMember();
        } else if (EnrollmentStatus.BANNED.equals(enrollment.getStatus())) {
            // 재입장
            enrollment.approveMember();
        }

        enrollmentRepository.save(enrollment);
        return apiResponse(enrollment);

    }
//    // 재입장
//    public Header<EnrollmentResponse> unmemberKick (Long userId, Long studyGroupId) {
//        Enrollment enrollment = enrollmentRepository.findById(userId, studyGroupId)
//                .orElseThrow(()-> new EntityNotFoundException("해당 객체가 없습니다."));
//
//        if (enrollment.memberKick() == EnrollmentStatus.BANNED) {
//            enrollment.getStatus(EnrollmentStatus.APPROVED);
//
//            enrollmentRepository.save(enrollment);
//        }
//    }

   /* @Transactional
    public Header<EnrollmentResponse> memberKick (Long id) {

        Enrollment enrollment = enrollmentRepository.findById(id)
                .orElseThrow(()-> new EntityNotFoundException("해당 id " + id + "에 해당하는 객체가 없습니다."));

        enrollment.memberKick();



        return apiResponse(enrollment);
//        return Header.OK();
    }*/
    // 삭제를 하지 않고 데이터는 있는데 차단만 한 상태(다시 입장 할 수 있다.)


}
