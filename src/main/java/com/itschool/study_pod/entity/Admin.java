package com.itschool.study_pod.entity;

import com.itschool.study_pod.dto.request.Admin.AdminRequest;
import com.itschool.study_pod.dto.request.Board.BoardUpdateRequest;
import com.itschool.study_pod.dto.request.Comment.CommentRequest;
import com.itschool.study_pod.dto.response.AdminResponse;
import com.itschool.study_pod.entity.base.BaseEntity;
import com.itschool.study_pod.enumclass.AccountRole;
import com.itschool.study_pod.ifs.Convertible;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

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

    public static Admin of(AdminRequest request) { // create용
        return Admin.builder()
                .build();
    }

    @Override
    public void update(AdminRequest request) {

    }

    @Override
    public AdminResponse response() {
        return null;
    }
}
