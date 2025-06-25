package com.itschool.study_pod.domain.Message.controller;

import com.itschool.study_pod.domain.Message.dto.request.MessageRequest;
import com.itschool.study_pod.domain.Message.dto.response.MessageResponse;
import com.itschool.study_pod.domain.Message.entity.Message;
import com.itschool.study_pod.domain.Message.service.MessageService;
import com.itschool.study_pod.domain.chatRoom.dto.request.ChatRoomRequest;
import com.itschool.study_pod.domain.chatRoom.dto.response.ChatRoomResponse;
import com.itschool.study_pod.global.base.crud.CrudController;
import com.itschool.study_pod.global.base.crud.CrudService;
import com.itschool.study_pod.global.base.dto.Header;
import com.itschool.study_pod.global.enumclass.ChatRoomType;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@Tag(name = "메시지", description = "메시지 API")
@RequestMapping("/api/Message")
public class MessageController extends CrudController<MessageRequest, MessageResponse, Message> {
    private final MessageService messageService;

    private final SimpMessagingTemplate messagingTemplate;

    @Override
    protected CrudService<MessageRequest, MessageResponse, Message> getBaseService() {return messageService; }

    @Operation(summary = "채팅 메시지 전송", description = "WebSocket을 통해 받은 메시지를 저장한 뒤, 같은 채팅방 사용자들에게 실시간으로 전달합니다.")
    @MessageMapping("/chat/send-message")
    public void sendMessage(MessageRequest messageRequest) {
        // 메시지 저장
        Header<MessageResponse> savedMessageHeader = messageService.createMessage(messageRequest);
        MessageResponse savedMessage = savedMessageHeader.getData();

        String roomId = messageRequest.getChatRoom().getId().toString();

        String destination;

        if (messageRequest.getChatRoom().getType() == ChatRoomType.DIRECT) {
            destination = "/topic/chat/direct/" + roomId;
        }else if (messageRequest.getChatRoom().getType() == ChatRoomType.GROUP) {
            destination = "/topic/chat/group/" + roomId;
        } else {
            throw new RuntimeException("알 수 없는 채팅방 입니다.");
        }

        messagingTemplate.convertAndSend(destination, savedMessage);
    }

    @Operation(summary = "채팅방 메시지 조회", description = "채팅방의 메시지를 시간순으로 조회합니다.")
    @GetMapping("/message/{chatRoomId}")
    public List<MessageResponse> getMessage(@PathVariable Long chatRoomId) {
        return messageService.findMessagesByChatRoomId(chatRoomId);
    }
}
