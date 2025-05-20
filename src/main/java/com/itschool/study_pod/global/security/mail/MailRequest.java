package com.itschool.study_pod.global.security.mail;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MailRequest {
    @NotEmpty
    private String receiverEmail; // 인증번호를 받을 사용자 이메일
}
