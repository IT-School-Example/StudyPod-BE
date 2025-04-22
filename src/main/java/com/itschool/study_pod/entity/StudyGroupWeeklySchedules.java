package com.itschool.study_pod.entity;

import com.itschool.study_pod.enumclass.DayOfWeek;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalTime;

@Entity
@Table(name = "study_group_weekly_schedules",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"study_group_id", "day_of_week"})
        })
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StudyGroupWeeklySchedules {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "study_group_id", nullable = false)
    private StudyGroups studyGroup;

    @Enumerated(EnumType.STRING)
    @Column(name = "day_of_week", nullable = false)
    private DayOfWeek dayOfWeek;

    @Column(name = "start_time")
    private LocalTime startTime;

    @Column(name = "end_time")
    private LocalTime endTime;
}
