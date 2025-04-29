package com.itschool.study_pod.entity;

import com.itschool.study_pod.dto.request.UserRequest;
import com.itschool.study_pod.dto.response.UserResponse;
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
@Table(name = "users")
@SQLDelete(sql = "UPDATE users SET is_deleted = true WHERE user_id = ?")
@Where(clause = "is_deleted = false")
public class User extends BaseEntity implements Convertible<UserRequest, UserResponse> {

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

    public static User of(UserRequest request) { // create용
        if(request != null) {
            return User.builder()
                    .id(request.getId())
                    .email(request.getName())
                    .password(request.getPassword())
                    .role(request.getRole())
                    .name(request.getName())
                    .nickname(request.getNickname())
                    .build();
        }
        return null;
    }

    // update용
    @Override
    @Deprecated
    public void update(UserRequest request) {
        updatePassword(request.getPassword());
        updateNickName(request.getNickname());
    }

    public void updatePassword(String password) {
        this.password = password;
    }

    public void updateNickName(String nickname) {
        this.nickname = nickname;
    }

    @Override
    public UserResponse response() {
        return UserResponse.builder()
                .id(this.id)
                .email(this.email)
                .role(this.role)
                .name(this.name)
                .nickname(this.nickname)
                .createdAt(this.createdAt)
                .createdBy(this.createdBy)
                .updatedAt(this.updatedAt)
                .updatedBy(this.updatedBy)
                .build();
    }
}
