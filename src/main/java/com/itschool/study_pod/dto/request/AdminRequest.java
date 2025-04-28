package com.itschool.study_pod.dto.request;

import com.itschool.study_pod.enumclass.AccountRole;
import lombok.*;

@Data // 종합선물세트 : @Getter, @Setter, @ToString, @EqualsAndHashCode, @RequiredArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class AdminRequest {

    // fk용으로 필요
    private Long id;

    private String email;

    private String password;

    private AccountRole role;
}
