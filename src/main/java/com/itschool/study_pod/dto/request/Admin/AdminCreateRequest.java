package com.itschool.study_pod.dto.request.Admin;

import com.itschool.study_pod.enumclass.AccountRole;
import lombok.*;

@Data // 종합선물세트 : @Getter, @Setter, @ToString, @EqualsAndHashCode, @RequiredArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class AdminCreateRequest extends AdminRequest {
    private String email;

    private String password;

    private AccountRole role;
}
