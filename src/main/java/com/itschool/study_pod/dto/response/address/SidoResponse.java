package com.itschool.study_pod.dto.response.address;

import lombok.*;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class SidoResponse {

    private String sidoCd;

    private String sidoNm;
}
