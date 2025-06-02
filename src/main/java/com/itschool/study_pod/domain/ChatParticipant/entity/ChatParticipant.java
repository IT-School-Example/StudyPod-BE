package com.itschool.study_pod.domain.ChatParticipant.entity;

import com.itschool.study_pod.domain.ChatParticipant.dto.request.ChatParticipantRequest;
import com.itschool.study_pod.domain.ChatParticipant.dto.response.ChatParticipantResponse;
import com.itschool.study_pod.domain.chatRoom.dto.response.ChatRoomResponse;
import com.itschool.study_pod.domain.chatRoom.entity.ChatRoom;
import com.itschool.study_pod.domain.user.dto.response.UserResponse;
import com.itschool.study_pod.domain.user.entity.User;
import com.itschool.study_pod.global.base.BaseEntity;
import com.itschool.study_pod.global.base.crud.Convertible;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Table(name = "chat_participants")
public class ChatParticipant extends BaseEntity implements Convertible<ChatParticipantRequest, ChatParticipantResponse> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "chat_participant_id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chat_room_id", nullable = false)
    private ChatRoom chatRoom;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    // 참가한 시간
    private LocalDateTime joinedAt;

    public static ChatParticipant of(ChatParticipantRequest request) {
        return ChatParticipant.builder()
                .chatRoom(ChatRoom.withId(request.getChatRoom().getId()))
                .user(User.withId(request.getUser().getId()))
                .joinedAt(request.getJoinedAt())
                .build();
    }

    @Override
    public void update(ChatParticipantRequest request) {
        this.chatRoom = ChatRoom.withId(request.getChatRoom().getId());
        this.user = User.withId(request.getUser().getId());
        this.joinedAt = request.getJoinedAt();
    }

    @Override
    public ChatParticipantResponse response() {
        return ChatParticipantResponse.builder()
                .id(this.id)
                .chatRoom(ChatRoomResponse.withId(this.chatRoom.getId()))
                .user(UserResponse.withId(this.user.getId()))
                .joinedAt(this.joinedAt)
                .build();
    }

    public static ChatParticipant withId(Long id) {
        return ChatParticipant.builder()
                .id(id)
                .build();
    }
}
