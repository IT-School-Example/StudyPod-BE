package com.itschool.study_pod.domain.statistics.service;

import com.itschool.study_pod.domain.statistics.dto.response.StudyGroupStatisticsResponse;
import com.itschool.study_pod.domain.statistics.dto.response.YearMonthCountResponse;
import com.itschool.study_pod.domain.studygroup.repository.StudyGroupRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class StudyGroupStatisticsService {

    private final StudyGroupRepository studyGroupRepository;

    public StudyGroupStatisticsResponse getStudyGroupStatistics() {
        LocalDateTime today = LocalDate.now().atStartOfDay();
        LocalDateTime monthStart = LocalDate.now().withDayOfMonth(1).atStartOfDay();
        LocalDateTime yearStart = LocalDate.now().withDayOfYear(1).atStartOfDay();

        return StudyGroupStatisticsResponse.builder()
                .totalStudyGroupCount(studyGroupRepository.count())
                .todayStudyGroupCount(studyGroupRepository.countByCreatedAtAfter(today))
                .monthlyStudyGroupCount(studyGroupRepository.countByCreatedAtAfter(monthStart))
                .yearlyStudyGroupCount(studyGroupRepository.countByCreatedAtAfter(yearStart))
                .build();
    }

    public YearMonthCountResponse getStudyGroupCountByDate(int year, Integer month) {
        LocalDateTime start;
        LocalDateTime end;
        String label;

        if (month == null) {
            start = LocalDate.of(year, 1, 1).atStartOfDay();
            end = start.plusYears(1);
            label = String.valueOf(year);
        } else {
            start = LocalDate.of(year, month, 1).atStartOfDay();
            end = start.plusMonths(1);
            label = year + "-" + month;
        }

        long count = studyGroupRepository.countByCreatedAtBetween(start, end);
        return YearMonthCountResponse.builder()
                .data(Map.of(label, count))
                .build();
    }
}
