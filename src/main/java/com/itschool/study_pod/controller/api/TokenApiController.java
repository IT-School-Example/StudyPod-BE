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

    // login 메서드 파라미터에 HttpServletResponse 추가
    @PostMapping("/login")
    public ResponseEntity<TokenResponse> login(@RequestBody LoginRequest request,
                                               HttpServletResponse response) {

        TokenResponse tokenResponse = tokenService.login(request);

        // Refresh Token을 HttpOnly + Secure 쿠키로 설정
        Cookie refreshTokenCookie = new Cookie("refreshToken", tokenResponse.getRefreshToken());
        refreshTokenCookie.setHttpOnly(true);
        refreshTokenCookie.setSecure(true);  // HTTPS 환경에서만 전송 (탈취 불가)
        refreshTokenCookie.setPath("/");     // 전체 도메인 경로에 대해 전송
        refreshTokenCookie.setMaxAge(60 * 60 * 2); // 토큰 만료와 일치 시켜야함. 2시간 유효기간 (초 단위)
        response.addCookie(refreshTokenCookie);

        // 응답 바디에는 Access Token만 보냄 (또는 Refresh Token을 null 처리)
        TokenResponse responseBody = new TokenResponse(tokenResponse.getAccessToken(), null);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(responseBody); // Access Toke은 클라이언트에서 Authorization Header에 삽입
    }

    @DeleteMapping("/logout")
    public ResponseEntity<Void> logout(@CookieValue("refreshToken") String refreshToken,
                                       HttpServletResponse response) {
        tokenService.logout(refreshToken);

        // 로그아웃 시 쿠키 삭제 (만료시켜서 클라이언트에서 삭제하도록 함)
        Cookie deleteCookie = new Cookie("refreshToken", null);
        deleteCookie.setHttpOnly(true);
        deleteCookie.setSecure(true);
        deleteCookie.setPath("/");
        deleteCookie.setMaxAge(0);
        response.addCookie(deleteCookie);

        return ResponseEntity.noContent().build();
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<TokenResponse> refreshAccessToken(@CookieValue("refreshToken") String refreshToken) {
        String newAccessToken = tokenService.refreshAccessToken(refreshToken);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new TokenResponse(newAccessToken, null)); // Access Toke은 클라이언트에서 Authorization Header에 삽입
    }

    // 전역 예외 핸들링용 핸들러 (Controller 내에서 발생하는 예외 처리)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Header> handleException(Exception e) {
        // 예외 정보 로그
        log.error("Exception Occurred: ", e);

        // 클라이언트에게 반환할 메시지 생성
        String errorMessage = e.getClass().getSimpleName() + " : " + e.getMessage();

        // 에러 응답 본문 생성
        Header errorResponse = Header.ERROR(errorMessage);

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(errorResponse);
    }
}
