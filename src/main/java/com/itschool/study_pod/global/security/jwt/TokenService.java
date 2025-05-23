package com.itschool.study_pod.global.security.jwt;

import com.itschool.study_pod.global.base.account.Account;
import com.itschool.study_pod.global.security.jwt.dto.request.LoginRequest;
import com.itschool.study_pod.global.security.jwt.redis.RefreshTokenRepository;
import com.itschool.study_pod.global.security.jwt.dto.response.TokenResponse;
import com.itschool.study_pod.global.security.jwt.redis.RefreshToken;
import com.itschool.study_pod.global.base.account.AccountRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TokenService {

    private final AuthenticationManager authenticationManager;

    private final TokenProvider tokenProvider;  // JWT 토큰 생성, 검증 담당

    private final RefreshTokenRepository refreshTokenRepository;  // Redis에 저장된 리프레시 토큰 저장소

    private final AccountRepository accountRepository;  // 사용자 계정 조회용 저장소

    private final PasswordEncoder bCryptPasswordEncoder;  // 비밀번호 암호화 매칭용 (BCrypt)


    public TokenResponse login(LoginRequest request) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );

        Account account = (Account) authentication.getPrincipal();

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
        RefreshToken tokenEntity = tokenProvider.findByRefreshToken(refreshToken);

        // 3. 리프레시 토큰 삭제 (무효화 처리)
        refreshTokenRepository.delete(tokenEntity);
    }
}
