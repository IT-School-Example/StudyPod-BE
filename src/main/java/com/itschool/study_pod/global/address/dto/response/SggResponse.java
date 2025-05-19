package com.itschool.study_pod.global.address.dto.response;

import lombok.*;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class SggResponse {

    private Long id;

    private SidoResponse sido;

    private String sggCd;

    private String sggNm;

    public static SggResponse withId(Long id) {
        return SggResponse.builder()
                .id(id)
                .build();
    }
}
