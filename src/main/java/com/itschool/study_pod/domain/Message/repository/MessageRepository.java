package com.itschool.study_pod.domain.Message.repository;

import com.itschool.study_pod.domain.Message.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {

    Message findTopByChatRoomIdOrderByCreatedAtDesc(Long chatRoomId);
    List<Message> findByChatRoomId(Long chatRoomId);

    // Long countByChatRoomIdAndReceiverIdAndIsReadFalse(Long chatRoomId, Long receiverId);
//    Optional<InterestedStudy> findById(Long interestedStudyList);
//
//    List<InterestedStudy> findByName(String name);
//
//    Optional<InterestedStudy> getFirstByOrderByIdDesc();
//
//    Optional<Long> countBy();
}
