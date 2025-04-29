package com.itschool.study_pod.dto.response;

import com.itschool.study_pod.enumclass.AccountRole;
import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class AdminResponse {
    private Long id;

    private String email;

    private String password;

    private AccountRole role;

    private String createdBy;

    private LocalDateTime createdAt;

    private String updatedBy;

    private LocalDateTime updatedAt;
}
