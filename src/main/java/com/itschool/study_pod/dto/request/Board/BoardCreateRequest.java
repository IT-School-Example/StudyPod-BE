package com.itschool.study_pod.dto.request.Board;

import com.itschool.study_pod.enumclass.BoardCategory;
import lombok.*;

@Data // 종합선물세트 : @Getter, @Setter, @ToString, @EqualsAndHashCode, @RequiredArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class BoardCreateRequest {

    private String title;

    private String content;

    private BoardCategory category;

    private Long userId;

    private Long adminId;

    private Long studyGroupId;

}
