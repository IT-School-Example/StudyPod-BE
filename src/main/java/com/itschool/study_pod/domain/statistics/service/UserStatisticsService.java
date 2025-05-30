package com.itschool.study_pod.domain.statistics.service;

import com.itschool.study_pod.domain.statistics.dto.response.UserStatisticsResponse;
import com.itschool.study_pod.domain.statistics.dto.response.YearMonthCountResponse;
import com.itschool.study_pod.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class UserStatisticsService {

    private final UserRepository userRepository;

    public UserStatisticsResponse getUserStatistics() {
        LocalDateTime today = LocalDate.now().atStartOfDay();
        LocalDateTime monthStart = LocalDate.now().withDayOfMonth(1).atStartOfDay();
        LocalDateTime yearStart = LocalDate.now().withDayOfYear(1).atStartOfDay();

        return UserStatisticsResponse.builder()
                .totalUserCount(userRepository.count())
                .todayUserCount(userRepository.countByCreatedAtAfter(today))
                .monthlyUserCount(userRepository.countByCreatedAtAfter(monthStart))
                .yearlyUserCount(userRepository.countByCreatedAtAfter(yearStart))
                .build();
    }

    public YearMonthCountResponse getUserCountByDate(int year, Integer month) {
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

        long count = userRepository.countByCreatedAtBetween(start, end);
        return YearMonthCountResponse.builder()
                .data(Map.of(label, count))
                .build();
    }
}
