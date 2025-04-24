package com.itschool.study_pod.entity;

import com.itschool.study_pod.dto.request.SubjectArea.SubjectAreaRequest;
import com.itschool.study_pod.dto.request.User.UserCreateRequest;
import com.itschool.study_pod.dto.response.SubjectAreaResponse;
import com.itschool.study_pod.enumclass.Subject;
import com.itschool.study_pod.ifs.Convertible;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
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
        return SubjectArea.builder()
                .build();
    }

    @Override
    public void update(SubjectAreaRequest request) {
        this.subject = request.getSubject();
    }

    @Override
    public SubjectAreaResponse response() {
        return null;
    }
}
