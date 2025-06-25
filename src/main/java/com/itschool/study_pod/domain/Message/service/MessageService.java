package com.itschool.study_pod.domain.Message.service;

import com.itschool.study_pod.domain.Message.dto.request.MessageRequest;
import com.itschool.study_pod.domain.Message.dto.response.MessageResponse;
import com.itschool.study_pod.domain.Message.entity.Message;
import com.itschool.study_pod.domain.Message.repository.MessageRepository;
import com.itschool.study_pod.domain.chatRoom.dto.request.ChatRoomRequest;
import com.itschool.study_pod.domain.chatRoom.dto.response.ChatRoomResponse;
import com.itschool.study_pod.domain.chatRoom.entity.ChatRoom;
import com.itschool.study_pod.domain.chatRoom.repository.ChatRoomRepository;
import com.itschool.study_pod.domain.user.entity.User;
import com.itschool.study_pod.domain.user.repository.UserRepository;
import com.itschool.study_pod.global.base.crud.CrudService;
import com.itschool.study_pod.global.base.dto.Header;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MessageService extends CrudService<MessageRequest, MessageResponse, Message> {
    private final MessageRepository messageRepository;
    private final ChatRoomRepository chatRoomRepository;
    private final UserRepository userRepository;

    @Override
    protected JpaRepository<Message, Long> getBaseRepository() {return messageRepository; }

    @Override
    protected Message toEntity(MessageRequest request) {return Message.of(request);}

    public Header<MessageResponse> createMessage(MessageRequest request) {
        // 채팅방, 사용자 조회 및 검증
        ChatRoom chatRoom = chatRoomRepository.findById(request.getChatRoom().getId())
                .orElseThrow(() -> new RuntimeException("채팅방이 존재하지 않습니다."));

        User sender = userRepository.findByNickname(request.getSender().getNickname())
                .orElseThrow(() -> new RuntimeException("해당 사용자(닉네임)가 없습니다."));

        Message message = Message.builder()
                .chatRoom(chatRoom)
                .sender(sender)
                .messageText(request.getMessageText())
                .messageType(request.getMessageType())
                .build();

        Message saved = messageRepository.save(message);

        MessageResponse response = saved.response();

        return Header.OK(response);


    }

    public List<MessageResponse> findMessagesByChatRoomId(Long chatRoomId) {
        //DB에서 chatRoomId에 해당하는 메시지 조회
        List<Message> messages = messageRepository.findByChatRoomId(chatRoomId);

        return messages.stream()
                .map(Message::response)
                .collect(Collectors.toList());
    }
    /*public Header<MessageResponse> createMessage(User user, ChatRoom chatRoom) {

        User nickname = userRepository.findById(user.getNickname())
                .orElseThrow(())
    }*/

}
