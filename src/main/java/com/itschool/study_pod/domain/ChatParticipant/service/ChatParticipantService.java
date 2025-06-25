package com.itschool.study_pod.domain.ChatParticipant.service;

import com.itschool.study_pod.domain.ChatParticipant.dto.request.ChatParticipantRequest;
import com.itschool.study_pod.domain.ChatParticipant.dto.response.ChatParticipantResponse;
import com.itschool.study_pod.domain.ChatParticipant.dto.response.ChatParticipantSummaryResponse;
import com.itschool.study_pod.domain.ChatParticipant.entity.ChatParticipant;
import com.itschool.study_pod.domain.ChatParticipant.repostory.ChatParticipantRepository;
import com.itschool.study_pod.domain.chatRoom.entity.ChatRoom;
import com.itschool.study_pod.domain.user.entity.User;
import com.itschool.study_pod.global.base.crud.CrudService;
import com.itschool.study_pod.global.base.dto.Header;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ChatParticipantService extends CrudService<ChatParticipantRequest, ChatParticipantResponse, ChatParticipant> {

    private final ChatParticipantRepository chatParticipantRepository;

    @Override
    protected JpaRepository<ChatParticipant, Long> getBaseRepository() {return chatParticipantRepository; }

    @Override
    protected ChatParticipant toEntity(ChatParticipantRequest request) {return ChatParticipant.of(request); }

    // 사용자가 채팅방 멤버인지
    public void checkUserInChatRoom(Long userId, Long chatRoomId) {
        boolean isParticipant = chatParticipantRepository.existsByUserIdAndChatRoomId(userId, chatRoomId);
        if (!isParticipant) {
            throw new RuntimeException("채팅방 멤버가 아닙니다.");
        }
    }

    @Transactional
    public Header<ChatParticipantSummaryResponse> recordEntranceTime(User user, ChatRoom chatRoom) {
        // 이미 참가한 사용자라면 joinedAt만 업데이트
        Optional<ChatParticipant> existingParticipant = chatParticipantRepository.findByUserAndChatRoom(user, chatRoom);

        ChatParticipant chatParticipant;
        if (existingParticipant.isPresent()) {
            ChatParticipant existing= existingParticipant.get();

            chatParticipant = ChatParticipant.builder()
                    .id(existing.getId())
                    .user(existing.getUser())
                    .chatRoom(existing.getChatRoom())
                    .joinedAt(LocalDateTime.now())
                    .build();
        } else {
            //처음 참가하는 경우
            chatParticipant = ChatParticipant.builder()
                    .user(user)
                    .chatRoom(chatRoom)
                    .joinedAt(LocalDateTime.now())
                    .build();

        }
        ChatParticipant savedParticipant = chatParticipantRepository.save(chatParticipant);

        //chatParticipant.addChatParticipant(savedParticipant);

        ChatParticipantSummaryResponse response = ChatParticipantSummaryResponse.builder()
                .id(savedParticipant.getId())
                .nickname(savedParticipant.getUser().getNickname())
                .entranceTime(savedParticipant.getJoinedAt())
                .build();
        return Header.OK(response);
    }
}
