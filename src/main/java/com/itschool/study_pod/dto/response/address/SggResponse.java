package com.itschool.study_pod.dto.response.address;

import com.itschool.study_pod.entity.address.Sido;
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
}
