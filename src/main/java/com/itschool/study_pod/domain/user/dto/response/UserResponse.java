package com.itschool.study_pod.domain.user.dto.response;

import lombok.*;

import java.time.LocalDateTime;

@Data // 종합선물세트 : @Getter, @Setter, @ToString, @EqualsAndHashCode, @RequiredArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class UserResponse {

    private Long id;

    private String email;

    // private AccountRole role;

    private String name;

    private String nickname;

    private String createdBy;

    private LocalDateTime createdAt;

    private String updatedBy;

    private LocalDateTime updatedAt;

    public static UserResponse withId(Long id) {
        return UserResponse.builder()
                .id(id)
                .build();
    }
}
