package com.itschool.study_pod.dto.request.Admin;

import lombok.*;

@Data // 종합선물세트 : @Getter, @Setter, @ToString, @EqualsAndHashCode, @RequiredArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class AdminPasswordRequest extends AdminRequest{
    private String password;

    private String newPassword;

    private String confirmNewPassword;
}
