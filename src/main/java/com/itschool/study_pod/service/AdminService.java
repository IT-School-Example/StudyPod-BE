package com.itschool.study_pod.service;

import com.itschool.study_pod.dto.request.AdminRequest;
import com.itschool.study_pod.dto.response.AdminResponse;
import com.itschool.study_pod.entity.Admin;
import com.itschool.study_pod.repository.AdminRepository;
import com.itschool.study_pod.service.base.CrudService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AdminService extends CrudService<AdminRequest, AdminResponse, Admin> {

    private final AdminRepository adminRepository;

    @Override
    protected JpaRepository<Admin, Long> getBaseRepository() {
        return adminRepository;
    }

    @Override
    protected Admin toEntity(AdminRequest requestEntity) {
        return Admin.of(requestEntity);
    }

    /*public AdminResponse create(AdminRequest request) {
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
    }*/
}
