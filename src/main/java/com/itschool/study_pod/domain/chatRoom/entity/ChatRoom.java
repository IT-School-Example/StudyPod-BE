package com.itschool.study_pod.domain.chatRoom.entity;

import com.itschool.study_pod.domain.ChatParticipant.entity.ChatParticipant;
import com.itschool.study_pod.domain.ChatParticipant.service.ChatParticipantService;
import com.itschool.study_pod.domain.chatRoom.dto.request.ChatRoomRequest;
import com.itschool.study_pod.domain.chatRoom.dto.request.DirectChatRoomRequest;
import com.itschool.study_pod.domain.chatRoom.dto.request.StudyGroupChatRoomRequest;
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
    @JoinColumn(name = "study_group_id"/*, unique = true*/)
    private StudyGroup studyGroup;

    @Builder.Default
    @OneToMany(mappedBy = "chatRoom", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ChatParticipant> members = new HashSet<>();

    public void addChatParticipant(ChatParticipant member) {
        member.setChatRoom(this);
        this.members.add(member);
    }

    // 요청 DTO -> Entity로 변환하는 메서드
    public static ChatRoom of(ChatRoomRequest request) {
        if (request instanceof StudyGroupChatRoomRequest studyGroupChatRoomRequest) {
            return of(studyGroupChatRoomRequest);
        } else if (request instanceof DirectChatRoomRequest directChatRoomRequest) {
            return of(directChatRoomRequest);
        } else {
            throw new IllegalArgumentException("알 수 없는 ChatRoomRequest 타입입니다.");
        }
    }
   /* public static ChatRoom of(ChatRoomRequest request) { // create용
        ChatRoom chatRoom = ChatRoom.builder()
                .type(request.getType())
                .name(request.getName())
                .studyGroup(StudyGroup.withId(request.getStudyGroup() != null ? request.getStudyGroup().getId() : null))
                .build();

        if (request.getMemberIds() != null && !request.getMemberIds().isEmpty()) {
            request.getMemberIds().forEach(userId -> {
                ChatParticipant participant = ChatParticipant.builder()
                        .user(User.withId(userId))
                        .build();
                chatRoom.addChatParticipant(participant);
            });
        }

        return chatRoom;
    }*/
    /*@Override
    public void update(ChatRoomRequest request) {
        this.type = request.getType();
        this.name = request.getName();
        this.studyGroup = StudyGroup.withId(request.getCreatorId().getId());

        this.studyGroup = StudyGroup.withId(request.getStudyGroup().getId());
        this.name = request.getName();
    }*/

    public void update(ChatRoomRequest request) {
        if (request instanceof StudyGroupChatRoomRequest studyGroupChatRoomRequest) {
            update(studyGroupChatRoomRequest);
        } else if (request instanceof DirectChatRoomRequest directChatRoomRequest) {
            update(directChatRoomRequest);
        } else {
            throw new IllegalArgumentException("알 수 없는 ChatRoomRequest 타입입니다.");
        }
    }


    @Override
    public ChatRoomResponse response() {
//        Set<UserResponse> memberResponse = this.members.stream()
//                .map(ChatParticipant::getUser)
//                .map(User::response)
//                .collect(Collectors.toSet());

        return ChatRoomResponse.builder()
                .id(this.id)
                .type(this.type)
                .name(this.name)
                //.studyGroup(StudyGroupSummaryResponse.fromEntity(this.studyGroup))
                .studyGroup(studyGroup != null ? StudyGroupSummaryResponse.fromEntity(studyGroup) : null)
                .members(this.members.stream()
                        .map(ChatParticipant::getUser)
                        .map(User::response)
                        .collect(Collectors.toSet()))
                .build();
    }

    public static ChatRoom withId(Long id) {
        return ChatRoom.builder()
                .id(id)
                .build();
    }

}
