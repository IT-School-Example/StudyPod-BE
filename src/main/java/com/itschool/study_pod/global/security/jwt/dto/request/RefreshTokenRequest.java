package com.itschool.study_pod.global.security.jwt.dto.request;

import lombok.*;

@Getter
public class RefreshTokenRequest {
    private String refreshToken;
}