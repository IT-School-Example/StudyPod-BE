package com.itschool.study_pod.embedable;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.DayOfWeek;

@Embeddable
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WeeklySchedule {
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DayOfWeek dayOfWeek;

    @Embedded
    @Column(nullable = false)
    private TimeRange timeRange;
}
