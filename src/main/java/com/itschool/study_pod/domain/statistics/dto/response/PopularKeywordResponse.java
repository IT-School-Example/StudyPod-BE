package com.itschool.study_pod.domain.statistics.dto.response;

import lombok.*;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class PopularKeywordResponse {

    private String keyword;
    private Long count;
}
