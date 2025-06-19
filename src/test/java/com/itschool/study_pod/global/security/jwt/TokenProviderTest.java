package com.itschool.study_pod.global.security.jwt;

import com.itschool.study_pod.StudyPodApplicationTests;
import com.itschool.study_pod.domain.user.entity.User;
import com.itschool.study_pod.global.base.account.Account;
import com.itschool.study_pod.global.base.account.AccountDetails;
import com.itschool.study_pod.global.enumclass.AccountRole;
import com.itschool.study_pod.domain.user.repository.UserRepository;
import com.itschool.study_pod.global.security.jwt.redis.RefreshToken;
import com.itschool.study_pod.global.security.jwt.redis.RefreshTokenRepository;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.util.Date;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
class TokenProviderTest extends StudyPodApplicationTests {

    @Autowired
    private TokenProvider tokenProvider;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    private User testUser;

    @BeforeEach
    public void beforeSetUp() {
        // given
        testUser = userRepository.save(User.builder()
                .email(UUID.randomUUID() + "@gmail.com")
                .password("test")
                .name("테스터")
                .role(AccountRole.ROLE_USER)
                .build());
    }


    @DisplayName("유저 정보와 만료 기간을 전달해 토큰을 만들 수 있다.")
    @Test
    void generateAccessToken() {
        // when
        String token = tokenProvider.generateAccessToken(new AccountDetails(testUser));

        // System.out.println("Generated Token: " + token);

        // then
        Long userId = Jwts.parserBuilder()
                .setSigningKey(tokenProvider.getAccessSecretKey())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .get("id", Long.class);

        assertThat(userId).isEqualTo(testUser.getId());
    }

    @DisplayName("만료된 토큰인 경우에 유효성 검증에 실패한다.")
    @Test
    void invalidToken() {

        // when
        String token = Jwts.builder()
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
                .setIssuer("issuer")
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime() - Duration.ofDays(7).toMillis()))
                .signWith(tokenProvider.getAccessSecretKey(), SignatureAlgorithm.HS256)
                .compact();

        boolean result = tokenProvider.validateAccessToken(token);

        // then
        assertThat(result).isFalse();
    }


    @DisplayName("유효한 토큰인 경우에 유효성 검증에 성공한다.")
    @Test
    void validToken() {
        // when
        String token = tokenProvider.generateAccessToken(new AccountDetails(testUser));

        boolean result = tokenProvider.validateAccessToken(token);

        // then
        assertThat(result).isTrue();
    }


    @DisplayName("토큰 기반으로 인증정보를 가져올 수 있다.")
    @Test
    void getAuthentication() {
        // given
        String token = tokenProvider.generateAccessToken(new AccountDetails(testUser));

        boolean result = tokenProvider.validateAccessToken(token);

        // when
        Authentication authentication = tokenProvider.getAuthentication(token);

        // then
        assertThat(result).isTrue();
        AccountDetails accountDetails = (AccountDetails) authentication.getPrincipal();
        assertThat(accountDetails.getUsername()).isEqualTo(testUser.getEmail());
    }

    @DisplayName("토큰으로 유저 ID를 가져올 수 있다.")
    @Test
    void getUserId() {
        // given
        String token = tokenProvider.generateAccessToken(new AccountDetails(testUser));

        // when
        Long userIdByToken = tokenProvider.getUserId(token);

        // then
        assertThat(userIdByToken).isEqualTo(testUser.getId());
    }

    @DisplayName("리프레시 토큰으로 새로운 액세스 토큰을 발급 받을 수 있다.")
    @Test
    void refreshAccessToken() {
        // given
        String refreshToken = tokenProvider.generateRefreshToken(new AccountDetails(testUser));
        refreshTokenRepository.save(RefreshToken.builder()
                .accountId(testUser.getId())
                .refreshToken(refreshToken)
                .build());

        // when
        String newAccessToken = tokenProvider.refreshAccessToken(refreshToken);

        // then
        assertThat(newAccessToken).isNotNull();
        assertThat(tokenProvider.validateAccessToken(newAccessToken)).isTrue();
    }
}
