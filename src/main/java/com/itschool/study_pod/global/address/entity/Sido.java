package com.itschool.study_pod.global.address.entity;

import com.itschool.study_pod.global.address.dto.request.SidoRequest;
import com.itschool.study_pod.global.address.dto.response.SidoResponse;
import com.itschool.study_pod.global.base.crud.Convertible;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(schema = "address",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"sidoNm"})
        }
)
public class Sido implements Convertible<SidoRequest, SidoResponse> {
    @Id
    @JdbcTypeCode(SqlTypes.CHAR)
    @Column(length = 2) // , insertable = false, updatable = false
    private String sidoCd;

    @Column(columnDefinition = "character varying(100)") // , insertable = false, updatable = false
    private String sidoNm;

    @Deprecated
    public static Sido of(SidoRequest request) { // create용
        if(request != null) {
            return Sido.builder()
                    .sidoCd(request.getSidoCd())
                    .sidoNm(request.getSidoNm())
                    .build();
        }
        return null;
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
