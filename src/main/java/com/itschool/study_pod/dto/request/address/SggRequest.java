package com.itschool.study_pod.dto.request.address;

import com.itschool.study_pod.entity.address.Sido;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class SggRequest {

    private Long id;

    private SidoRequest sido;

    private String sggCd;

    private String sggNm;
}
