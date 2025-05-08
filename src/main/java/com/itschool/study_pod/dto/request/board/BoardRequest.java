package com.itschool.study_pod.dto.request.board;

import com.itschool.study_pod.dto.ReferenceDto;
import com.itschool.study_pod.enumclass.BoardCategory;
import io.swagger.v3.oas.annotations.media.Schema;
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

    @NotEmpty
    private BoardCategory category;

    private ReferenceDto user;

    private ReferenceDto admin;

    private ReferenceDto studyGroup;

}
