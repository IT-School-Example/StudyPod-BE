package com.itschool.study_pod.dto.response;

import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class IntroduceResponse {
    private Long id;

    private String content;

    private boolean isPosted;

    private StudyGroupResponse studyGroup;

    private boolean isDeleted;

    private String createdBy;

    private LocalDateTime createdAt;

    private String updatedBy;

    private LocalDateTime updatedAt;
}
