package com.itschool.study_pod.dto.response;

import com.itschool.study_pod.entity.SubjectArea;
import lombok.*;

import java.time.LocalDateTime;
@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class InterestedSubjectResponse {

    private Long id;

    private UserResponse user;

    private SubjectAreaResponse subjectArea;

    private String createdBy;

    private LocalDateTime createdAt;

    private String updatedBy;

    private LocalDateTime updatedAt;

    public static InterestedSubjectResponse withId(Long id) {
        return InterestedSubjectResponse.builder()
                .id(id)
                .build();
    }
}
