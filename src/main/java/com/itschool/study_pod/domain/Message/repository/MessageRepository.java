package com.itschool.study_pod.domain.Message.repository;

import com.itschool.study_pod.domain.Message.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<Message, Long> {

    /*Message findTopByChatRoomIdOrderByCreatedAtDesc(Long chatRoomId);

    Long countByChatRoomIdAndReceiverIdAndIsReadFalse(Long chatRoomId, Long receiverId);*/
//    Optional<InterestedStudy> findById(Long interestedStudyList);
//
//    List<InterestedStudy> findByName(String name);
//
//    Optional<InterestedStudy> getFirstByOrderByIdDesc();
//
//    Optional<Long> countBy();
}
