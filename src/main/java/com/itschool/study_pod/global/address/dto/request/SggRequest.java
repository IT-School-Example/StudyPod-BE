package com.itschool.study_pod.global.address.dto.request;

import lombok.*;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class SggRequest {

    private Long id;

    private SidoRequest sido;

    private String sggCd;

    private String sggNm;
}
