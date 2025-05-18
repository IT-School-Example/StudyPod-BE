package com.itschool.study_pod.repository;

import com.itschool.study_pod.entity.redis.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {

    Optional<RefreshToken> findByAccountId(Long accountId);

    Optional<RefreshToken> findByRefreshToken(String refreshToken);
}
