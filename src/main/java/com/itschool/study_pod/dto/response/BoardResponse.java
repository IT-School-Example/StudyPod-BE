package com.itschool.study_pod.dto.response;

import com.itschool.study_pod.enumclass.BoardCategory;
import lombok.*;

import java.time.LocalDateTime;

@Data // 종합선물세트 : @Getter, @Setter, @ToString, @EqualsAndHashCode, @RequiredArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class BoardResponse {

    private Long id;

    private String title;

    private String content;

    private BoardCategory category;

    private UserResponse user;

    private AdminResponse admin;

    private StudyGroupResponse studyGroup;

    private boolean isDeleted;

    private String createdBy;

    private LocalDateTime createdAt;

    private String updatedBy;

    private LocalDateTime updatedAt;
}
