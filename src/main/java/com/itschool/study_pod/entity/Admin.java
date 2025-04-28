package com.itschool.study_pod.entity;

import com.itschool.study_pod.dto.request.Admin.AdminCreateRequest;
import com.itschool.study_pod.dto.request.Admin.AdminInformationRequest;
import com.itschool.study_pod.dto.request.Admin.AdminPasswordRequest;
import com.itschool.study_pod.dto.request.Admin.AdminRequest;
import com.itschool.study_pod.dto.response.AdminResponse;
import com.itschool.study_pod.entity.base.BaseEntity;
import com.itschool.study_pod.enumclass.AccountRole;
import com.itschool.study_pod.ifs.Convertible;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Table(name = "admins")
public class Admin extends BaseEntity implements Convertible<AdminRequest, AdminResponse> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "admin_id")
    private Long id;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AccountRole role;

    public static Admin of(AdminCreateRequest request) { // create용
        return Admin.builder()
                .email(request.getEmail())
                .password(request.getPassword())
                .role(request.getRole())
                .build();
    }

    @Override
    public void update(AdminRequest request) {
        if(request instanceof AdminInformationRequest informationRequest) {
            this.email = informationRequest.getEmail();
        } else if(request instanceof AdminPasswordRequest passwordRequest) {
            this.password = passwordRequest.getPassword();
        } else {
            throw new IllegalArgumentException("지원하지 않는 요청 타입입니다: " + request.getClass().getSimpleName());
        }
    }

    @Override
    public AdminResponse response() {
        return AdminResponse.builder()
                .id(this.id)
                .email(this.email)
                .password(this.password)
                .role(this.role)
                .build();
    }
}
