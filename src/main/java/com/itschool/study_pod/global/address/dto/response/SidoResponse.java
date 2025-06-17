package com.itschool.study_pod.global.address.dto.response;

import com.itschool.study_pod.domain.user.dto.response.UserResponse;
import lombok.*;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class SidoResponse {

    private String sidoCd;

    private String sidoNm;

    public static SidoResponse withId(String sidoCd) {

        return SidoResponse.builder()
                .sidoCd(sidoCd)
                .build();

    }
}