package com.itschool.study_pod.domain.ChatParticipant.dto.response;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ChatParticipantSummaryResponse {
    private Long id;
    private String nickname;
}
