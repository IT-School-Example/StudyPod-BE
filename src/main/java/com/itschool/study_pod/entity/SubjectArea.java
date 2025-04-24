package com.itschool.study_pod.entity;

import com.itschool.study_pod.dto.request.SubjectAreaRequest;
import com.itschool.study_pod.enumclass.Subject;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Table(name = "subject_areas")
public class SubjectArea {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "subject_area_id", nullable = false)
    private Long id;

    @Column(name = "subject_name", nullable = false)
    @Enumerated(EnumType.STRING)
    private Subject subject;

    public void update(SubjectAreaRequest request) {
        this.subject = request.getSubject();
    }
}
