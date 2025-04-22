package com.itschool.study_pod.entity.address;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(schema = "address")
public class Sgg {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sgg_id", insertable = false, updatable = false)
    private Long id;

    // @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sido_cd", insertable = false, updatable = false)
    private Sido sido;  // `Sido` 엔티티와의 외래 키 관계

    // @Id
    @Column(columnDefinition = "CHAR(3)", insertable = false, updatable = false)
    private String sggCd;

    @Column(columnDefinition = "character varying(100)")
    private String sggNm;
}
