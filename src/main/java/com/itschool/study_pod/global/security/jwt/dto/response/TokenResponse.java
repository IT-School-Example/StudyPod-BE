package com.itschool.study_pod.global.security.jwt.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class TokenResponse {
    private String accessToken;

    private String refreshToken;
}