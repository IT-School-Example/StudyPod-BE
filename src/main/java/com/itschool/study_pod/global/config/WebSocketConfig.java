package com.itschool.study_pod.global.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.*;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    // 메시지브로커를 등록하는 코드
    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        // 그룹 구독용 /topic, 개인 메시지용 /queue, /user 경로 추가
        // 보충 설명 : 큐만으로는 누구에게 보낼지 구분할수 x -> /user prifix 설정이 꼭 필요
        registry.enableSimpleBroker("/topic","/queue","/user");
        // 개인 메시지 접두어
        registry.setUserDestinationPrefix("/user");
        // 도착경로에 대한 prifix를 설정
        registry.setApplicationDestinationPrefixes("/app");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // "/ws" 경로로 웹소켓 연결 엔드포인트 등록 (웹소켓 연결을 허용한다)
        registry.addEndpoint("/ws")
                // 허용할 도메인 설정
                .setAllowedOrigins("http://localhost:8080")
                .setHandshakeHandler(new DefaultHandshakeHandler())
                // 웹소켓이 지원되지 않는 환경을 위해 SockJS 폴백 기능 활성화
                .withSockJS();
    }
}
