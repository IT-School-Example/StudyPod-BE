package com.itschool.study_pod.domain.admin.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Data // 종합선물세트 : @Getter, @Setter, @ToString, @EqualsAndHashCode, @RequiredArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class AdminRequest {

    @NotEmpty
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
    @Email(message = "이메일 형식이 올바르지 않습니다.")
    private String email;

    @NotEmpty
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[!@#$%^&*]).{8,16}$",
            message = "비밀번호는 최소 8자 이상, 숫자, 대문자 또는 소문자, 특수문자를 포함해야 합니다.")
    private String password;

    /*@NotNull
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
    private AccountRole role;*/

}
