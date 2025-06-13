package com.itschool.study_pod.domain.subjectarea.entity;

import com.itschool.study_pod.domain.subjectarea.dto.request.SubjectAreaRequest;
import com.itschool.study_pod.domain.subjectarea.dto.response.SubjectAreaResponse;
import com.itschool.study_pod.global.enumclass.Subject;
import com.itschool.study_pod.global.base.crud.Convertible;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Table(name = "subject_areas")
public class SubjectArea implements Convertible<SubjectAreaRequest, SubjectAreaResponse> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "subject_area_id", nullable = false)
    private Long id;

    @Column(name = "subject_name", nullable = false)
    @Enumerated(EnumType.STRING)
    private Subject subject;

    public static SubjectArea of(SubjectAreaRequest request) { // create용
        if (request != null) {
            return SubjectArea.builder()
                    .subject(request.getSubject())
                    .build();
        }
        return null;
    }

    public static SubjectArea withId(Long id) {
        return SubjectArea.builder()
                .id(id)
                .build();
    }

    @Override
    public void update(SubjectAreaRequest request) {
        this.subject = request.getSubject();
    }

    @Override
    public SubjectAreaResponse response() {
        return SubjectAreaResponse.builder()
                .id(this.id)
                .subject(this.subject)
                .build();
    }
}