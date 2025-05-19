package com.itschool.study_pod.domain.adminboard.dto.response;

import com.itschool.study_pod.domain.admin.dto.response.AdminResponse;
import com.itschool.study_pod.global.enumclass.AdminBoardCategory;
import lombok.*;

import java.time.LocalDateTime;

@Data // 종합선물세트 : @Getter, @Setter, @ToString, @EqualsAndHashCode, @RequiredArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class AdminBoardResponse {

    private Long id;

    private String title;

    private String content;

    private AdminBoardCategory adminBoardCategory;

    private AdminResponse admin;

    private String createdBy;

    private LocalDateTime createdAt;

    private String updatedBy;

    private LocalDateTime updatedAt;

    public static AdminBoardResponse withId(Long id) {
        return AdminBoardResponse.builder()
                .id(id)
                .build();
    }
}
