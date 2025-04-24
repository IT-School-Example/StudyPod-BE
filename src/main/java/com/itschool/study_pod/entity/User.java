package com.itschool.study_pod.entity;

import com.itschool.study_pod.dto.request.User.UserCreateRequest;
import com.itschool.study_pod.dto.request.User.UserInformationRequest;
import com.itschool.study_pod.dto.request.User.UserPasswordRequest;
import com.itschool.study_pod.dto.request.User.UserRequest;
import com.itschool.study_pod.entity.base.BaseEntity;
import com.itschool.study_pod.enumclass.AccountRole;
import com.itschool.study_pod.ifs.Updatable;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Table(name = "users")
public class User extends BaseEntity implements Updatable<UserRequest> {

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

    public static User of (UserCreateRequest request) { // create용
        return User.builder()
                .email(request.getName())
                .password(request.getPassword())
                .role(request.getRole())
                .name(request.getName())
                .build();
    }

    // update용
    @Override
    public void update(UserRequest request) {
        if(request instanceof UserInformationRequest informationRequest) {
            this.email = informationRequest.getEmail();
            this.name = informationRequest.getName();
        } else if(request instanceof UserPasswordRequest passwordRequest) {
            this.password = passwordRequest.getPassword();
        } else {
            throw new IllegalArgumentException("지원하지 않는 요청 타입입니다: " + request.getClass().getSimpleName());
        }
    }
}
