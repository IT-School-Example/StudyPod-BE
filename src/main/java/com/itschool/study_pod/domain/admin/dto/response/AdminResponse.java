package com.itschool.study_pod.domain.admin.dto.response;

import com.itschool.study_pod.global.enumclass.AccountRole;
import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class AdminResponse {
    private Long id;

    private String email;

    // private String password;

    private AccountRole role;

    private String createdBy;

    private LocalDateTime createdAt;

    private String updatedBy;

    private LocalDateTime updatedAt;

    public static AdminResponse withId(Long id) {
        return AdminResponse.builder()
                .id(id)
                .build();
    }
}
