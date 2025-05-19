package com.itschool.study_pod.global.security.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@RequiredArgsConstructor
// DispatcherServlet 진입 전에 요청을 가로채는 Servlet 레벨 필터
public class TokenAuthenticationFilter extends OncePerRequestFilter {

    private final TokenProvider tokenProvider;

    private final static String HEADER_AUTHORIZATION = "Authorization";

    private final static String TOKEN_PREFIX = "Bearer";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        // 요청 헤더의 Authorization 키의 값 조회
        // String authorizationHeader = request.getHeader(HEADER_AUTHORIZATION);

        // 가져온 값에서 접두사 제거
        // String token = getAccessToken(authorizationHeader);

        // 헤더 대신 쿠키에서 토큰을 가져옴
        String token = getAccessTokenFromCookie(request);

        // 가져온 토큰이 유효한지 확인하고, 유효한 때는 인증 정보 설정
        if (token != null) {
            if (tokenProvider.validateAccessToken(token)) {
                Authentication authentication;
                try {
                    authentication = tokenProvider.getAuthentication(token);
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                } catch (Exception e) {
                    // 인증 객체 생성 실패 시 쿠키 삭제 : DB에 해당 유저가 존재하지 않거나 삭제된 경우
                    TokenProvider.addCookie(response, "accessToken", null, 0);
                    TokenProvider.addCookie(response, "refreshToken", null, 0);

                    response.setContentType("application/json;charset=UTF-8");
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    response.getWriter().write("{\"error\":\"Unauthorized\",\"message\":\"인증 실패: 토큰이 잘못되었거나 사용자 정보 없음.\"}");
                    return;
                }
            } else {
                // 토큰은 있으나 유효하지 않은 경우 → 401 에러 응답
                response.setContentType("application/json;charset=UTF-8");
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write("{\"error\":\"Unauthorized\",\"message\":\"토큰이 만료되었거나 유효하지 않습니다.\"}");
                return;
            }
        }
        // 토큰이 없는 경우는 인증 없이 그냥 다음 필터로 넘김
        filterChain.doFilter(request, response);
    }

    private String getAccessTokenFromCookie(HttpServletRequest request) {
        if (request.getCookies() == null) {
            return null;
        }
        for (Cookie cookie : request.getCookies()) {
            if ("accessToken".equals(cookie.getName())) {
                return cookie.getValue();
            }
        }
        return null;
    }

    private String getAccessToken(String authorizationHeader) {
        if(authorizationHeader != null && authorizationHeader.startsWith(TOKEN_PREFIX)) {
            return authorizationHeader.substring(TOKEN_PREFIX.length()).trim();
        }
        return null;
    }
}
