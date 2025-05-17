package com.itschool.study_pod.service;

import com.itschool.study_pod.dto.Header;
import com.itschool.study_pod.dto.request.admin.AdminPasswordUpdateRequest;
import com.itschool.study_pod.dto.request.admin.AdminRequest;
import com.itschool.study_pod.dto.response.AdminResponse;
import com.itschool.study_pod.entity.Admin;
import com.itschool.study_pod.enumclass.AccountRole;
import com.itschool.study_pod.repository.AdminRepository;
import com.itschool.study_pod.service.base.CrudService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AdminService extends CrudService<AdminRequest, AdminResponse, Admin> {

    private final PasswordEncoder bCryptPasswordEncoder;

    private final AdminRepository adminRepository;

    @Override
    protected JpaRepository<Admin, Long> getBaseRepository() {
        return adminRepository;
    }

    @Override
    protected Admin toEntity(AdminRequest request) {
        return Admin.builder()
                .email(request.getEmail())
                .password(bCryptPasswordEncoder.encode(request.getPassword()))
                .role(AccountRole.ROLE_ADMIN)
                .build();
    }



    /*
     * 비밀번호 수정하기
     * */
    @Transactional
    public Header<Void> updatePassword(Long id, Header<AdminPasswordUpdateRequest> request) {

        Admin entity = getBaseRepository().findById(id)
                .orElseThrow(() -> new EntityNotFoundException(this.getClass().getSimpleName() + " : 해당 id " + id + "에 해당하는 객체가 없습니다."));

        entity.updatePassword(request.getData().getPassword());

        return Header.OK();
    }
}
