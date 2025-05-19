package com.itschool.study_pod.domain.comment.dto.request;

import com.itschool.study_pod.global.base.dto.ReferenceDto;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data // 종합선물세트 : @Getter, @Setter, @ToString, @EqualsAndHashCode, @RequiredArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class CommentRequest {

    @NotEmpty
    private String content;

    @NotNull
    private ReferenceDto board;

    @NotNull
    private ReferenceDto user;

    private ReferenceDto parentComment;
}
