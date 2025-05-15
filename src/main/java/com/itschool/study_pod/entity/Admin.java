package com.itschool.study_pod.entity;

import com.itschool.study_pod.dto.request.admin.AdminRequest;
import com.itschool.study_pod.dto.response.AdminResponse;
import com.itschool.study_pod.entity.base.Account;
import com.itschool.study_pod.entity.base.BaseEntity;
import com.itschool.study_pod.enumclass.AccountRole;
import com.itschool.study_pod.ifs.Convertible;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SuperBuilder
@Table(name = "admins")
/*@SQLDelete(sql = "UPDATE admins SET is_deleted = true WHERE admin_id = ?")
@Where(clause = "is_deleted = false")*/
public class Admin extends Account implements Convertible<AdminRequest, AdminResponse> {




    public static Admin of(AdminRequest request) { // create용
        return Admin.builder()
                .email(request.getEmail())
                .password(request.getPassword())
                .role(AccountRole.ROLE_ADMIN)
                .build();
    }

    @Override
    @Deprecated
    public void update(AdminRequest request) {
        // PUT 전체 업데이트
        // 이메일 변경
        this.email = request.getEmail() != null? request.getEmail() : this.email;

        // 역할 변경
        // this.role = request.getRole() != null? request.getRole() : this.role;

        // 비밀번호 변경
        // updatePassword(request.getPassword());

    }

    public void updatePassword(String password) {
        // PATCH 일부 업데이트
        this.password = password != null? password : this.password;
    }

    @Override
    public AdminResponse response() {
        return AdminResponse.builder()
                .id(this.id)
                .email(this.email)
                // .password(this.password)
                .role(this.role)
                .createdAt(this.createdAt)
                .createdBy(this.createdBy)
                .updatedAt(this.updatedAt)
                .updatedBy(this.updatedBy)
                .build();
    }

    public static Admin withId(Long id) {
        return Admin.builder()
                .id(id)
                .build();
    }
}
