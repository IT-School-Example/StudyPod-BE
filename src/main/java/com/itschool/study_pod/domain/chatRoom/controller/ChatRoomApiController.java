package com.itschool.study_pod.domain.chatRoom.controller;

import com.itschool.study_pod.domain.chatRoom.dto.request.ChatRoomRequest;
import com.itschool.study_pod.domain.chatRoom.dto.response.ChatRoomResponse;
import com.itschool.study_pod.domain.chatRoom.entity.ChatRoom;
import com.itschool.study_pod.domain.chatRoom.service.ChatRoomService;
import com.itschool.study_pod.global.base.crud.CrudController;
import com.itschool.study_pod.global.base.crud.CrudService;
import com.itschool.study_pod.global.base.dto.Header;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@Tag(name = "채팅방", description = "채팅방 API")
@RequestMapping("/api/chatrooms")
public class ChatRoomApiController extends CrudController<ChatRoomRequest, ChatRoomResponse, ChatRoom> {

    private final ChatRoomService chatRoomService;

    @Override
    protected CrudService<ChatRoomRequest, ChatRoomResponse, ChatRoom> getBaseService() {return chatRoomService; }

    @GetMapping("{chatRoomId}/access-check")
    @Operation(summary = "그룹 채팅방 입장 전 권한 확인 api", description = "해당 그룹에 멤버로 승인된 회원 입장 권한 식별합니다.")
    public Header<ChatRoomResponse> ChatRoomAccessService (@PathVariable(name = "chatRoomId") Long chatRoomId) {
        return chatRoomService.read(chatRoomId);
    }
}

