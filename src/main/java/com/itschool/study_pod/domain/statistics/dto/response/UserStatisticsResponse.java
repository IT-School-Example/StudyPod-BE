package com.itschool.study_pod.domain.statistics.dto.response;

import lombok.*;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class UserStatisticsResponse {
    // 사용자
    private long totalUserCount; // 전체
    private long todayUserCount; // 오늘
    private long monthlyUserCount; // 이번 달
    private long yearlyUserCount; // 올해


}
