package com.itschool.study_pod.service;

import com.itschool.study_pod.dto.request.EnrollmentRequest;
import com.itschool.study_pod.dto.response.EnrollmentResponse;
import com.itschool.study_pod.entity.Enrollment;
import com.itschool.study_pod.repository.EnrollmentRepository;
import com.itschool.study_pod.service.base.CrudService;
import org.springframework.stereotype.Service;

@Service
public class EnrollmentService extends CrudService<EnrollmentRequest, EnrollmentResponse, Enrollment> {

    public EnrollmentService(EnrollmentRepository baseRepository) {
        super(baseRepository);
    }

    @Override
    protected Enrollment toEntity(EnrollmentRequest requestEntity) {
        return Enrollment.of(requestEntity);
    }

    /*private final EnrollmentRepository enrollmentRepository;

    public EnrollmentResponse create(EnrollmentRequest request) {
        return enrollmentRepository.save(Enrollment.of(request)).response();
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
    }*/
}
