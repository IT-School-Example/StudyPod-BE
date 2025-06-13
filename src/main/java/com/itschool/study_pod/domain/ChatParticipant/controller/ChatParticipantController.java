package com.itschool.study_pod.domain.ChatParticipant.controller;

import com.itschool.study_pod.domain.ChatParticipant.dto.request.ChatParticipantRequest;
import com.itschool.study_pod.domain.ChatParticipant.dto.response.ChatParticipantResponse;
import com.itschool.study_pod.domain.ChatParticipant.entity.ChatParticipant;
import com.itschool.study_pod.domain.ChatParticipant.service.ChatParticipantService;
import com.itschool.study_pod.domain.chatRoom.entity.ChatRoom;
import com.itschool.study_pod.domain.chatRoom.service.ChatRoomService;
import com.itschool.study_pod.domain.user.entity.User;
import com.itschool.study_pod.domain.user.service.UserService;
import com.itschool.study_pod.global.base.crud.CrudController;
import com.itschool.study_pod.global.base.crud.CrudService;
import com.itschool.study_pod.global.base.dto.Header;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@Tag(name = "채팅방 참가자", description = "채팅방 참가자 API")
@RequestMapping("/api/chat-participants")
public class ChatParticipantController extends CrudController<ChatParticipantRequest, ChatParticipantResponse, ChatParticipant> {

    private final ChatParticipantService chatParticipantService;
    private final UserService userService;
    private final ChatRoomService chatRoomService;


    @Override
    protected CrudService<ChatParticipantRequest, ChatParticipantResponse, ChatParticipant> getBaseService() {return chatParticipantService; }

    @GetMapping("/check")
    public ResponseEntity<ChatParticipantResponse> chatParticipant(
            @RequestParam Long userId,
            @RequestParam Long chatRoomId) {
        chatParticipantService.checkUserInChatRoom(userId, chatRoomId);

        ChatParticipantResponse response = ChatParticipantResponse.builder()
                .message("채팅방 멤버 인증 성공")
                .build();

        return ResponseEntity.ok(response);
    }
    @PostMapping("/chat-participants/enter")
    @Operation(summary = "입장기록", description = "입장 기록 API")
    public Header<ChatParticipant> saveEntranceRecord(@RequestBody ChatParticipantRequest request) {

        Long userId = request.getUser().getId();
        Long chatRoomId = request.getChatRoom().getId();

        User user = userService.findById(userId)
                .orElseThrow(() -> new RuntimeException("유저가 존재하지 않습니다."));
        ChatRoom chatRoom = chatRoomService.findById(chatRoomId)
                .orElseThrow(() -> new RuntimeException("채팅방이 존재하지 않습니다."));

        return chatParticipantService.recordEntranceTime(user, chatRoom);
    }
}
