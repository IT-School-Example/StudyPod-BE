package com.itschool.study_pod.global.config;

import com.itschool.study_pod.global.config.error.CustomAccessDeniedHandler;
import com.itschool.study_pod.global.config.error.CustomAuthenticationEntryPoint;
import com.itschool.study_pod.global.enumclass.AccountRole;
import com.itschool.study_pod.global.security.jwt.TokenAuthenticationFilter;
import com.itschool.study_pod.global.security.jwt.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

import java.util.List;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableGlobalMethodSecurity(
        prePostEnabled = true,   // @PreAuthorize, @PostAuthorize 사용 가능
        securedEnabled = true,   // @Secured 사용 가능
        jsr250Enabled = true     // @RolesAllowed 사용 가능
)
public class WebSecurityConfig {

    private final TokenProvider tokenProvider;

    // 운영 프로필에서 Security FilterChain 자체를 거치지 않도록 설정하는 메서드
    @Bean
    @Profile("prod")
    public WebSecurityCustomizer prodConfigure() {
        return web -> web.ignoring()
                // 운영 환경에서만 허용할 경로
                .requestMatchers(
                        // 해당 요청은 필터링 제외 (local dev에서만)
                        new AntPathRequestMatcher("/css/**"),
                        new AntPathRequestMatcher("/img/**"),
                        new AntPathRequestMatcher("/js/**")
                );
    }

    // 운영 외 프로필에서 Security FilterChain 자체를 거치지 않도록 설정하는 메서드
    @Bean
    @Profile({"local", "dev"})
    public WebSecurityCustomizer devConfigure() {
        return web -> web.ignoring()
                // 개발 및 테스트 환경에서만 허용할 경로
                .requestMatchers(
                        // 해당 요청은 필터링 제외 (local dev에서만)
                        // new AntPathRequestMatcher("/*.html"),
                        new AntPathRequestMatcher("/css/**"),
                        new AntPathRequestMatcher("/img/**"),
                        new AntPathRequestMatcher("/js/**"),

                        // new AntPathRequestMatcher("/api/**"),
                        new AntPathRequestMatcher("/api-docs"),
                        new AntPathRequestMatcher("/api-docs/**"),
                        new AntPathRequestMatcher("/v3/api-docs/**"),
                        new AntPathRequestMatcher("/swagger*/**"),
                        new AntPathRequestMatcher("/swagger-resources/**")
                );
    }

