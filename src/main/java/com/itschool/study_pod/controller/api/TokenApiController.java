package com.itschool.study_pod.controller.api;

import com.itschool.study_pod.dto.Header;
import com.itschool.study_pod.dto.request.LoginRequest;
import com.itschool.study_pod.dto.response.TokenResponse;
import com.itschool.study_pod.entity.base.Account;
import com.itschool.study_pod.security.TokenProvider;
import com.itschool.study_pod.service.TokenService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RestController
public class TokenApiController {

    private final TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity<Void> login(@RequestBody LoginRequest request,
                                               HttpServletResponse response) {
        TokenResponse tokenResponse = tokenService.login(request);

        // accessToken 쿠키 (예: 1시간 유효)
        TokenProvider.addCookie(response, "accessToken", tokenResponse.getAccessToken(), 60 * 5);

        // refreshToken 쿠키 (예: 2시간 유효)
        TokenProvider.addCookie(response, "refreshToken", tokenResponse.getRefreshToken(), 60 * 60 * 2);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(null);
    }
    
    // Security /logout 기본 method인 post 차용, 변경 시 클라이언트도 반영 필요
    @PostMapping("/logout")
    public ResponseEntity<Void> logout(HttpServletResponse response) {
        // 쿠키 삭제 (만료시간 0으로 설정)
        TokenProvider.addCookie(response, "accessToken", null, 0);
        TokenProvider.addCookie(response, "refreshToken", null, 0);

        // 리다이렉트 응답
        response.setStatus(HttpServletResponse.SC_FOUND); // 302
        response.setHeader("Location", "/"); // 또는 원하는 URL

        return ResponseEntity.status(HttpServletResponse.SC_FOUND).build();
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<TokenResponse> refreshAccessToken(@CookieValue("refreshToken") String refreshToken,
                                                            HttpServletResponse response) {
        String newAccessToken = tokenService.refreshAccessToken(refreshToken);

        // 새로운 accessToken 쿠키 발행
        TokenProvider.addCookie(response, "accessToken", newAccessToken, 60 * 60);

        // refreshToken은 그대로 유지하거나 재발행 필요시 추가 코드 작성

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(null);
    }

    @GetMapping("/me")
    public ResponseEntity<String> getCurrentUserName(@AuthenticationPrincipal Account userDetails) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(userDetails.getUsername());
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
