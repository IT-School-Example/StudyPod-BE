package com.itschool.study_pod.entity.base;


import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@MappedSuperclass
@SuperBuilder // Lombok의 상속 가능한 빌더
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Post extends BaseEntity {

    @Column(nullable = false)
    protected String title;

    @Column(nullable = false)
    protected String content;

}
