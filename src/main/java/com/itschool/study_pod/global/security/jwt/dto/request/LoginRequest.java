package com.itschool.study_pod.global.security.jwt.dto.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class LoginRequest {

    @NotEmpty
    private String email;

    @NotEmpty
    private String password;

}
