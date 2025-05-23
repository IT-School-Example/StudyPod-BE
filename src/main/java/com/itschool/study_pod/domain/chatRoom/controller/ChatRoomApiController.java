package com.itschool.study_pod.domain.chatRoom.controller;

import com.itschool.study_pod.domain.chatRoom.dto.request.ChatRoomRequest;
import com.itschool.study_pod.domain.chatRoom.dto.response.ChatRoomResponse;
import com.itschool.study_pod.domain.chatRoom.entity.ChatRoom;
import com.itschool.study_pod.domain.chatRoom.service.ChatRoomService;
import com.itschool.study_pod.global.base.crud.CrudController;
import com.itschool.study_pod.global.base.crud.CrudService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
}
