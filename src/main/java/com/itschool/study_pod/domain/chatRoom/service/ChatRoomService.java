package com.itschool.study_pod.domain.chatRoom.service;

import com.itschool.study_pod.domain.chatRoom.dto.request.ChatRoomRequest;
import com.itschool.study_pod.domain.chatRoom.dto.response.ChatRoomResponse;
import com.itschool.study_pod.domain.chatRoom.entity.ChatRoom;
import com.itschool.study_pod.domain.chatRoom.repository.ChatRoomRepository;
import com.itschool.study_pod.domain.studygroup.repository.StudyGroupRepository;
import com.itschool.study_pod.domain.user.repository.UserRepository;
import com.itschool.study_pod.global.base.crud.CrudService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChatRoomService extends CrudService<ChatRoomRequest, ChatRoomResponse, ChatRoom> {
    private final ChatRoomRepository chatRoomRepository;

    private final StudyGroupRepository studyGroupRepository;

    private final UserRepository userRepository;

    @Override
    protected JpaRepository<ChatRoom, Long> getBaseRepository() {return chatRoomRepository; }

    @Override
    protected ChatRoom toEntity(ChatRoomRequest request) {return ChatRoom.of(request);}

}
