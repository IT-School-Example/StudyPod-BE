package com.itschool.study_pod.entity;

import com.itschool.study_pod.dto.request.AdminRequest;
import com.itschool.study_pod.dto.response.AdminResponse;
import com.itschool.study_pod.entity.base.BaseEntity;
import com.itschool.study_pod.enumclass.AccountRole;
import com.itschool.study_pod.ifs.Convertible;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Table(name = "admins")
@SQLDelete(sql = "UPDATE admins SET is_deleted = true WHERE admin_id = ?")
@Where(clause = "is_deleted = false")
public class Admin extends BaseEntity implements Convertible<AdminRequest, AdminResponse> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "admin_id")
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AccountRole role;

    public static Admin of(AdminRequest request) { // create용
        if(request != null) {
            return Admin.builder()
                    .email(request.getEmail())
                    .password(request.getPassword())
                    .role(request.getRole())
                    .build();
        }
        return null;
    }

    @Override
    @Deprecated
    public void update(AdminRequest request) {
        updatePassword(request.getPassword());
    }

    public void updatePassword(String password) {
        this.password = password;
    }

    @Override
    public AdminResponse response() {
        return AdminResponse.builder()
                .id(this.id)
                .email(this.email)
                .password(this.password)
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
