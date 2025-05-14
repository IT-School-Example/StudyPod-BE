package com.itschool.study_pod.dto.response;

import com.itschool.study_pod.entity.StudyGroup;
import com.itschool.study_pod.entity.User;

import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class InterestedStudyResponse {

    private Long id;

    private UserResponse user;

    private StudyGroupResponse studyGroup;

    private Boolean isDeleted;

    private String createdBy;

    private LocalDateTime createdAt;

    private String updatedBy;

    private LocalDateTime updatedAt;

    public static InterestedStudyResponse withId(Long id) {
        return InterestedStudyResponse.builder()
                .id(id)
                .build();
    }
}
