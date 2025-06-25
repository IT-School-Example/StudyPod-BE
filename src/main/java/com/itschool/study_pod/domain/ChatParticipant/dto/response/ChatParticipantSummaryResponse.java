package com.itschool.study_pod.domain.ChatParticipant.dto.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Builder
@Data
public class ChatParticipantSummaryResponse {

    private Long id;
    // 사용자 닉네임
    private String nickname;
    // 입장시간
    private LocalDateTime entranceTime;
}
