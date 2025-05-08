package com.itschool.study_pod.dto.request.comment;

import com.itschool.study_pod.dto.ReferenceDto;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@Data // 종합선물세트 : @Getter, @Setter, @ToString, @EqualsAndHashCode, @RequiredArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class CommentRequest {

    @NotEmpty
    private String content;

    @NotEmpty
    private ReferenceDto board;

    @NotEmpty
    private ReferenceDto user;

    private ReferenceDto parentComment;
}
