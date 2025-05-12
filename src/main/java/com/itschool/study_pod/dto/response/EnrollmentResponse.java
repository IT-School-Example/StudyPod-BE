package com.itschool.study_pod.dto.response;

import com.itschool.study_pod.enumclass.EnrollmentStatus;
import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class EnrollmentResponse {

    private Long id;

    private LocalDateTime appliedAt;

    private String introduce;

    private LocalDateTime joinedAt;

    private EnrollmentStatus status;

    private StudyGroupResponse studyGroup;

    private UserResponse user;

    private String createdBy;

    private LocalDateTime createdAt;

    private String updatedBy;

    private LocalDateTime updatedAt;

    public static EnrollmentResponse withId(Long id) {
        return EnrollmentResponse.builder()
                .id(id)
                .build();
    }
}
