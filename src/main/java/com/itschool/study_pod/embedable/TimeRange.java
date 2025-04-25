package com.itschool.study_pod.embedable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Duration;
import java.time.LocalTime;

@Embeddable
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TimeRange {

    @Column(nullable = false)
    private LocalTime startTime;

    @Column(nullable = false)
    private LocalTime endTime;

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

    @Transient
    public Duration getPeriodDuration() {
        if (startTime == null || endTime == null || endTime.isBefore(startTime)) {
            return Duration.ZERO;
        }
        return Duration.between(startTime, endTime);
    }

    public static TimeRange of(int sHour, int sMinute, int eHour, int eMinute) {
        return new TimeRange(LocalTime.of(sHour, sMinute), LocalTime.of(eHour, eMinute));
    }
}