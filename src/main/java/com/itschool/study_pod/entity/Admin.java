package com.itschool.study_pod.entity;

import com.itschool.study_pod.entity.base.BaseEntity;
import com.itschool.study_pod.enumclass.AccountRole;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Table(name = "admins")
public class Admin extends BaseEntity {

    @Id
    @Column(name = "admin_id")
    private Long id;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AccountRole role;
}
