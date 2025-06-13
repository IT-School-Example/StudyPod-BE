package com.itschool.study_pod.global.base.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReferenceDto {
    private Long id;

    public static ReferenceDto withId(Long id) {
        return ReferenceDto.builder().id(id).build();
    }
}

