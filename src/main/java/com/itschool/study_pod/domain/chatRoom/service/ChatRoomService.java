package com.itschool.study_pod.domain.chatRoom.service;

import com.itschool.study_pod.common.AuthUtil;
import com.itschool.study_pod.domain.ChatParticipant.entity.ChatParticipant;
import com.itschool.study_pod.domain.ChatParticipant.repostory.ChatParticipantRepository;
import com.itschool.study_pod.domain.ChatParticipant.service.ChatParticipantService;
import com.itschool.study_pod.domain.Message.entity.Message;
import com.itschool.study_pod.domain.Message.repository.MessageRepository;
import com.itschool.study_pod.domain.chatRoom.dto.request.ChatRoomRequest;
import com.itschool.study_pod.domain.chatRoom.dto.request.DirectChatRoomRequest;
import com.itschool.study_pod.domain.chatRoom.dto.request.StudyGroupChatRoomRequest;
import com.itschool.study_pod.domain.chatRoom.dto.response.ChatRoomListItemResponse;
import com.itschool.study_pod.domain.chatRoom.dto.response.ChatRoomResponse;
import com.itschool.study_pod.domain.chatRoom.entity.ChatRoom;
import com.itschool.study_pod.domain.chatRoom.repository.ChatRoomRepository;
import com.itschool.study_pod.domain.enrollment.repository.EnrollmentRepository;
import com.itschool.study_pod.domain.studygroup.entity.StudyGroup;
import com.itschool.study_pod.domain.studygroup.repository.StudyGroupRepository;
import com.itschool.study_pod.domain.user.dto.response.UserResponse;
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
import java.util.Set;
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

    private final ChatParticipantRepository chatParticipantRepository;

    @Override
    protected JpaRepository<ChatRoom, Long> getBaseRepository() {return chatRoomRepository; }

    @Override
    protected ChatRoom toEntity(ChatRoomRequest request) {return ChatRoom.of(request);}

    // 승인된 멤버인지 체크하는 메서드
    private boolean isApprovedMember(Long studyGroupId, Long userId) {
        return enrollmentRepository.existsByStudyGroupIdAndUserIdAndStatus(
                studyGroupId, userId, EnrollmentStatus.APPROVED);
    }

    // 채팅방 참여자 리스트
    private Set<UserResponse> getChatRoomMembers(ChatRoom chatRoom) {
        return chatRoom.getMembers().stream()
                .map(ChatParticipant::getUser)
                .map(User::response)
                .collect(Collectors.toSet());
    }

    // 스터디 그룹 내 승인된 멤버 리스트 조회
    private List<User> getApprovedMembers(Long studyGroupId) {
        return enrollmentRepository.findAllByStudyGroupIdAndStatus(studyGroupId
                , EnrollmentStatus.APPROVED)
                .stream()
                .map(enrollment -> enrollment.getUser())
                .collect(Collectors.toList());
    }

    // 권한 체크 메서드
    private void checkUserIsMemberOfStudyGroup(Long studyGroupId, Long userId) {
        if (!isApprovedMember(studyGroupId, userId)) {
            throw new RuntimeException("해당 그룹에 대한 접근 권한이 없습니다.");
        }
    }

    // 그룹 채팅방 생성
    public Header<ChatRoomResponse> createStudyGroupChatRoom(Header<StudyGroupChatRoomRequest> request) {
        StudyGroupChatRoomRequest data = request.getData();

        User creator = userRepository.findById(data.getCreatorId())
                .orElseThrow(() -> new RuntimeException("채팅방 생성자가 존재하지 않습니다."));

        StudyGroup studyGroup = studyGroupRepository.findById(data.getStudyGroup().getId())
                .orElseThrow(() -> new EntityNotFoundException("스터디 그룹이 존재하지 않습니다."));

        if (!studyGroup.getLeader().getId().equals(creator.getId())) {
            throw new RuntimeException("스터디그룹의 리더만 생성이 가능합니다.");
        }

        ChatRoom chatRoom = ChatRoom.builder()
                .type(ChatRoomType.GROUP)
                .name(data.getName())
                .studyGroup(studyGroup)
                .build();

        // 리더(creator) 추가
        ChatParticipant leaderParticipant = ChatParticipant.builder()
                .chatRoom(chatRoom)
                .user(creator)
                .joinedAt(LocalDateTime.now())
                .build();
        chatRoom.addChatParticipant(leaderParticipant);

        // 승인된 멤버들 추가(리더 제외)
        List<User> approvedMembers = getApprovedMembers(studyGroup.getId());
        for (User user : approvedMembers) {
            if (!user.getId().equals(creator.getId())) {
                ChatParticipant participant = ChatParticipant.builder()
                        .chatRoom(chatRoom)
                        .user(user)
                        .joinedAt(LocalDateTime.now())
                        .build();
                chatRoom.addChatParticipant(participant);
            }
        }

        // ChatRoom 저장
        ChatRoom savedChatRoom = chatRoomRepository.save(chatRoom);


        ChatRoomResponse response = savedChatRoom.response();
        response.setMembers(getChatRoomMembers(savedChatRoom));
        return Header.OK(response);
    }

    // 1:1 채팅방 생성
    public Header<ChatRoomResponse> createDirectChatRoom(Header<DirectChatRoomRequest> request) {
        DirectChatRoomRequest data = request.getData();

        if (data.getMemberIds() == null || data.getMemberIds().size() != 2) {
            throw new RuntimeException("1:1 채팅은 2명의 멤버만 들어갈 수 있습니다.");
        }

        ChatRoom chatRoom = ChatRoom.builder()
                .type(ChatRoomType.DIRECT)
                .name(data.getName())
                .build();
        ChatRoom savedChatRoom = chatRoomRepository.save(chatRoom);

        // 참여자 저장
        for (Long userId : data.getMemberIds()) {
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다. ID: " + userId));

            ChatParticipant participant = ChatParticipant.builder()
                    .chatRoom(savedChatRoom)
                    .user(user)
                    .joinedAt(LocalDateTime.now())
                    .build();

            chatParticipantRepository.save(participant);
        }

        ChatRoomResponse response = savedChatRoom.response();
        response.setMembers(getChatRoomMembers(savedChatRoom));
        return Header.OK(response);
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


        checkUserIsMemberOfStudyGroup(studyGroupId, userId);

        ChatRoomResponse response = chatRoom.response();
        response.setMembers(getChatRoomMembers(chatRoom));

        /*// 입장메시지 발송
        return super.read(chatRoomId);*/

        return Header.OK(response);
    }

    public Optional<ChatRoom> findById(Long id) {
        return chatRoomRepository.findById(id);
    }

    // 1:1 채팅방(참여자 권한 체크)
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
            // Long unreadCount = messageRepository.countByChatRoomIdAndReceiverIdAndIsReadFalse(chatRoom.getId(), userId);

            // 상대방 닉네임 찾기
            String opponentUsername = chatRoom.getMembers().stream()
                    .map(ChatParticipant::getUser)
                    .filter(user -> !user.getId().equals(userId))
                    .map(User::getNickname)
                    .findFirst()
                    .orElse("알 수 없음");

            return ChatRoomListItemResponse.builder()
                    .chatRoomId(chatRoom.getId())
                    .chatRoomType(chatRoom.getType())
                    .name(chatRoom.getName())
                    .lastMessage(lastMessage != null ? lastMessage.getMessageText() : "")
                    .lastMessageTime(lastMessage != null ? lastMessage.getCreatedAt() : null)
                    // .unreadMessageCount(unreadCount)
                    .opponentUsername(opponentUsername)
                    .build();

        }).collect(Collectors.toList()));

    }

}
