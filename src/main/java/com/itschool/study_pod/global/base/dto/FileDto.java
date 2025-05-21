package com.itschool.study_pod.global.base.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class FileDto {
    @Schema(hidden = true)
    private String fileUrl;
}
