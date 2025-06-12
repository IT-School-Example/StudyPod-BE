package com.itschool.study_pod.global.address.dto.response;

import com.itschool.study_pod.global.address.entity.Sgg;
import lombok.*;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
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

    public static SggResponse toDto(Sgg sgg) {
        return SggResponse.builder()
                .id(sgg.getId())
                .sggCd(sgg.getSggCd())
                .sggNm(sgg.getSggNm())
                .sido(SidoResponse.builder()
                        .sidoCd(sgg.getSido().getSidoCd())
                        .sidoNm(sgg.getSido().getSidoNm())
                        .build())
                .build();
    }

    public SggResponse toSggResponse(Sgg sgg) {
        return SggResponse.builder()
                .id(sgg.getId())
                .sggCd(sgg.getSggCd())
                .sggNm(sgg.getSggNm())
                .sido(SidoResponse.builder()
                        .sidoCd(sgg.getSido().getSidoCd())
                        .sidoNm(sgg.getSido().getSidoNm())
                        .build())
                .build();
    }


}
