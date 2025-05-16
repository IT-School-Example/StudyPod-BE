package com.itschool.study_pod.dto.request.address;

import jakarta.persistence.Column;
import lombok.*;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class SidoRequest {
    // fk용으로 필요
    private String sidoCd;

    private String sidoNm;
}
