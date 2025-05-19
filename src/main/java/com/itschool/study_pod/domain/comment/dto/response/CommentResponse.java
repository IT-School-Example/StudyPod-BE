package com.itschool.study_pod.domain.comment.dto.response;

import com.itschool.study_pod.domain.board.dto.response.BoardResponse;
import com.itschool.study_pod.domain.user.dto.response.UserResponse;
import lombok.*;

import java.time.LocalDateTime;

@Data // 종합선물세트 : @Getter, @Setter, @ToString, @EqualsAndHashCode, @RequiredArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class CommentResponse {

    private Long id;

    private String content;

    private BoardResponse board;

    private CommentResponse parentComment;

    private UserResponse user;

    private String createdBy;

    private LocalDateTime createdAt;

    private String updatedBy;

    private LocalDateTime updatedAt;

    public static CommentResponse withId(Long id) {
        return CommentResponse.builder()
                .id(id)
                .build();
    }
}
