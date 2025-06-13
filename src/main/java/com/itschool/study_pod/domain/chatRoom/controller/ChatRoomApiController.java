package com.itschool.study_pod.domain.chatRoom.controller;

import com.itschool.study_pod.common.AuthUtil;
import com.itschool.study_pod.domain.chatRoom.dto.request.ChatRoomRequest;
import com.itschool.study_pod.domain.chatRoom.dto.response.ChatRoomListItemResponse;
import com.itschool.study_pod.domain.chatRoom.dto.response.ChatRoomResponse;
import com.itschool.study_pod.domain.chatRoom.entity.ChatRoom;
import com.itschool.study_pod.domain.chatRoom.service.ChatRoomService;
import com.itschool.study_pod.global.base.crud.CrudController;
import com.itschool.study_pod.global.base.crud.CrudService;
import com.itschool.study_pod.global.base.dto.Header;
import com.itschool.study_pod.global.enumclass.ChatRoomType;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@Tag(name = "채팅방", description = "채팅방 API")
@RequestMapping("/api/chat-rooms")
public class ChatRoomApiController extends CrudController<ChatRoomRequest, ChatRoomResponse, ChatRoom> {

    private final ChatRoomService chatRoomService;

    @Override
    protected CrudService<ChatRoomRequest, ChatRoomResponse, ChatRoom> getBaseService() {return chatRoomService; }


    @PostMapping("/created")
    @Operation(summary = "채팅방 생성", description = "채팅방 생성 api")
    public ChatRoom createChatRoom(@PathVariable(name = "chatRoomId") ChatRoomRequest request) {
        return chatRoomService.create(request);
    }

    @GetMapping("{chatRoomId}/access-check")
    @Operation(summary = "그룹 채팅방 입장 전 권한 확인 api", description = "해당 그룹에 멤버로 승인된 회원 입장 권한 식별합니다.")
    public Header<ChatRoomResponse> chatRoomAccessService (@PathVariable(name = "chatRoomId") Long chatRoomId) {
        ChatRoom chatRoom = chatRoomService.findById(chatRoomId)
                .orElseThrow(() -> new RuntimeException("존재하지 않는 채팅방입니다."));

        // 1:1 채팅방 권한 확인
        if (chatRoom.getType() == ChatRoomType.DIRECT) {
            return chatRoomService.userChatRoom(chatRoomId);
            // 그룹 채팅방 권한 확인
        } else if (chatRoom.getType() == ChatRoomType.GROUP) {
            return chatRoomService.read(chatRoomId);
        } else {
            throw  new RuntimeException("알 수 없는 채팅방입니다.");
        }
    }
    @GetMapping("list")
    @Operation(summary = "사용자가 참여 중인 채팅방 리스트 조회", description = "현재 로그인한 사용자의 채팅방 목록 반환")
    public Header<List<ChatRoomListItemResponse>> getChatRoom() {
        Long userId = AuthUtil.getCurrentAccountId();
        return chatRoomService.getChatRoomsForUser(userId);
    }
}
