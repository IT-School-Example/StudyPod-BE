package com.itschool.study_pod.domain.admin.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class AdminPasswordUpdateRequest {

    @NotEmpty
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[!@#$%^&*]).{8,16}$",
            message = "비밀번호는 최소 8자 이상, 숫자, 대문자 또는 소문자, 특수문자를 포함해야 합니다.")
    private String password;
}
