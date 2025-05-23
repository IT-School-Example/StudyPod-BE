package com.itschool.study_pod.domain.Message.service;

import com.itschool.study_pod.domain.Message.dto.request.MessageRequest;
import com.itschool.study_pod.domain.Message.dto.response.MessageResponse;
import com.itschool.study_pod.domain.Message.entity.Message;
import com.itschool.study_pod.domain.Message.repository.MessageRepository;
import com.itschool.study_pod.domain.chatRoom.dto.request.ChatRoomRequest;
import com.itschool.study_pod.domain.chatRoom.entity.ChatRoom;
import com.itschool.study_pod.global.base.crud.CrudService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MessageService extends CrudService<MessageRequest, MessageResponse, Message> {
    private final MessageRepository messageRepository;

    @Override
    protected JpaRepository<Message, Long> getBaseRepository() {return messageRepository; }

    @Override
    protected Message toEntity(MessageRequest request) {return Message.of(request);}

}
