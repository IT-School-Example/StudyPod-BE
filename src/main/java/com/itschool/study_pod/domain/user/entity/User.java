package com.itschool.study_pod.domain.user.entity;

import com.itschool.study_pod.domain.user.dto.request.UserRequest;
import com.itschool.study_pod.domain.user.dto.response.UserResponse;
import com.itschool.study_pod.global.base.account.Account;
import com.itschool.study_pod.global.enumclass.AccountRole;
import com.itschool.study_pod.global.base.crud.Convertible;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SuperBuilder
@Table(name = "users")
/*@SQLDelete(sql = "UPDATE users SET is_deleted = true WHERE account_id = ?")
@Where(clause = "is_deleted = false")*/
public class User extends Account implements Convertible<UserRequest, UserResponse> {

    @Column(unique = true)
    private String nickname;

    public static User of(UserRequest request) { // create용
        return User.builder()
                .email(request.getEmail())
                .password(request.getPassword())
                .role(AccountRole.ROLE_USER)
                .name(request.getName())
                .nickname(request.getNickname())
                .createdBy(request.getEmail())
                .build();
    }

    // update용
    @Override
    public void update(UserRequest request) {
        // PUT 전체 업데이트
        // 이메일 수정
        this.email = request.getEmail();

        // 비밀번호 수정 x
        // updatePassword(request.getPassword());

        // 역할 수정 x
        // this.role = request.getRole() != null? request.getRole() : this.role;

        // 이름 수정
        this.name = request.getName();

        // 닉네임 수정 (처음 가입할때 null을 허용하나, null로 update 요청 시 현재 닉네임 유지)
        this.nickname = request.getNickname() != null? request.getNickname() : this.nickname;
    }

    public void updatePassword(String password) {
        // PATCH 일부 업데이트
        // 비밀번호 null로 수정 불가 (null로 수정 요청 오면 현재 비밀번호 유지)
        this.password = password != null? password : this.password;
    }

    @Override
    public UserResponse response() {
        return UserResponse.builder()
                .id(this.id)
                .email(this.email)
                // .role(this.role)
                .name(this.name)
                .nickname(this.nickname)
                .createdAt(this.createdAt)
                .createdBy(this.createdBy)
                .updatedAt(this.updatedAt)
                .updatedBy(this.updatedBy)
                .build();
    }

    public static User withId(Long id) {
        return User.builder()
                .id(id)
                .build();
    }
}