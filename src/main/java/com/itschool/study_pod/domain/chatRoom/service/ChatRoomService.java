package com.itschool.study_pod.domain.chatRoom.service;

import com.itschool.study_pod.common.AuthUtil;
import com.itschool.study_pod.domain.chatRoom.dto.request.ChatRoomRequest;
import com.itschool.study_pod.domain.chatRoom.dto.response.ChatRoomResponse;
import com.itschool.study_pod.domain.chatRoom.entity.ChatRoom;
import com.itschool.study_pod.domain.chatRoom.repository.ChatRoomRepository;
import com.itschool.study_pod.domain.enrollment.repository.EnrollmentRepository;
import com.itschool.study_pod.domain.studygroup.repository.StudyGroupRepository;
import com.itschool.study_pod.domain.user.repository.UserRepository;
import com.itschool.study_pod.global.base.crud.CrudService;
import com.itschool.study_pod.global.base.dto.Header;
import com.itschool.study_pod.global.enumclass.EnrollmentStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChatRoomService extends CrudService<ChatRoomRequest, ChatRoomResponse, ChatRoom> {
    private final ChatRoomRepository chatRoomRepository;

    private final StudyGroupRepository studyGroupRepository;

    private final UserRepository userRepository;

    private final EnrollmentRepository enrollmentRepository;

    @Override
    protected JpaRepository<ChatRoom, Long> getBaseRepository() {return chatRoomRepository; }

    @Override
    protected ChatRoom toEntity(ChatRoomRequest request) {return ChatRoom.of(request);}

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

}
