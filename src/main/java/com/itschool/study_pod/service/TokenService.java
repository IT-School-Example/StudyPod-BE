package com.itschool.study_pod.service;

import com.itschool.study_pod.security.TokenProvider;
import com.itschool.study_pod.dto.request.LoginRequest;
import com.itschool.study_pod.dto.response.TokenResponse;
import com.itschool.study_pod.entity.base.Account;
import com.itschool.study_pod.entity.redis.RefreshToken;
import com.itschool.study_pod.repository.AccountRepository;
import com.itschool.study_pod.repository.RefreshTokenRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TokenService {

    private final TokenProvider tokenProvider;  // JWT 토큰 생성, 검증 담당

    private final RefreshTokenRepository refreshTokenRepository;  // Redis에 저장된 리프레시 토큰 저장소

    private final AccountRepository accountRepository;  // 사용자 계정 조회용 저장소

    private final PasswordEncoder bCryptPasswordEncoder;  // 비밀번호 암호화 매칭용 (BCrypt)


    public TokenResponse login(LoginRequest request) {

        // 1. 이메일로 계정 조회 (존재하지 않으면 예외 발생)
        Account account = accountRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("잘못된 이메일"));

        // 2. 입력한 비밀번호와 DB에 저장된 암호화된 비밀번호 비교
        if (!bCryptPasswordEncoder.matches(request.getPassword(), account.getPassword())) {
            throw new IllegalArgumentException("잘못된 이메일 또는 비밀번호");
        }

        // 3. 액세스 토큰과 리프레시 토큰 생성
        String accessToken = tokenProvider.generateAccessToken(account);
        String refreshToken = tokenProvider.generateRefreshToken(account);

        // 4. 리프레시 토큰 Redis 저장 (사용자 ID를 키로 사용)
        refreshTokenRepository.save(RefreshToken.builder()
                .accountId(account.getId())
                .refreshToken(refreshToken)
                .build());

        // 5. 토큰 응답 반환 (액세스, 리프레시 토큰 포함)
        return TokenResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }


    public void logout(String refreshToken) {
        // 1. 리프레시 토큰 유효성 검사 (만료 또는 변조 확인)
        if (!tokenProvider.validateRefreshToken(refreshToken)) {
            throw new IllegalArgumentException("Invalid refresh token");
        }

        // 2. Redis에서 해당 리프레시 토큰 엔티티 조회 (없으면 예외 발생)
        RefreshToken tokenEntity = findByRefreshToken(refreshToken);

        // 3. 리프레시 토큰 삭제 (무효화 처리)
        refreshTokenRepository.delete(tokenEntity);
    }


    public String refreshAccessToken(String refreshToken) {
        // 1. 리프레시 토큰 유효성 검사
        if(!tokenProvider.validateRefreshToken(refreshToken)) {
            throw new IllegalArgumentException("Unexpected token");
        }

        // 2. Redis에서 리프레시 토큰과 매칭되는 사용자 ID 조회
        Long accountId = findByRefreshToken(refreshToken).getAccountId();

        // 3. 사용자 존재 확인
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new IllegalArgumentException("해당 유저가 DB에서 삭제된 케이스"));

        // 4. 새로운 액세스 토큰 발급 및 반환
        return tokenProvider.generateAccessToken(account);
    }


    private RefreshToken findByRefreshToken(String refreshToken) {
        // 리프레시 토큰으로 Redis 저장소 조회, 없으면 예외 발생
        return refreshTokenRepository.findByRefreshToken(refreshToken)
                .orElseThrow(() -> new EntityNotFoundException("해당 Refresh 토큰이 없음"));
    }
}
