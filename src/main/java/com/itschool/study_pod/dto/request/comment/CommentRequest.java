package com.itschool.study_pod.dto.request.comment;

import com.itschool.study_pod.dto.ReferenceDto;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data // 종합선물세트 : @Getter, @Setter, @ToString, @EqualsAndHashCode, @RequiredArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class CommentRequest {

    @NotEmpty
    private String content;

    @NotNull
    private ReferenceDto studyBoard;

    @NotNull
    private ReferenceDto user;

    private ReferenceDto parentComment;
}
