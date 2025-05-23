package com.itschool.study_pod.global.security.mail.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MailCheckRequest {
    private String email;
    private String code;
}
