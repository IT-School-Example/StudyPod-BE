package com.itschool.study_pod.dto.request;

import com.itschool.study_pod.dto.ReferenceDto;
import lombok.*;

@Data // 종합선물세트 : @Getter, @Setter, @ToString, @EqualsAndHashCode, @RequiredArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class InterestedStudyRequest {

    private ReferenceDto user;

    private ReferenceDto studyGroup;

}
