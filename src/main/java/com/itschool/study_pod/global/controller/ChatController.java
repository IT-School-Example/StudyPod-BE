package com.itschool.study_pod.global.controller;

import com.itschool.study_pod.domain.Message.dto.response.MessageResponse;
import com.itschool.study_pod.domain.Message.entity.Message;
import com.itschool.study_pod.domain.user.entity.User;
import com.itschool.study_pod.domain.user.repository.UserRepository;
import com.itschool.study_pod.global.base.account.Account;
import com.itschool.study_pod.global.enumclass.MessageType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;

import java.security.Principal;

@Slf4j
@Controller
@RequiredArgsConstructor
public class ChatController {
    // 클라이언트와 서버 간 실시간 메시지 송수신을 STOMP 프로토콜로 처리하는 핵심 역할
    private final SimpMessageSendingOperations messageSendingOperations;

    private final UserRepository userRepository;
    
    @MessageMapping("/chat/message") // 클라이언트가 /app/chat/message로 보낼 경우 매핑
    public void message(Message message, Principal principal) {

        try {

            User user = userRepository.findByEmail(principal.getName())
                    .orElseThrow(() -> new RuntimeException("해당 유저를 못 찾음"));

            message.setSender(user);

            // 메시지 없을때 로그를 찍고 실행 중단
            if (message == null) {
                log.warn("수신된 메시지가 null입니다.");
                return;
            }

            // 채팅방정보&보낸 사람정보가 없으면 로그찍고 중단
            if (message.getChatRoom() == null || message.getSender() == null) {
                log.warn("채팅방 또는 보낸 사람 정보가 없습니다. message: {}", message);
                return;
            }

            MessageResponse sendMessage = message.response();

            // 입장메시지라면
            if (MessageType.ENTER.equals(message.getMessageType())) {

                // 입장 안내 메시지 생성: "닉네임님이 입장하셨습니다."
                sendMessage = Message.builder()
                        .chatRoom(message.getChatRoom())
                        .sender(message.getSender())
                        .messageText(message.getSender().getNickname()  + "님이 입장하셨습니다.")
                        .isRead(false) // 입장 메시지는 읽음 처리 안함
                        .messageType(message.getMessageType())
                        .build().response();
            }
            // 구독자에게 메시지 전송 → 클라이언트는 /topic/chat/room/{roomId}를 구독해야 함(구독한 모든 클라이언트에게 메시지 전송)
            messageSendingOperations.convertAndSend("/topic/chat/room/" + sendMessage.getChatRoom().getId(), sendMessage);
            log.info("메시지 전송 완료: {}", sendMessage);

    } catch (Exception e) {
            log.error("채팅 메시지 처리 중 예외 발생: ", e);
        }
    }
}
