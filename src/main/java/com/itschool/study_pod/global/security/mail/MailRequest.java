package com.itschool.study_pod.global.security.mail;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MailRequest {
    private String senderEmail; // 인증번호를 받을 사용자 이메일
}
