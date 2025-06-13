package com.itschool.study_pod.domain.Message.entity;

import com.itschool.study_pod.domain.Message.dto.request.MessageRequest;
import com.itschool.study_pod.domain.Message.dto.response.MessageResponse;
import com.itschool.study_pod.domain.chatRoom.dto.response.ChatRoomResponse;
import com.itschool.study_pod.domain.chatRoom.entity.ChatRoom;
import com.itschool.study_pod.domain.user.dto.response.UserResponse;
import com.itschool.study_pod.domain.user.entity.User;
import com.itschool.study_pod.global.base.BaseEntity;
import com.itschool.study_pod.global.base.account.Account;
import com.itschool.study_pod.global.base.crud.Convertible;
import com.itschool.study_pod.global.enumclass.MessageType;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Table(name = "message")
public class Message extends BaseEntity implements Convertible<MessageRequest, MessageResponse> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "message_id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chat_room_id", nullable = false)
    private ChatRoom chatRoom;

    // 메시지를 보낸 사용자
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sender_id", nullable = false)
    private User sender;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "receiver_id", nullable = false)
    private User receiver;

    // 메시지 내용
    @Column(name = "message_text", nullable = false)
    private String messageText;

    // 메시지 읽었는지 여부
    @Column(name = "is_read", nullable = false)
    private boolean isRead;

    @Enumerated(EnumType.STRING)
    @Column(name = "message_type", nullable = false)
    private MessageType messageType;

    // 요청 DTO -> Entity로 변환하는 메서드
    public static Message of(MessageRequest request) { //create용
        return Message.builder()
                .chatRoom(ChatRoom.withId(request.getChatRoom().getId()))
                .sender(User.of(request.getSender()))
                .receiver(User.of(request.getReceiver()))
                .messageText(request.getMessageText())
                .isRead(false)
                .messageType(request.getMessageType())
                .build();
    }

    @Override
    public void update(MessageRequest request) {
        this.chatRoom = ChatRoom.withId(request.getChatRoom().getId());
        this.sender = User.of(request.getSender());
        this.receiver = User.of(request.getReceiver());
        this.messageText = request.getMessageText();
        this.isRead = request.isRead();
        this.messageType = request.getMessageType();
    }

    @Override
    public MessageResponse response() {
        return MessageResponse.builder()
                .id(this.id)
                .chatRoom(ChatRoomResponse.withId(this.chatRoom.getId()))
                .sender(this.sender.response())
                .receiver(this.receiver.response())
                .messageText(this.messageText)
                .isRead(this.isRead)
                .messageType(this.messageType)
                .createdBy(this.createdBy)
                .createdAt(this.createdAt)
                .updatedBy(this.updatedBy)
                .updatedAt(this.updatedAt)
                .isDeleted(this.isDeleted)
                .build();
    }
    

    public static Message withId(Long id) {
        return Message.builder()
                .id(id)
                .build();
    }

    public void setSender(User sender) {
        this.sender = sender;
    }
}


