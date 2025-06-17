package com.itschool.study_pod.global.address.entity;

import com.itschool.study_pod.domain.user.entity.User;
import com.itschool.study_pod.global.address.dto.request.SidoRequest;
import com.itschool.study_pod.global.address.dto.response.SidoResponse;
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
@Table(schema = "address",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"sido_nm"})
        }
)
public class Sido implements Convertible<SidoRequest, SidoResponse> {
    @Id
    @JdbcTypeCode(SqlTypes.CHAR)
    @Column(name = "sido_cd", length = 2)
    private String sidoCd;

    @Column(name = "sido_nm", columnDefinition = "character varying(100)")
    private String sidoNm;

    @Deprecated
    public static Sido of(SidoRequest request) { // create용
        if (request != null) {
            return Sido.builder()
                    .sidoCd(request.getSidoCd())
                    .sidoNm(request.getSidoNm())
                    .build();
        }
        return null;
    }

    public static Sido withId(String sidoCd) {
        return Sido.builder()
                .sidoCd(sidoCd)
                .build();
    }

    @Deprecated
    @Override
    public void update(SidoRequest request) {
        throw new RuntimeException("사용하지 않음");
    }

    // 조회만 허용
    @Override
    public SidoResponse response() {
        return SidoResponse.builder()
                .sidoCd(this.sidoCd)
                .sidoNm(this.sidoNm)
                .build();
    }
}