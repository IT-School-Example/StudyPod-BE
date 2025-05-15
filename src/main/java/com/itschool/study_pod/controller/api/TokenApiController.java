package com.itschool.study_pod.controller.api;

import com.itschool.study_pod.dto.Header;
import com.itschool.study_pod.dto.request.LoginRequest;
import com.itschool.study_pod.dto.response.TokenResponse;
import com.itschool.study_pod.service.TokenService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RestController
public class TokenApiController {

    private final TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity<TokenResponse> login(@RequestBody LoginRequest request,
                                               HttpServletResponse response) {
        TokenResponse tokenResponse = tokenService.login(request);

        // accessToken 쿠키 (예: 1시간 유효)
        addCookie(response, "accessToken", tokenResponse.getAccessToken(), 60 * 5);

        // refreshToken 쿠키 (예: 2시간 유효)
        addCookie(response, "refreshToken", tokenResponse.getRefreshToken(), 60 * 60 * 2);

        // 응답 바디는 토큰 노출 안 하거나 null 처리해도 됨
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(null);
    }

    @DeleteMapping("/logout")
    public ResponseEntity<Void> logout(HttpServletResponse response) {
        // 쿠키 삭제 (만료시간 0으로 설정)
        addCookie(response, "accessToken", null, 0);
        addCookie(response, "refreshToken", null, 0);

        return ResponseEntity.noContent().build();
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<TokenResponse> refreshAccessToken(@CookieValue("refreshToken") String refreshToken,
                                                            HttpServletResponse response) {
        String newAccessToken = tokenService.refreshAccessToken(refreshToken);

        // 새로운 accessToken 쿠키 발행
        addCookie(response, "accessToken", newAccessToken, 60 * 60);

        // refreshToken은 그대로 유지하거나 재발행 필요시 추가 코드 작성

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(null);
    }

    // 쿠키 생성 메서드 (SameSite 속성은 헤더로 직접 추가)
    private void addCookie(HttpServletResponse response, String name, String value, int maxAgeSeconds) {
        Cookie cookie = new Cookie(name, value);
        cookie.setHttpOnly(true);
        cookie.setSecure(true);   // HTTPS 환경에서만 전송
        cookie.setPath("/");
        cookie.setMaxAge(maxAgeSeconds);
        response.addCookie(cookie);

        // SameSite 직접 헤더로 추가 (Java Servlet API 쿠키 객체는 SameSite 미지원)
        String cookieHeader = String.format("%s=%s; Max-Age=%d; Path=/; Secure; HttpOnly; SameSite=Strict",
                name, value, maxAgeSeconds);
        response.addHeader("Set-Cookie", cookieHeader);
    }

    // 전역 예외 핸들링
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Header> handleException(Exception e) {
        log.error("Exception Occurred: ", e);
        String errorMessage = e.getClass().getSimpleName() + " : " + e.getMessage();
        Header errorResponse = Header.ERROR(errorMessage);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }
}
