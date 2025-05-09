package com.itschool.study_pod.dto.request.board;

import com.itschool.study_pod.dto.ReferenceDto;
import com.itschool.study_pod.enumclass.BoardCategory;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@Data // 종합선물세트 : @Getter, @Setter, @ToString, @EqualsAndHashCode, @RequiredArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class StudyNoticeRequest {

    @NotEmpty
    private String title;

    @NotEmpty
    private String content;

    private BoardCategory category;

    private ReferenceDto user;

    // private ReferenceDto admin;

    private ReferenceDto studyGroup;

}
