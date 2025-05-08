package com.itschool.study_pod.dto.request.comment;

import com.itschool.study_pod.dto.ReferenceDto;
import lombok.*;

@Data // 종합선물세트 : @Getter, @Setter, @ToString, @EqualsAndHashCode, @RequiredArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class CommentRequest {

    private String content;

    private ReferenceDto board;

    private ReferenceDto user;

    private ReferenceDto parentComment;
}
