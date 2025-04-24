package com.itschool.study_pod.entity;

import com.itschool.study_pod.dto.request.UserInformationRequest;
import com.itschool.study_pod.dto.request.UserPasswordRequest;
import com.itschool.study_pod.dto.request.UserRequest;
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
@Table(name = "users")
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AccountRole role;

    @Column(nullable = false)
    private String name;

    @Column(unique = true)
    private String nickname;

    // update용
    public void update(UserRequest request) {
        if(request instanceof UserInformationRequest informationRequest) {
            this.email = informationRequest.getEmail();
            this.name = informationRequest.getName();
        } else if(request instanceof UserPasswordRequest passwordRequest) {
            this.password = passwordRequest.getPassword();
        }
    }

    /*public void updateInformation(UserInformationRequest request) {
        this.email = request.getEmail();
        this.name = request.getName();
    }

    public void updatePassword(UserPasswordRequest request) {
        this.password = request.getPassword();
    }*/
}
