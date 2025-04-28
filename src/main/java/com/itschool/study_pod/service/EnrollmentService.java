package com.itschool.study_pod.service;

import com.itschool.study_pod.dto.request.Enrollment.EnrollmentCreateRequest;
import com.itschool.study_pod.dto.request.Enrollment.EnrollmentRequest;
import com.itschool.study_pod.dto.response.EnrollmentResponse;
import com.itschool.study_pod.entity.Enrollment;
import com.itschool.study_pod.entity.StudyGroup;
import com.itschool.study_pod.entity.User;
import com.itschool.study_pod.repository.EnrollmentRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EnrollmentService {

    private final EnrollmentRepository enrollmentRepository;

    public EnrollmentResponse create(EnrollmentCreateRequest request, User user, StudyGroup studyGroup) {
        return enrollmentRepository.save(Enrollment.of(request, user, studyGroup)).response();
    }

    public EnrollmentResponse read(Long id) {
        return enrollmentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException())
                .response();
    }

    @Transactional
    public EnrollmentResponse update(Long id, EnrollmentRequest enrollmentRequest) {
        Enrollment entity = enrollmentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException());

        entity.update(enrollmentRequest);

        return entity.response();
    }

    public void delete(Long id) {
        Enrollment findEntity = enrollmentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException());
        enrollmentRepository.delete(findEntity);
    }

}
