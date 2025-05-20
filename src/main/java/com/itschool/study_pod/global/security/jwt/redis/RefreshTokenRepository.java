package com.itschool.study_pod.global.security.jwt.redis;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {

    Optional<RefreshToken> findByAccountId(Long accountId);

    Optional<RefreshToken> findByRefreshToken(String refreshToken);
}
