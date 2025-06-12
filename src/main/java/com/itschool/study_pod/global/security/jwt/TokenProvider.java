package com.itschool.study_pod.global.security.jwt;

import com.itschool.study_pod.global.base.account.Account;
import com.itschool.study_pod.global.base.account.AccountDetails;
import com.itschool.study_pod.global.base.account.AccountRepository;
import com.itschool.study_pod.global.security.jwt.redis.RefreshToken;
import com.itschool.study_pod.global.security.jwt.redis.RefreshTokenRepository;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.time.Duration;
import java.util.Base64;
import java.util.Date;

@Slf4j
@RequiredArgsConstructor
@Service
public class TokenProvider {

    // JWT에 담길 사용자 ID 클레임 키 이름
    private static final String CLAIM_ID = "id";

    private static final String TOKEN_TYPE = "token_type";

    private final JwtProperties jwtProperties;       // application.yml에서 읽은 JWT 설정값

    private final AccountRepository accountRepository;  // DB에서 사용자 정보 조회용

    private final RefreshTokenRepository refreshTokenRepository;


    // 인코딩된 비밀키를 디코딩해 SecretKey 객체를 캐싱 (접근용, 리프레시용)
    private SecretKey accessSecretKey;
    private SecretKey refreshSecretKey;

    /**
     * Account 객체를 기반으로 1시간 유효한 Access Token 생성
     */
    public String generateAccessToken(AccountDetails account) {
        return generateToken(account, Duration.ofHours(1), getAccessSecretKey(), "Access");
    }

    /**
     * Account 객체를 기반으로 5시간 유효한 Refresh Token 생성
     */
    public String generateRefreshToken(AccountDetails account) {
        return generateToken(account, Duration.ofHours(5), getRefreshSecretKey(), "Refresh");
    }

