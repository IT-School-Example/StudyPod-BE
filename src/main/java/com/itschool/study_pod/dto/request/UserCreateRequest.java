package com.itschool.study_pod.dto.request;

import com.itschool.study_pod.entity.User;
import com.itschool.study_pod.enumclass.AccountRole;
import lombok.*;

@Data // 종합선물세트 : @Getter, @Setter, @ToString, @EqualsAndHashCode, @RequiredArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class UserCreateRequest extends UserRequest {

    private String email;

    private String password;

    private AccountRole role;

    private String name;

    // nickname 필수 값 아니니 필드 추가, 삭제 가능
    private String nickname;

    public User toEntity() { // create용
        return User.builder()
                .email(email)
                .password(password)
                .role(role)
                .name(name)
                .build();
    }
}
