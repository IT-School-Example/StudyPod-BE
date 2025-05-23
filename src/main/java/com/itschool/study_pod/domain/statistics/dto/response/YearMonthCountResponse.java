package com.itschool.study_pod.domain.statistics.dto.response;

import lombok.*;

import java.util.Map;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class YearMonthCountResponse {
    private Map<String, Long> data;
}
