package com.itschool.study_pod.dto.request.User;

import lombok.*;

@Data // 종합선물세트 : @Getter, @Setter, @ToString, @EqualsAndHashCode, @RequiredArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class UserPasswordRequest extends UserRequest {

    private String password;

    private String newPassword;

    private String confirmNewPassword;
}
