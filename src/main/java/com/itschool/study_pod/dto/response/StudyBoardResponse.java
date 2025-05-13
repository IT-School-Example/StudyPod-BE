package com.itschool.study_pod.dto.response;

import com.itschool.study_pod.enumclass.StudyBoardCategory;
import lombok.*;

import java.time.LocalDateTime;

@Data // 종합선물세트 : @Getter, @Setter, @ToString, @EqualsAndHashCode, @RequiredArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class StudyBoardResponse {

    private Long id;

    private String title;

    private String content;

    private StudyBoardCategory studyBoardCategory;

    private UserResponse user;

    private StudyGroupResponse studyGroup;

    private String createdBy;

    private LocalDateTime createdAt;

    private String updatedBy;

    private LocalDateTime updatedAt;

    public static StudyBoardResponse withId(Long id) {
        return StudyBoardResponse.builder()
                .id(id)
                .build();
    }
}
