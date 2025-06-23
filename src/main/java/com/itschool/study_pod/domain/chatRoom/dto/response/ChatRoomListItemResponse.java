package com.itschool.study_pod.domain.chatRoom.dto.response;

import com.itschool.study_pod.domain.ChatParticipant.dto.response.ChatParticipantResponse;
import com.itschool.study_pod.domain.ChatParticipant.entity.ChatParticipant;
import com.itschool.study_pod.domain.chatRoom.entity.ChatRoom;
import com.itschool.study_pod.domain.user.dto.response.UserResponse;
import com.itschool.study_pod.global.enumclass.ChatRoomType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Schema
@Data
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class ChatRoomListItemResponse {

    private Long chatRoomId;

    private String name;

    // 마지막 메시지 내용
    private String lastMessage;

    // 안 읽은 메시지 수
    private Long unreadMessageCount;

    // 참여자 목록
    private List<ChatParticipantResponse> participants;

    // 마지막 메시지 전송 시간
    private LocalDateTime lastMessageTime;

    private String opponentUsername;

    private ChatRoomType chatRoomType;

    public static ChatRoomListItemResponse fromEntity(
            ChatRoom chatRoom,
            String lastMessage,
            int unreadMessageCount,
            LocalDateTime lastMessageTime,
            //List<ChatParticipantResponse> participants,
            String opponentUsername) {

        return ChatRoomListItemResponse.builder()
                .chatRoomId(chatRoom.getId())
                .chatRoomType(chatRoom.getType())
                .name(chatRoom.getName())
                .lastMessage(lastMessage)
                .unreadMessageCount((long) unreadMessageCount)
                //.participants(participants)
                .lastMessageTime(lastMessageTime)
                .opponentUsername(opponentUsername)
                .build();
    }
}

