package com.itschool.study_pod.config;

import com.itschool.study_pod.enumclass.AccountRole;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {

    // 스프링 시큐리티 기능 비활성화 : Security FilterChain 자체를 거치지 않도록 설정하는 메서드
    @Bean
    @Profile({"local", "dev"})
    public WebSecurityCustomizer configure() {
        return web -> web.ignoring()
                // 개발 및 테스트 환경에서만 허용할 경로
                .requestMatchers(
                        // 모든 요청 제외 (local dev에서만)
                        new AntPathRequestMatcher("/**")
                );
    }

    // ✅ HTTP 요청에 대한 Spring Security 웹 기반 보안 구성
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests(auth -> auth // 🔐 인가(Authorization) 설정 시작

                        // ✅ 비인증 사용자(비로그인 사용자)도 접근 가능한 경로
                        .requestMatchers(

                                new AntPathRequestMatcher("/login"),                        // 로그인 페이지
                                new AntPathRequestMatcher("/signup"),                       // 회원가입 페이지
                                new AntPathRequestMatcher("/api/user"),                     // 회원가입 처리 API
                                new AntPathRequestMatcher("/api/user/check-email")        // 이메일 중복 확인 API
                        ).permitAll() // 위 경로는 로그인 없이 접근 가능

                        // ✅ 관리자 전용 API
                        .requestMatchers(
                                new AntPathRequestMatcher("/api/admin/**")
                        ).hasAnyAuthority(
                                AccountRole.ROLE_MODERATOR.name(), // 중간 관리자
                                AccountRole.ROLE_SUPER.name()      // 최고 관리자
                        )

                        // ✅ 일반 사용자 전용 API
                        .requestMatchers(
                                new AntPathRequestMatcher("api/**") // 모든 /api/** 경로 (단, admin 경로 제외)
                        ).hasAnyAuthority(
                                AccountRole.ROLE_USER.name()
                        )

                        // ✅ 위에 명시되지 않은 나머지 모든 요청은 인증 필요
                        .anyRequest().authenticated()
                )


                // ⛔️ [선택 사항] 폼 로그인 설정 (현재 주석 처리됨 - JWT 기반 로그인 등 커스텀 로그인 처리 예상)
                /*.formLogin(formLogin -> formLogin
                                .loginPage("/login")              // 사용자 정의 로그인 페이지
                                .usernameParameter("email")       // 로그인 시 사용할 파라미터명
                                .defaultSuccessUrl("/")           // 로그인 성공 후 이동 경로
                        // .successHandler(customSuccessHandler) // (선택) 로그인 성공 후 사용자 정의 처리
                )*/



                // ⛔️ [선택 사항] 로그아웃 설정 (현재 주석 처리됨)
                /*.logout(logout -> logout
                        .logoutUrl("/logout")             // 로그아웃 요청 URL
                        .logoutSuccessUrl("/")            // 로그아웃 후 이동 경로
                        .invalidateHttpSession(true)      // 세션 무효화
                        .deleteCookies("JSESSIONID")      // JSESSIONID 쿠키 삭제
                )*/


                // ✅ CSRF 보호 비활성화 (REST API 방식에서는 보통 비활성화)
                .csrf(csrf -> csrf.disable())

                // ✅ H2 콘솔 접근 허용을 위한 X-Frame-Options 헤더 설정
                // SAMEORIGIN: 같은 출처에서 iframe으로 로딩을 허용 (H2 콘솔 등에서 필요)
                /*.headers(headers -> headers
                        .frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin)
                )*/

                .build();
    }


    // 패스워드 인코더로 사용할 빈 등록
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
