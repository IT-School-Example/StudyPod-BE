package com.itschool.study_pod.domain.statistics.controller;

import com.itschool.study_pod.domain.statistics.dto.response.PopularKeywordResponse;
import com.itschool.study_pod.domain.statistics.dto.response.UserStatisticsResponse;
import com.itschool.study_pod.domain.statistics.dto.response.StudyGroupStatisticsResponse;
import com.itschool.study_pod.domain.statistics.dto.response.YearMonthCountResponse;
import com.itschool.study_pod.domain.statistics.service.PopularKeywordService;
import com.itschool.study_pod.domain.statistics.service.StudyGroupStatisticsService;
import com.itschool.study_pod.domain.statistics.service.UserStatisticsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin/statistics")
@Tag(name = "통계", description = "통계 API")
@PreAuthorize("hasRole('ROLE_ADMIN')")
public class AdminStatisticsController {

    private final UserStatisticsService userStatisticsService;
    private final StudyGroupStatisticsService studyGroupStatisticsService;
    private final PopularKeywordService popularKeywordService;

    @GetMapping("/users")
    @Operation(summary = "사용자 통계", description = "전체, 오늘, 이번 달, 올해 사용자 수 반환")
    public UserStatisticsResponse getUserStatistics() {
        return userStatisticsService.getUserStatistics();
    }

    @GetMapping("/study-groups")
    @Operation(summary = "스터디 그룹 통계", description = "전체, 오늘, 이번 달, 올해 스터디 그룹 수 반환")
    public StudyGroupStatisticsResponse getStudyGroupStatistics() {
        return studyGroupStatisticsService.getStudyGroupStatistics();
    }

    @GetMapping("/users/by-date")
    @Operation(summary = "연도 / 월별 사용자 수", description = "연도 또는 연+월 기준 사용자 수")
    public YearMonthCountResponse getUserCountByDate(@RequestParam(name = "year") int year,
                                                     @RequestParam(name = "month", required = false) Integer month) {
        return userStatisticsService.getUserCountByDate(year, month);
    }

    @GetMapping("/study-groups/by-date")
    @Operation(summary = "연도 / 월별 스터디 그룹 수", description = "연도 또는 연+월 기준 스터디 그룹 수")
    public YearMonthCountResponse getStudyGroupCountByDate(@RequestParam(name = "year") int year,
                                                         @RequestParam(name = "month", required = false) Integer month) {
        return studyGroupStatisticsService.getStudyGroupCountByDate(year, month);
    }

    @GetMapping("/popular-keywords")
    @Operation(summary = "인기 키워드 조회", description = "스터디 그룹 키워드 중 가장 많이 사용된 상위 검색어 목록 조회")
    public List<PopularKeywordResponse> getPopularKeywords() {
        return popularKeywordService.getPopularKeywords();
    }
}
