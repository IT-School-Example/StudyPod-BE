package com.itschool.study_pod.entity;

import com.itschool.study_pod.enumclass.Subject;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Table(name = "subject_areas")
public class SubjectArea {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "subject_area_id", nullable = false)
    private Long subjectAreaId;

    @Column(name = "subject_name", nullable = false)
    @Enumerated(EnumType.STRING)
    private Subject subject;
}
