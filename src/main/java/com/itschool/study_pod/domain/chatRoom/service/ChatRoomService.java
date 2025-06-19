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

    // 승인된 멤버인지 체크하는 메서드
    private boolean isApprovedMember(Long studyGroupId, Long userId) {
        return enrollmentRepository.existsByStudyGroupIdAndUserIdAndStatus(
                studyGroupId, userId, EnrollmentStatus.APPROVED);
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
    private void chechUserIsMemberOfStudyGroup(Long studyGroupId, Long userId) {
        if (!isApprovedMember(studyGroupId, userId)) {
            throw new RuntimeException("해당 그룹에 대한 접근 권한이 없습니다.");
        }
    }

    // 채팅방 생성 (creator = 채팅방 생성자)
    public ChatRoom create(ChatRoomRequest request) {

        ChatRoomType type = request.getType();

        User creator = userRepository.findById(request.getCreatorId())
                .orElseThrow(() -> new EntityNotFoundException("채팅방 생성자가 존재하지 않습니다."));


        ChatRoom chatRoom;

        if (type == ChatRoomType.DIRECT) {
            chatRoom = createDirectChatRoom(request, type);

        }
        else if (type == ChatRoomType.GROUP) {
            chatRoom = createGroupChatRoom(request, type, creator);

        }
        else {
            throw new RuntimeException("유효하지 않은 채팅방 타입입니다.");
        }

        return chatRoomRepository.save(chatRoom);
    }

    // 그룹 채팅방 생성 (승인된 스터디 멤버 자동 추가)
    private ChatRoom createGroupChatRoom(ChatRoomRequest request, ChatRoomType type, User creator) {
        //ChatRoom chatRoom;
        // 그룹 채팅
        // 요청에 스터디 그룹 정보나 ID가 없으면 예외발생
        if (request.getStudyGroup() == null || request.getStudyGroup().getId() == null) {
            throw new RuntimeException("그룹채팅에는 스터디 그룹 정보가 필요합니다.");
        }

        // 클라이언트가 보낸 스터디그룹 ID로 DB에서 실제 스터디 그룹 정보 조회(없으면 예외 발생)
        StudyGroup studyGroup = studyGroupRepository.findById(request.getStudyGroup().getId())
                .orElseThrow(() -> new EntityNotFoundException("스터디그룹이 존재하지 않습니다."));

        // 리더만 생성 가능
        if (!studyGroup.getLeader().getId().equals(creator.getId())) {
            throw new RuntimeException("스터디그룹의 리더만 생성이 가능합니다.");
        }

        // 채팅방 생성
        ChatRoom chatRoom = ChatRoom.builder()
                .type(type)
                .name(request.getName())
                .studyGroup(studyGroup)
                .build();

        // 멤버 ID 리스트를 빈 리스트로 시작
        List<User> approvedMembers = getApprovedMembers(studyGroup.getId());
        approvedMembers.forEach(user -> {
            chatRoom.addChatParticipant(ChatParticipant.builder()
                    .chatRoom(chatRoom)
                    .user(user)
                    .joinedAt(LocalDateTime.now())
                    .build());
        });
        return chatRoom;
    }

    private ChatRoom createDirectChatRoom(ChatRoomRequest request, ChatRoomType type) {
        // 1:1 채팅(방 생성)
        if (request.getMemberIds() == null || request.getMemberIds().size() != 2) {
            throw new RuntimeException("1:1 채팅은 2명의 멤버만 들어갈 수 있습니다.");
        }

        ChatRoom chatRoom = ChatRoom.builder()
                .type(type)
                .name(request.getName())
                .build();

        // 멤버 등록
        for (Long userId : request.getMemberIds()) {
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다." + userId));

            chatRoom.getMembers().add(ChatParticipant.builder()
                    .user(user)
                    .chatRoom(chatRoom)
                    .build());

        }
        return chatRoom;
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


        chechUserIsMemberOfStudyGroup(studyGroupId, userId);

        // 입장메시지 발송
        return super.read(chatRoomId);
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
