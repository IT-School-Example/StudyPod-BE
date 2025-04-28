package com.itschool.study_pod.dto.request.Comment;

import lombok.*;

@Data // 종합선물세트 : @Getter, @Setter, @ToString, @EqualsAndHashCode, @RequiredArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class CommentRequest{

    private String content;

    private Long boardId;

    private Long userId;

    private Long parentCommentId;
}
