package com.itschool.study_pod.global.security.jwt.redis;

import jakarta.persistence.*;
import lombok.*;
// import org.springframework.data.redis.core.RedisHash;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Table(name = "refresh_tokens")
// @RedisHash(value = "refresh_token", timeToLive = 7200) // key prefix와 TTL(초 단위)
public class RefreshToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "refresh_token_id", updatable = false)
    private Long id;

    @Column(name = "account_id", nullable = false)
    private Long accountId;

    @Column(name = "refresh_token", nullable = false, length = 512)
    private String refreshToken;

    public void update(String newRefreshToken) {
        this.refreshToken = newRefreshToken;
    }
}