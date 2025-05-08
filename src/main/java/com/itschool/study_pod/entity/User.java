package com.itschool.study_pod.entity;

import com.itschool.study_pod.dto.request.UserRequest;
import com.itschool.study_pod.dto.response.SubjectAreaResponse;
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

    @Column(nullable = false, unique = true)
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
                    .email(request.getEmail())
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
    public void update(UserRequest request) {
        if(request.getPassword() != null)
            this.password = request.getPassword();

        if(request.getNickname() != null)
            this.nickname = request.getNickname();
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

    public static User withId(Long id) {
        return User.builder()
                .id(id)
                .build();
    }
}
