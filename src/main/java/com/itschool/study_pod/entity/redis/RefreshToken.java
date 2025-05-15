package com.itschool.study_pod.entity.redis;

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
    @Column(name = "", updatable = false)
    private Long id;

    @Column(name = "account_id", nullable = false, unique = true)
    private Long accountId;

    @Column(name = "refresh_token", nullable = false)
    private String refreshToken;

    public void update(String newRefreshToken) {
        this.refreshToken = newRefreshToken;
    }
}