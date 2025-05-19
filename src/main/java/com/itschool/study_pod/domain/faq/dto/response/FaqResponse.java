package com.itschool.study_pod.domain.faq.dto.response;

import com.itschool.study_pod.domain.admin.dto.response.AdminResponse;
import lombok.*;

import java.time.LocalDateTime;

@Data // 종합선물세트 : @Getter, @Setter, @ToString, @EqualsAndHashCode, @RequiredArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class FaqResponse {

    private Long id;

    private String question;

    private String answer;

    private Boolean visible;

    private AdminResponse admin;

    private String createdBy;

    private LocalDateTime createdAt;

    private String updatedBy;

    private LocalDateTime updatedAt;

    public static FaqResponse withId(Long id) {
        return FaqResponse.builder()
                .id(id)
                .build();
    }
}
