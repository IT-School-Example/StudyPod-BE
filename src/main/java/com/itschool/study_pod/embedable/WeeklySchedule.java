package com.itschool.study_pod.embedable;

import jakarta.persistence.*;
import lombok.*;

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
    private TimeRange timeRange;
}
