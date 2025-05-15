package com.itschool.study_pod.config.jwt;

import com.itschool.study_pod.entity.base.Account;
import com.itschool.study_pod.enumclass.AccountRole;
import com.itschool.study_pod.repository.AdminRepository;
import com.itschool.study_pod.repository.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.time.Duration;
import java.util.Base64;
import java.util.Date;

@RequiredArgsConstructor
@Service
public class TokenProvider {

    private final JwtProperties jwtProperties;

    private final AdminRepository adminRepository;

    private final UserRepository userRepository;

    private SecretKey secretKey;


    public SecretKey getSecretKey() {

        if(secretKey == null) {
            String base64EncodedKey = jwtProperties.getSecretKey(); // c3R1ZHlwb2Qtc3ByaW5nYm9vdC1zZWNyZXQta2V5LXRlc3Q=

            byte[] decodedKey = Base64.getDecoder().decode(base64EncodedKey); // studypod-springboot-secret-key-test

            secretKey = Keys.hmacShaKeyFor(decodedKey);
        }

        return secretKey;
    }

    public String generateToken(Account account, Duration expiredAt) {
        Date now = new Date();

        return Jwts.builder()
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
                .setIssuer(jwtProperties.getIssuer())
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + expiredAt.toMillis()))
                .setSubject(account.getEmail())
                .claim("id", account.getId())
                .claim("role", account.getRole().name())
                .signWith(getSecretKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public boolean validToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(getSecretKey())
                    .build()
                    .parseClaimsJws(token);

            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public Authentication getAuthentication(String token) {
        Claims claims = getClaims(token);

        Long id = claims.get("id", Long.class);
        String roleName = claims.get("role", String.class);
        AccountRole role = AccountRole.valueOf(roleName);

        Account account;

        switch (role) {
            case ROLE_ADMIN:
                account = adminRepository.findById(id)
                        .orElseThrow(() -> new RuntimeException("관리자 정보를 찾을 수 없습니다."));
                break;
            case ROLE_USER:
                account = userRepository.findById(id)
                        .orElseThrow(() -> new RuntimeException("사용자 정보를 찾을 수 없습니다."));
                break;
            default:
                throw new RuntimeException("알 수 없는 역할: " + roleName);
        }

        return new UsernamePasswordAuthenticationToken(account, token, account.getAuthorities());
    }

    public Long getUserId(String token) {
        Claims claims = getClaims(token);

        return claims.get("id", Long.class);
    }

    private Claims getClaims(String token) {

        return Jwts.parserBuilder()
                .setSigningKey(getSecretKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
