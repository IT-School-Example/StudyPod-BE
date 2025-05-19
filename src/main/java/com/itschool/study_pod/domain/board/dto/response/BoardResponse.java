package com.itschool.study_pod.domain.board.dto.response;

import com.itschool.study_pod.domain.admin.dto.response.AdminResponse;
import com.itschool.study_pod.domain.studygroup.dto.response.StudyGroupResponse;
import com.itschool.study_pod.domain.user.dto.response.UserResponse;
import com.itschool.study_pod.global.enumclass.BoardCategory;
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

    private String createdBy;

    private LocalDateTime createdAt;

    private String updatedBy;

    private LocalDateTime updatedAt;

    public static BoardResponse withId(Long id) {
        return BoardResponse.builder()
                .id(id)
                .build();
    }
}
