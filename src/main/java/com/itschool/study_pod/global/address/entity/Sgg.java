package com.itschool.study_pod.global.address.entity;

import com.itschool.study_pod.global.address.dto.request.SggRequest;
import com.itschool.study_pod.global.address.dto.response.SggResponse;
import com.itschool.study_pod.global.base.crud.Convertible;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Table(schema = "address")
public class Sgg implements Convertible<SggRequest, SggResponse> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sgg_id", insertable = false, updatable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sido_cd") // ✅ 수정: insertable/updatable 제거
    private Sido sido;

    @JdbcTypeCode(SqlTypes.CHAR)
    @Column(length = 3) // ✅ 수정: insertable/updatable 제거
    private String sggCd;

    @Column(columnDefinition = "character varying(100)")
    private String sggNm;

    @Deprecated
    public static Sgg of(SggRequest request) {
        if (request != null) {
            return Sgg.builder()
                    .id(request.getId())
                    .sido(Sido.of(request.getSido()))
                    .sggCd(request.getSggCd())
                    .sggNm(request.getSggNm())
                    .build();
        }
        return null;
    }

    public static Sgg withId(Long id) {
        return Sgg.builder()
                .id(id)
                .build();
    }

    @Deprecated
    @Override
    public void update(SggRequest request) {
        throw new RuntimeException("사용하지 않음");
    }

    @Override
    public SggResponse response() {
        return SggResponse.builder()
                .id(this.id)
                .sido(this.sido.response())
                .sggCd(this.sggCd)
                .sggNm(this.sggNm)
                .build();
    }
}
