package com.itschool.study_pod.domain.chatRoom.service;

import com.itschool.study_pod.common.AuthUtil;
import com.itschool.study_pod.domain.ChatParticipant.entity.ChatParticipant;
import com.itschool.study_pod.domain.Message.entity.Message;
import com.itschool.study_pod.domain.Message.repository.MessageRepository;
import com.itschool.study_pod.domain.chatRoom.dto.request.ChatRoomRequest;
import com.itschool.study_pod.domain.chatRoom.dto.response.ChatRoomListItemResponse;
import com.itschool.study_pod.domain.chatRoom.dto.response.ChatRoomResponse;
import com.itschool.study_pod.domain.chatRoom.entity.ChatRoom;
import com.itschool.study_pod.domain.chatRoom.repository.ChatRoomRepository;
import com.itschool.study_pod.domain.enrollment.repository.EnrollmentRepository;
import com.itschool.study_pod.domain.studygroup.entity.StudyGroup;
import com.itschool.study_pod.domain.studygroup.repository.StudyGroupRepository;
import com.itschool.study_pod.domain.user.entity.User;
import com.itschool.study_pod.domain.user.repository.UserRepository;
import com.itschool.study_pod.global.base.crud.CrudService;
import com.itschool.study_pod.global.base.dto.Header;
import com.itschool.study_pod.global.enumclass.ChatRoomType;
import com.itschool.study_pod.global.enumclass.EnrollmentStatus;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class ChatRoomService extends CrudService<ChatRoomRequest, ChatRoomResponse, ChatRoom> {
    private final ChatRoomRepository chatRoomRepository;

    private final StudyGroupRepository studyGroupRepository;

    private final UserRepository userRepository;

    private final EnrollmentRepository enrollmentRepository;

    private final MessageRepository messageRepository;

    @Override
    protected JpaRepository<ChatRoom, Long> getBaseRepository() {return chatRoomRepository; }

    @Override
    protected ChatRoom toEntity(ChatRoomRequest request) {return ChatRoom.of(request);}

    // 채팅방 생성 (creator = 채팅방 생성자)
    public ChatRoom create(ChatRoomRequest request) {

        ChatRoomType type = request.getType();

        User creator = userRepository.findById(request.getCreatorId())
                .orElseThrow(() -> new EntityNotFoundException("채팅방 생성자가 존재하지 않습니다."));


       /* // 멤버 Id가 없으면
        if (request.getMemberIds() == null || request.getMemberIds().isEmpty()) {
            throw new RuntimeException("채팅방에 멤버가 없습니다.");
        }

        for (Long userId : request.getMemberIds()) {
            if (userId == null || userId == 0) {
                throw new RuntimeException("유효하지 않은 멤버 ID가 포함되어 있습니다. userId:" + userId + "," + request.getMemberIds());
            }
        }*/

        ChatRoom chatRoom;

        if (type == ChatRoomType.DIRECT) {
            // 1:1 채팅
            if (request.getMemberIds() == null || request.getMemberIds().size() != 2) {
                throw new RuntimeException("1:1 채팅은 2명의 멤버만 들어갈 수 있습니다.");
            }

            chatRoom = ChatRoom.builder()
                    .type(type)
                    .name(request.getName())
                    .build();

            for (Long userId : request.getMemberIds()) {
                User user = userRepository.findById(userId)
                        .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다." + userId));

                chatRoom.getMembers().add(ChatParticipant.builder()
                        .user(user)
                        .chatRoom(chatRoom)
                        .build());
                /*ChatParticipant chatParticipant = ChatParticipant.builder()
                        .user(user)
                        .chatRoom(chatRoom)
                        .build();
                chatRoom.getMembers().add(chatParticipant);*/
            }

        } else if (type == ChatRoomType.GROUP) {
            // 그룹 채팅
            if (request.getStudyGroup() == null || request.getStudyGroup().getId() == null) {
                throw new RuntimeException("그룹채팅에는 스터디 그룹 정보가 필요합니다.");
            }

            StudyGroup studyGroup = studyGroupRepository.findById(request.getStudyGroup().getId())
                    .orElseThrow(() -> new EntityNotFoundException("스터디그룹이 존재하지 않습니다."));

            chatRoom = ChatRoom.builder()
                    .type(type)
                    .name(request.getName())
                    .studyGroup(studyGroup)
                    .build();

            // 리더만 생성 가능
            if (!studyGroup.getLeader().getId().equals(creator.getId())) {
                throw new RuntimeException("스터디그룹의 리더만 생성이 가능합니다.");
            }

            List<Long> memberIds = List.of();
            // memberIds가 비었을 경우 승인된 멤버 자동 조회
            if (request.getMemberIds() == null || request.getMemberIds().isEmpty()) {
                enrollmentRepository.findAllByStudyGroupIdAndStatus(
                        studyGroup.getId(), EnrollmentStatus.APPROVED
                ).stream()
                        .forEach(enrollment -> {
                            boolean isApproved = enrollmentRepository.existsByStudyGroupIdAndUserIdAndStatus(
                                    studyGroup.getId(),
                                    enrollment.getUser().getId(),
                                    EnrollmentStatus.APPROVED);

                            if (!isApproved) {
                                // throw new RuntimeException("멤버 중에 승인되지 않은 사용자가 있습니다. userId:" + enrollment.getUser().getId());
                                return;
                            }

                            ChatParticipant chatParticipant = ChatParticipant.builder()
                                            .chatRoom(chatRoom)
                                            .user(enrollment.getUser())
                                            .joinedAt(LocalDateTime.now())
                                            .build();

                            chatRoom.addChatParticipant(chatParticipant);
                        });

            } else {
                memberIds = request.getMemberIds().stream()
                        .filter(id -> id != 0L)
                        .collect(Collectors.toList());

                // chatRoom.getMembers();
            }

            // 멤버 승인 여부 확인
            /*for (Long userId : memberIds) {
                boolean isApproved = enrollmentRepository.existsByStudyGroupIdAndUserIdAndStatus(
                        studyGroup.getId(),
                        userId,
                        EnrollmentStatus.APPROVED
                );
                if (!isApproved) {
                    throw new RuntimeException("멤버 중에 승인되지 않은 사용자가 있습니다. userId:" + userId);
                }
            }*/
            /*int currentMemberCount = enrollmentRepository.existsByStudyGroupIdAndUserIdAndStatus(studyGroup.getId(), EnrollmentStatus.APPROVED);
            int maxMember = studyGroup.getMaxMember();

            if (currentMemberCount > maxMember) {
                throw new RuntimeException("그룹 최대 인원 수를 초과했습니다.");
            }*/



            for (Long userId : memberIds) {
                User user = userRepository.findById(userId)
                        .orElseThrow(() -> new EntityNotFoundException("사용자를 찾을 수 없습니다."));

                chatRoom.getMembers().add(ChatParticipant.builder()
                        .user(user)
                        .chatRoom(chatRoom)
                        .build());
            }

            // 리더 자동 추가 (만약 멤버 목록에리더가 없으면)
            User leader = studyGroup.getLeader();
            if (!memberIds.contains(leader.getId())) {
                chatRoom.getMembers().add(ChatParticipant.builder()
                        .user(leader)
                        .chatRoom(chatRoom)
                        .build());
            }

        } else {
            throw new RuntimeException("유효하지 않은 채팅방 타입입니다.");
        }

        return chatRoomRepository.save(chatRoom);
    }


    // 스터디 그룹 채팅방(해당 스터디그룹 회원만)
    @Override
    public Header<ChatRoomResponse> read(Long chatRoomId) {
        // 채팅방 정보조회
        ChatRoom chatRoom = chatRoomRepository.findById(chatRoomId)
                .orElseThrow(() -> new RuntimeException("존재하지 않는 채팅방입니다."));

        // 채팅방에 속한 스터디 그룹
        Long studyGroupId = chatRoom.getStudyGroup().getId();

        Long userId = AuthUtil.getCurrentAccountId();

        // 해당 유저가 스터디 그룹의 멤버인지
        boolean isMember = enrollmentRepository
                .existsByStudyGroupIdAndUserIdAndStatus(studyGroupId, userId, EnrollmentStatus.APPROVED);
        // 멤버가 아닐 경우
        if (!isMember) {
            throw new RuntimeException("해당 그룹에 대한 접근 권한이 없습니다.");
        }

        // 입장메시지 발송

        return super.read(chatRoomId);
    }

    public Optional<ChatRoom> findById(Long id) {
        return chatRoomRepository.findById(id);
    }

    // 1:1 채팅방
    public Header<ChatRoomResponse> userChatRoom(Long chatRoomId) {
        ChatRoom chatRoom = chatRoomRepository.findById(chatRoomId)
                .orElseThrow(() -> new RuntimeException("존재하지 않는 채팅방입니다."));

        Long userId = AuthUtil.getCurrentAccountId();

        // 1:1 채팅방 참여자 권한 검사
        boolean isParticipant = chatRoom.getMembers().stream()
                .anyMatch(member -> member.getUser() != null && member.getUser().getId().equals(userId));

        if (!isParticipant) {
            throw  new RuntimeException("해당 채팅방에 대한 접근 권한이 없습니다.");
        }

        return super.read(chatRoomId);
    }

    // 사용자가 참여 중인 채팅방 리스트 조회
    public Header<List<ChatRoomListItemResponse>> getChatRoomsForUser(Long userId) {
        List<ChatRoom> chatRooms = chatRoomRepository.findDistinctByMembersUserId(userId);

        return Header.OK(chatRooms.stream().map(chatRoom -> {
            // 마지막 메시지 조회
            Message lastMessage = messageRepository.findTopByChatRoomIdOrderByCreatedAtDesc(chatRoom.getId());

            // 안 읽은 메시지 수 조회
            Long unreadCount = messageRepository.countByChatRoomIdAndReceiverIdAndIsReadFalse(chatRoom.getId(), userId);

            // 상대방 닉네임 찾기
            String opponentUsername = chatRoom.getMembers().stream()
                    .map(ChatParticipant::getUser)
                    .filter(user -> !user.getId().equals(userId))
                    .map(User::getNickname)
                    .findFirst()
                    .orElse("알 수 없음");

            return ChatRoomListItemResponse.builder()
                    .chatRoomId(chatRoom.getId())
                    .name(chatRoom.getName())
                    .lastMessage(lastMessage != null ? lastMessage.getMessageText() : "")
                    .lastMessageTime(lastMessage != null ? lastMessage.getCreatedAt() : null)
                    .unreadMessageCount(unreadCount)
                    .opponentUsername(opponentUsername)
                    .build();

        }).collect(Collectors.toList()));

    }

}
