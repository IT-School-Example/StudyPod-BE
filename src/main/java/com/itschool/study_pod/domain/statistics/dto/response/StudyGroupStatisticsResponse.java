package com.itschool.study_pod.domain.statistics.dto.response;

import lombok.*;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class StudyGroupStatisticsResponse {
    // 스터디 그룹
    private long totalStudyGroupCount; // 전체
    private long todayStudyGroupCount; // 오늘
    private long monthlyStudyGroupCount; // 이번 달
    private long yearlyStudyGroupCount; // 올해
}
