package com.itschool.study_pod.global.config.error;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import java.io.IOException;

public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException) throws IOException {

        String uri = request.getRequestURI();

        // 이미 응답이 커밋되었는지 확인
        if (response.isCommitted()) {
            return;
        }

        try {
            if (uri.startsWith("/api/")) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.setContentType("application/json;charset=UTF-8");
                response.getWriter().write("{\"error\":\"Unauthorized\"}");
            } else if (!uri.equals("/login")) {
                response.sendRedirect("/login");
            }
        } catch (IOException e) {
            // 로깅 후 다시 던지기 (혹은 무시)
            e.printStackTrace();
            throw e;
        }
    }
}
