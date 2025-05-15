package com.itschool.study_pod.config.jwt;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties("jwt")
public class JwtProperties {

    // 발급자
    private String issuer;

    // BASE 64로 인코딩된 비밀키
    private String secretKey;
}
