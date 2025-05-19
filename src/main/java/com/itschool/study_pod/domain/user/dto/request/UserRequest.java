package com.itschool.study_pod.domain.user.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Data // 종합선물세트 : @Getter, @Setter, @ToString, @EqualsAndHashCode, @RequiredArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class UserRequest {

    @NotEmpty
    @Email(message = "이메일 형식이 올바르지 않습니다.")
    private String email;

    @NotEmpty
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[!@#$%^&*]).{8,16}$",
            message = "비밀번호는 최소 8자 이상, 숫자, 대문자 또는 소문자, 특수문자를 포함해야 합니다.")
    private String password;

    // private AccountRole role; // User는 AccountRole.ROLE_USER 하나로 고정

    @NotEmpty
    @Pattern(regexp = "^[가-힣]+$", message = "이름은 한글만 입력 가능합니다.")
    private String name;

    // nickname 필수 값 아니니 필드 추가, 삭제 가능
    private String nickname;

    /*@Pattern(regexp = "^(01[0-9])-(\\d{3,4})-(\\d{4})$", message = "유효한 한국 핸드폰 번호를 입력하세요.")*/
}
