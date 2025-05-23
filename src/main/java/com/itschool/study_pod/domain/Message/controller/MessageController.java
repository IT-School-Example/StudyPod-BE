package com.itschool.study_pod.domain.Message.controller;

import com.itschool.study_pod.domain.Message.dto.request.MessageRequest;
import com.itschool.study_pod.domain.Message.dto.response.MessageResponse;
import com.itschool.study_pod.domain.Message.entity.Message;
import com.itschool.study_pod.domain.Message.service.MessageService;
import com.itschool.study_pod.domain.chatRoom.dto.request.ChatRoomRequest;
import com.itschool.study_pod.domain.chatRoom.dto.response.ChatRoomResponse;
import com.itschool.study_pod.global.base.crud.CrudController;
import com.itschool.study_pod.global.base.crud.CrudService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@Tag(name = "메시지", description = "메시지 API")
@RequestMapping("/api/Message")
public class MessageController extends CrudController<MessageRequest, MessageResponse, Message> {
    private final MessageService messageService;

    @Override
    protected CrudService<MessageRequest, MessageResponse, Message> getBaseService() {return messageService; }
}
