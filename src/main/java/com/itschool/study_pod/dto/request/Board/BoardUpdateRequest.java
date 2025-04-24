package com.itschool.study_pod.dto.request.Board;

import lombok.*;

@Data // 종합선물세트 : @Getter, @Setter, @ToString, @EqualsAndHashCode, @RequiredArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class BoardUpdateRequest extends BoardRequest {

    private String title;

    private String content;
}
