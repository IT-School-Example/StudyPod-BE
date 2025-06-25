package com.itschool.study_pod.domain.chatRoom.repository;

import com.itschool.study_pod.domain.chatRoom.entity.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {

    @Query("SELECT DISTINCT c FROM ChatRoom c JOIN FETCH c.members m JOIN FETCH m.user WHERE m.user.id = :userId")
    List<ChatRoom> findDistinctByMembersUserIdWithUser(@Param("userId") Long userId);

    @Query("SELECT c FROM ChatRoom c JOIN FETCH c.members WHERE c.id = :id")
    Optional<ChatRoom> findWithMembersById(@Param("id") Long id);
//    Optional<InterestedStudy> findById(Long interestedStudyList);
//
//    List<InterestedStudy> findByName(String name);
//
//    Optional<InterestedStudy> getFirstByOrderByIdDesc();
//
//    Optional<Long> countBy();
}
