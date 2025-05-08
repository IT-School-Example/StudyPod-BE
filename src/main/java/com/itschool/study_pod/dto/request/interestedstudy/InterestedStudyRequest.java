package com.itschool.study_pod.dto.request.interestedstudy;

import com.itschool.study_pod.dto.ReferenceDto;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@Data // 종합선물세트 : @Getter, @Setter, @ToString, @EqualsAndHashCode, @RequiredArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class InterestedStudyRequest {

    @NotEmpty
    private ReferenceDto user;

    @NotEmpty
    private ReferenceDto studyGroup;

}
