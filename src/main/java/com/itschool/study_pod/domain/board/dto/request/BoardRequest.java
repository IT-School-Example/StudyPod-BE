package com.itschool.study_pod.domain.board.dto.request;

import com.itschool.study_pod.global.base.dto.ReferenceDto;
import com.itschool.study_pod.global.enumclass.BoardCategory;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@Data // 종합선물세트 : @Getter, @Setter, @ToString, @EqualsAndHashCode, @RequiredArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class BoardRequest {

    @NotEmpty
    private String title;

    @NotEmpty
    private String content;

    private BoardCategory category;

    private ReferenceDto user;

    private ReferenceDto admin;

    private ReferenceDto studyGroup;

}
