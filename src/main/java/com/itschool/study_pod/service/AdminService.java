package com.itschool.study_pod.service;

import com.itschool.study_pod.dto.request.AdminRequest;
import com.itschool.study_pod.dto.response.AdminResponse;
import com.itschool.study_pod.entity.Admin;
import com.itschool.study_pod.repository.AdminRepository;
import com.itschool.study_pod.service.base.CrudService;
import org.springframework.stereotype.Service;

@Service
public class AdminService extends CrudService<AdminRequest, AdminResponse, Admin> {

    public AdminService(AdminRepository baseRepository) {
        super(baseRepository);
    }

    @Override
    protected Admin toEntity(AdminRequest requestEntity) {
        return Admin.of(requestEntity);
    }

    /*private final AdminRepository adminRepository;

    public AdminResponse create(AdminRequest request) {
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
