package com.itschool.study_pod.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class TokenInfo {
    private String grantType;
    private String accessToken;
    private String refreshToken;
}