    /**
     * JWT 토큰 생성 공통 메서드
     * @param account 사용자 정보
     * @param expiry 토큰 유효 기간
     * @param secretKey 서명용 SecretKey
     * @return 생성된 JWT 토큰 문자열
     */
    private String generateToken(AccountDetails account, Duration expiry, SecretKey secretKey, String tokenType) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + expiry.toMillis());

        return Jwts.builder()
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE)  // JWT 타입 명시
                .setIssuer(jwtProperties.getIssuer())          // 발급자 정보
                .setIssuedAt(now)                              // 발급 시간
                .setExpiration(expiryDate)                         // 만료 시간
                .setSubject(account.getUsername())                // 토큰 주체 (이메일)
                .claim(CLAIM_ID, account.getId())              // 사용자 ID 클레임 추가
                .claim(TOKEN_TYPE, tokenType)                   // 토큰 타입 클레임 추가
                .signWith(secretKey, SignatureAlgorithm.HS256) // 서명 알고리즘 및 키 설정
                .compact();
    }

    /**
     * Access Token 유효성 검사
     * @param token 검증할 JWT Access Token 문자열
     * @return 토큰이 유효하면 true, 아니면 false 반환
     */
    public boolean validateAccessToken(String token) {
        return validateToken(token, getAccessSecretKey(), "Access");
    }

    /**
     * Refresh Token 유효성 검사
     * @param token 검증할 JWT Refresh Token 문자열
     * @return 토큰이 유효하면 true, 아니면 false 반환
     */
    public boolean validateRefreshToken(String token) {
        return validateToken(token, getRefreshSecretKey(), "Refresh");
    }

    /**
     * 공통 JWT 토큰 유효성 검사 메서드
     * <p>
     * 지정된 시크릿 키로 JWT 토큰의 서명 및 만료를 검증합니다.
     *
     * @param token JWT 토큰 문자열
     * @param secretKey 서명 검증에 사용할 비밀키
     * @param tokenType 토큰 종류명 (로그용, 예: "Access" 또는 "Refresh")
     * @return 유효하면 true, 아니면 false 반환
     */
    private boolean validateToken(String token, SecretKey secretKey, String tokenType) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (ExpiredJwtException e) {
            log.warn("JWT {} 토큰 만료됨: {}", tokenType, e.getMessage());
            return false;
        } catch (JwtException e) {
            log.warn("유효하지 않은 JWT {} 토큰: {}", tokenType, e.getMessage());
            return false;
        }
    }

    /**
     * Access Token에서 Claims 파싱
     * @param token Access Token 문자열
     * @return 토큰 내부 Claims 객체
     * @throws IllegalArgumentException 유효하지 않은 토큰인 경우 발생
     */
    public Claims getAccessTokenClaims(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(getAccessSecretKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (JwtException e) {
            log.warn("Failed to parse JWT claims: {}", e.getMessage());
            throw new IllegalArgumentException("유효하지 않은 토큰입니다.");
        }
    }

    /**
     * JWT 토큰에서 사용자 인증 객체 생성
     * @param token Access Token 문자열
     * @return 인증에 필요한 Authentication 객체 반환
     */
    public Authentication getAuthentication(String token) {
        Claims claims = getAccessTokenClaims(token);

        Long id = claims.get(CLAIM_ID, Long.class);
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("사용자 정보를 찾을 수 없습니다."));

        // 비밀번호가 변경된 이후에 모든 연결을 끊기 위한 로직도 추후 필요.
        // DB 에 있는 refresh token에 상태 값을 통해 유효하지 않으면 클라이언트의 access 토큰과 refresh 토큰 없애기

        AccountDetails accountDetails = new AccountDetails(account);

        return new UsernamePasswordAuthenticationToken(accountDetails, token, new AccountDetails(account).getAuthorities());
    }

    /**
     * 토큰에서 사용자 ID만 추출
     * @param token Access Token 문자열
     * @return 사용자 ID (Long)
     */
    public Long getUserId(String token) {
        return getAccessTokenClaims(token).get(CLAIM_ID, Long.class);
    }


    public String refreshAccessToken(String refreshToken) {
        // 1. 리프레시 토큰 유효성 검사
        if(!validateRefreshToken(refreshToken)) {
            throw new IllegalArgumentException("Unexpected token");
        }

        // 2. Redis에서 리프레시 토큰과 매칭되는 사용자 ID 조회
        Long accountId = findByRefreshToken(refreshToken).getAccountId();

        // 3. 사용자 존재 확인
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new IllegalArgumentException("해당 유저가 DB에서 삭제된 케이스"));

        // 4. 새로운 액세스 토큰 발급 및 반환
        return generateAccessToken(new AccountDetails(account));
    }

    public RefreshToken findByRefreshToken(String refreshToken) {
        // 리프레시 토큰으로 Redis 저장소 조회, 없으면 예외 발생
        return refreshTokenRepository.findByRefreshToken(refreshToken)
                .orElseThrow(() -> new EntityNotFoundException("해당 Refresh 토큰이 없음"));
    }
    
    // 쿠키 생성 메서드 (SameSite 속성은 헤더로 직접 추가, 메소드 위치 다른 클래스 이관 고민)
    public static void addCookie(HttpServletResponse response, String name, String value, int maxAgeSeconds) {
        Cookie cookie = new Cookie(name, value);
        cookie.setPath("/");
        cookie.setMaxAge(maxAgeSeconds);
        response.addCookie(cookie);

        // SameSite 직접 헤더로 추가 (Java Servlet API 쿠키 객체는 SameSite 미지원)
        String cookieHeader = String.format("%s=%s; Max-Age=%d; Path=/; Secure; HttpOnly; SameSite=None",
                name, value, maxAgeSeconds);
        response.addHeader("Set-Cookie", cookieHeader);
    }

    /**
     * application.yml의 'jwt' 프리픽스 설정을 매핑하는 클래스
     */
    @Getter
    @Setter
    @NoArgsConstructor
    @Configuration
    @ConfigurationProperties(prefix = "jwt")
    private static class JwtProperties {
        private String issuer;         // 토큰 발급자
        private String accessSecret;   // Base64 인코딩된 Access Token용 시크릿 키
        private String refreshSecret;  // Base64 인코딩된 Refresh Token용 시크릿 키
    }

    /**
     * Base64로 인코딩된 시크릿 키를 디코딩하여
     * HMAC-SHA256 서명에 사용할 SecretKey 객체 생성
     */
    private SecretKey decodeSecretKey(String base64EncodedKey) {
        byte[] decodedKey = Base64.getDecoder().decode(base64EncodedKey);
        return Keys.hmacShaKeyFor(decodedKey);
    }

    /**
     * 액세스 토큰용 SecretKey 반환 (최초 호출 시 디코딩 후 캐싱)
     */
    public SecretKey getAccessSecretKey() {
        if (accessSecretKey == null) {
            accessSecretKey = decodeSecretKey(jwtProperties.getAccessSecret());
        }
        return accessSecretKey;
    }

    /**
     * 리프레시 토큰용 SecretKey 반환 (최초 호출 시 디코딩 후 캐싱)
     */
    public SecretKey getRefreshSecretKey() {
        if (refreshSecretKey == null) {
            refreshSecretKey = decodeSecretKey(jwtProperties.getRefreshSecret());
        }
        return refreshSecretKey;
    }
}
