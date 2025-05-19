package com.itschool.study_pod.global.address.dto.request;

import lombok.*;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class SidoRequest {
    // fk용으로 필요
    private String sidoCd;

    private String sidoNm;
}
