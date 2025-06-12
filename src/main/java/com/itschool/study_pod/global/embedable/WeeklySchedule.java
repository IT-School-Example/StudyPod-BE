package com.itschool.study_pod.global.embedable;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalTime;

@Embeddable
@Getter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class WeeklySchedule {

    @Schema(description = "요일", example = "MONDAY")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DayOfWeek dayOfWeek;

    @Schema(type = "string", format = "HH:mm", example = "10:00", description = "시작 시간")
    @Column(nullable = false)
    private LocalTime startTime;

    @Schema(type = "string", format = "HH:mm", example = "12:00", description = "종료 시간")
    @Column(nullable = false)
    private LocalTime endTime;

    public static WeeklySchedule of(DayOfWeek dayOfWeek, int sHour, int sMinute, int eHour, int eMinute) {
        return new WeeklySchedule(dayOfWeek, LocalTime.of(sHour, sMinute), LocalTime.of(eHour, eMinute));
    }

    @Schema(hidden = true)
    @Transient
    public long getPeriodMinutes() {
        if (startTime == null || endTime == null) {
            return 0L;
        }
        if (endTime.isBefore(startTime)) {
            return 0L;
        }
        return Duration.between(startTime, endTime).toMinutes();
    }

    @Schema(hidden = true)
    @Transient
    public Duration getPeriodDuration() {
        if (startTime == null || endTime == null || endTime.isBefore(startTime)) {
            return Duration.ZERO;
        }
        return Duration.between(startTime, endTime);
    }
}