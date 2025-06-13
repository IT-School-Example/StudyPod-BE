package com.itschool.study_pod.domain.chatRoom.entity;

import com.itschool.study_pod.domain.ChatParticipant.entity.ChatParticipant;
import com.itschool.study_pod.domain.chatRoom.dto.request.ChatRoomRequest;
import com.itschool.study_pod.domain.chatRoom.dto.response.ChatRoomResponse;
import com.itschool.study_pod.domain.studygroup.dto.response.StudyGroupSummaryResponse;
import com.itschool.study_pod.domain.studygroup.entity.StudyGroup;
import com.itschool.study_pod.domain.user.dto.response.UserResponse;
import com.itschool.study_pod.domain.user.entity.User;
import com.itschool.study_pod.global.base.BaseEntity;
import com.itschool.study_pod.global.base.crud.Convertible;
import com.itschool.study_pod.global.enumclass.ChatRoomType;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

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

    // 채팅방 이름
    @Column(length = 255)
    private String name;

    // 그룹 채팅
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "study_group_id", unique = true)
    private StudyGroup studyGroup;

    @OneToMany(mappedBy = "chatRoom", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ChatParticipant> members = new HashSet<>();

    // 요청 DTO -> Entity로 변환하는 메서드
    public static ChatRoom of(ChatRoomRequest request) { // create용
        ChatRoom chatRoom = ChatRoom.builder()
                .type(request.getType())
                .name(request.getName())
                .studyGroup(StudyGroup.withId(request.getStudyGroup() != null ? request.getStudyGroup().getId() : null))
                .build();

        if (request.getMemberIds() != null && !request.getMemberIds().isEmpty()) {
            Set<ChatParticipant> participants = request.getMemberIds().stream()
                    .map(userId -> ChatParticipant.builder()
                            .user(User.withId(userId))
                            .chatRoom(chatRoom)
                            .build())
                    .collect(Collectors.toSet());
        }

        return chatRoom;
    }

    @Override
    public void update(ChatRoomRequest request) {
        this.type = request.getType();

        this.studyGroup = StudyGroup.withId(request.getStudyGroup().getId());
        this.name = request.getName();
    }

    @Override
    public ChatRoomResponse response() {
        return ChatRoomResponse.builder()
                .id(this.id)
                .type(this.type)
                .name(this.name)
                .studyGroup(StudyGroupSummaryResponse.fromEntity(this.studyGroup))
                .build();
    }

    public static ChatRoom withId(Long id) {
        return ChatRoom.builder()
                .id(id)
                .build();
    }

}
