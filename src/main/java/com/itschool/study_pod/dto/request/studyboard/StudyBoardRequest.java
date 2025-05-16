package com.itschool.study_pod.dto.request.studyboard;

import com.itschool.study_pod.dto.ReferenceDto;
import com.itschool.study_pod.enumclass.StudyBoardCategory;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data // 종합선물세트 : @Getter, @Setter, @ToString, @EqualsAndHashCode, @RequiredArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class StudyBoardRequest {

    @NotEmpty
    private String title;

    @NotEmpty
    private String content;

    @NotNull
    private StudyBoardCategory studyBoardCategory;

    private ReferenceDto user;

    private ReferenceDto studyGroup;
}
