package com.itschool.study_pod.dto.request.adminboard;

import com.itschool.study_pod.dto.ReferenceDto;
import com.itschool.study_pod.enumclass.AdminBoardCategory;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data // 종합선물세트 : @Getter, @Setter, @ToString, @EqualsAndHashCode, @RequiredArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class AdminBoardRequest {

    @NotEmpty
    private String title;

    @NotEmpty
    private String content;

    @NotNull
    private AdminBoardCategory adminBoardCategory;

    @NotNull
    private ReferenceDto admin;
}
