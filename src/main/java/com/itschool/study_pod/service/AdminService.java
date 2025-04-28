package com.itschool.study_pod.service;

import com.itschool.study_pod.dto.request.Admin.AdminCreateRequest;
import com.itschool.study_pod.dto.request.Admin.AdminRequest;
import com.itschool.study_pod.dto.response.AdminResponse;
import com.itschool.study_pod.entity.Admin;
import com.itschool.study_pod.repository.AdminRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final AdminRepository adminRepository;

    public AdminResponse create(AdminCreateRequest request) {
        return adminRepository.save(Admin.of(request)).response();
    }

    public AdminResponse read(Long id) {
        return adminRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException())
                .response();
    }

    @Transactional
    public AdminResponse update(Long id, AdminRequest adminRequest) {
        Admin entity = adminRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException());

        entity.update(adminRequest);

        return entity.response();
    }

    public void delete(Long id) {
        Admin findEntity = adminRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException());
        adminRepository.delete(findEntity);
    }

}
