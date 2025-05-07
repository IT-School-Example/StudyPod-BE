package com.itschool.study_pod.dto.request;

import com.itschool.study_pod.enumclass.AccountRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data // 종합선물세트 : @Getter, @Setter, @ToString, @EqualsAndHashCode, @RequiredArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class UserRequest {

    // fk용으로 필요
    private Long id;

    @Email(message = "이메일 형식이 올바르지 않습니다.")
    private String email;

    @Size(min = 8, max = 20, message = "비밀번호는 8~20자여야 합니다.")
    private String password;

    private AccountRole role;

    private String name;

    // nickname 필수 값 아니니 필드 추가, 삭제 가능
    private String nickname;
}
