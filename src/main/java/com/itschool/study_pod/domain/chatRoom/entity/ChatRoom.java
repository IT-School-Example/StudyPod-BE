package com.itschool.study_pod.domain.chatRoom.entity;

import com.itschool.study_pod.domain.chatRoom.dto.request.ChatRoomRequest;
import com.itschool.study_pod.domain.chatRoom.dto.response.ChatRoomResponse;
import com.itschool.study_pod.domain.studygroup.entity.StudyGroup;
import com.itschool.study_pod.domain.user.entity.User;
import com.itschool.study_pod.global.base.BaseEntity;
import com.itschool.study_pod.global.base.crud.Convertible;
import com.itschool.study_pod.global.enumclass.ChatRoomType;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Table(name = "chat_rooms")
public class ChatRoom extends BaseEntity implements Convertible<ChatRoomRequest, ChatRoomResponse> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "chat_Rooms_id", nullable = false)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ChatRoomType type;

    // 1:1 채팅
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user1_id")
    private User user1;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user2_id")
    private User user2;

    // 그룹 채팅
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "study_group_id")
    private StudyGroup studyGroup;

    @Column(length = 255)
    private String name;

    // 요청 DTO -> Entity로 변환하는 메서드
    public static ChatRoom of(ChatRoomRequest request) { // create용
        if (request != null) {
        return ChatRoom.builder()
                .type(request.getType())
                .user1(request.getUser1())
                .user2(request.getUser2())
                .studyGroup(StudyGroup.withId(request.getStudyGroup().getId()))
                .name(request.getName())
                .build();
        }
        return null;
    }

    @Override
    public void update(ChatRoomRequest request) {
        this.type = request.getType();
        this.user1 = request.getUser1();
        this.user2 = request.getUser2();
        this.studyGroup = StudyGroup.withId(request.getStudyGroup().getId());
        this.name = request.getName();
    }

    @Override
    public ChatRoomResponse response() {
        return ChatRoomResponse.builder()
                .id(this.id)
                .type(this.type)
                .user1(this.user1.response())
                .user2(this.user2.response())
                .studyGroup(StudyGroup.withId(this.studyGroup.getId()))
                .name(this.name)
                .build();
    }

    public static ChatRoom withId(Long id) {
        return ChatRoom.builder()
                .id(id)
                .build();
    }

}