    // ✅ HTTP 요청에 대한 Spring Security 웹 기반 보안 구성
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, HandlerMappingIntrospector introspector) throws Exception {
        MvcRequestMatcher.Builder mvc = new MvcRequestMatcher.Builder(introspector).servletPath("/");

        return http
                /*.authorizeRequests()
                .antMatchers("/ws/**", "/app/**", "/topic/**").permitAll()*/
                .authorizeHttpRequests(auth -> auth // 🔐 인가(Authorization) 설정 시작
                        .requestMatchers(new AntPathRequestMatcher("/**", HttpMethod.OPTIONS.name())).permitAll()

                        // ✅ 비인증 사용자(비로그인 사용자)도 접근 가능한 경로
                        .requestMatchers(
                                new AntPathRequestMatcher("/api/login"),
                                new AntPathRequestMatcher("/api/me"),
                                new AntPathRequestMatcher("/api/user/mailSend"),
                                new AntPathRequestMatcher("/api/user/mailCheck"),
                                new AntPathRequestMatcher("/api/user/find-pw"),
                                new AntPathRequestMatcher("/api/login"),
                                new AntPathRequestMatcher("/api/introduce/**"),
                                new AntPathRequestMatcher("/api/user/{id}/summary"),
                                new AntPathRequestMatcher("/login"),
                                new AntPathRequestMatcher("/signup"),
                                new AntPathRequestMatcher("/api/study-groups"),
                                new AntPathRequestMatcher("/api/study-groups/public/{id}"),
                                new AntPathRequestMatcher("/api/user"),
                                new AntPathRequestMatcher("/api/study-groups"),
                                new AntPathRequestMatcher("/api/user/check-email")
                        ).permitAll() // 위 경로는 로그인 없이 접근 가능

                        // ✅ 로그인된 사용자 전용 API
                        .requestMatchers(
                                new AntPathRequestMatcher("/api/**"), // 모든 /api/** 경로 (단, admin 경로 제외)
                                new AntPathRequestMatcher("/api/logout")
                                // new AntPathRequestMatcher("/logout")
                        ).hasAnyAuthority(
                                AccountRole.ROLE_USER.name(),
                                AccountRole.ROLE_ADMIN.name()
                        )

                        // ✅ 사용자 전용 페이지
                        .requestMatchers(
                                new AntPathRequestMatcher("/chat.html"),
                                new AntPathRequestMatcher("/websocket/chatting.html"),
                                new AntPathRequestMatcher("/ws-stomp/**"),
                                new AntPathRequestMatcher("/ws/**"), // 채팅
                                new AntPathRequestMatcher("/app/**"), // 채팅
                                new AntPathRequestMatcher("/topic/**") // 채팅
                        ).hasAuthority(
                                AccountRole.ROLE_USER.name()
                        )

                        // ✅ 관리자 전용 페이지
                        .requestMatchers(
                                new AntPathRequestMatcher("/"),
                                new AntPathRequestMatcher("/*.html"),
                                new AntPathRequestMatcher("/admin/**"),
                                new AntPathRequestMatcher("/notice/**"),
                                new AntPathRequestMatcher("/faq/**"),
                                new AntPathRequestMatcher("/subject-area/**"),
                                new AntPathRequestMatcher("/user/**"),
                                new AntPathRequestMatcher("/study-group/**"),
                                new AntPathRequestMatcher("/study-board/**")

                        ).hasAuthority(
                                AccountRole.ROLE_ADMIN.name()
                        )

                        // ✅ 위에 명시되지 않은 나머지 모든 요청은 인증 필요
                        .anyRequest().authenticated()
                )

                // .anonymous().disable()

                // 인증되지 않았을 경우, 로그인 페이지로 리다이렉션
                .exceptionHandling()
                .authenticationEntryPoint(new CustomAuthenticationEntryPoint()) // 401 미인증 처리
                .accessDeniedHandler(new CustomAccessDeniedHandler()) // 403 미인가 처리
                .and()

                // ⛔️ [선택 사항] 폼 로그인 설정 (JWT 기반 커스텀 로그인 처리)
                .formLogin().disable()
                .httpBasic().disable()

                // ⛔️ [선택 사항] 로그아웃 설정 (Spring security에서 제공하는 형태가 아닌 직접 api 구현 완료)
                .logout().disable()

                // ✅ CSRF 보호 비활성화 (REST API 방식에서는 보통 비활성화)
                .csrf(csrf -> csrf.disable())

                // ✅ H2 콘솔 접근 허용을 위한 X-Frame-Options 헤더 설정
                // SAMEORIGIN: 같은 출처에서 iframe으로 로딩을 허용 (H2 콘솔 등에서 필요)
                /*.headers(headers -> headers
                        .frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin)
                )*/

                // CORS 설정을 활성화
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))

                .cors()
                .and()

                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )

                .addFilterBefore(new TokenAuthenticationFilter(tokenProvider), UsernamePasswordAuthenticationFilter.class)

                .build();
    }

    // 패스워드 인코더로 사용할 빈 등록
    @Bean
    public PasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(List.of("https://studypod.click", "https://www.studypod.click", "http://localhost:3000", "https://admin.studypod.click"));
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
        config.setAllowedHeaders(List.of(
                "Authorization",
                "Content-Type",
                "X-Requested-With",
                "Accept",
                "Origin"
        ));
        config.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }
}


