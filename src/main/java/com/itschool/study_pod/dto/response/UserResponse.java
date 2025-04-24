package com.itschool.study_pod.dto.response;

import com.itschool.study_pod.enumclass.AccountRole;
import lombok.*;

@Data // 종합선물세트 : @Getter, @Setter, @ToString, @EqualsAndHashCode, @RequiredArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class UserResponse {

    private Long id;

    private String email;

    private AccountRole role;

    private String name;

    private String nickname;
}
