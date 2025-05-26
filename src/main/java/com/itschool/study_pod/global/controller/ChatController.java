package com.itschool.study_pod.global.controller;

import com.itschool.study_pod.domain.Message.dto.request.MessageRequest;
import com.itschool.study_pod.domain.Message.dto.response.MessageResponse;
import com.itschool.study_pod.domain.Message.entity.Message;
import com.itschool.study_pod.domain.Message.repository.MessageRepository;
import com.itschool.study_pod.domain.chatRoom.entity.ChatRoom;
import com.itschool.study_pod.domain.chatRoom.repository.ChatRoomRepository;
import com.itschool.study_pod.domain.user.entity.User;
import com.itschool.study_pod.domain.user.repository.UserRepository;
import com.itschool.study_pod.global.enumclass.MessageType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.security.Principal;

@Slf4j
@Controller
@RequiredArgsConstructor
public class ChatController {
    // 클라이언트와 서버 간 실시간 메시지 송수신을 STOMP 프로토콜로 처리하는 핵심 역할
    private final SimpMessageSendingOperations messageSendingOperations;
    private final UserRepository userRepository;
    private final ChatRoomRepository chatRoomRepository;
    private final MessageRepository messageRepository;
    private final SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/chat/message") // 클라이언트가 /app/chat/message로 보낼 경우 매핑
    public void message(MessageRequest messageRequest, Principal principal) {

        try {
            // 메시지 없을때 로그를 찍고 실행 중단
            if (messageRequest == null) {
                log.warn("수신된 메시지가 null입니다.");
                return;
            }

            // JWT에서 사용자 이메일 가져오기
            String email = principal.getName();

            //User 조회
            User user = userRepository.findByEmail(email)
                    .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));

            // 채팅방정보&보낸 사람정보가 없으면 로그찍고 중단
            if (messageRequest.getChatRoom() == null) {
                log.warn("채팅방 또는 보낸 사람 정보가 없습니다. message: {}", messageRequest);
                return;
            }

            // 채팅방 조회
            ChatRoom chatRoom = chatRoomRepository.findById(messageRequest.getChatRoom().getId())
                    .orElseThrow(() -> new RuntimeException("채팅방을 찾을 수 없습니다."));

            // 메시지 타입 변환
            MessageType messageType = messageRequest.getMessageType();

            String messageText;
            if (MessageType.ENTER.equals(messageType)) {
                // 입장 메시지일때,
                messageText = user.getNickname() + "님이 입장하셨습니다.";
            } else {
                // 일반 메시지 일때,
                messageText = messageRequest.getMessageText();
            }

            // 엔티티 생성
            Message message = Message.builder()
                    .chatRoom(chatRoom)
                    .sender(user)
                    .messageText(messageText) // 위에서 결정한 텍스트 넣기
                    .messageType(messageType)
                    .isRead(false)
                    .build();

            // DB 저장
            Message savedMessage = messageRepository.save(message);

            MessageResponse response = message.response();

            // 전송시 (엔티티 -> response 변환
            // 채팅방 구독자들에게 실시간으로 메시지 전송
            log.info("메시지 도착: {}", message.getMessageText());
            messageSendingOperations.convertAndSend(
                    "/topic/chat/room/" + chatRoom.getId(),
                    response
            );

            log.info("메시지 전송 완료: {}", message);
        }catch (Exception e) {
            log.error("채팅 메시지 처리 중 예외 발생: ", e);
        }
    }
}
