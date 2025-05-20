package com.itschool.study_pod.global.security.mail;

import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class MailService {
    private final JavaMailSender javaMailSender;
    private final Map<String, Integer> verificationCodes = new HashMap<>();

    private int generateNumber() {
        return (int)(Math.random() * 900000) + 100000;
    }

    public MimeMessage createMail(String toEmail) {
        int number = generateNumber();
        verificationCodes.put(toEmail, number);

        MimeMessage message = javaMailSender.createMimeMessage();
        try {
            message.setFrom(new InternetAddress("noreply@yourdomain.com"));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(toEmail));
            message.setSubject("이메일 인증");

            String body = "<h3>요청하신 인증 번호입니다.</h3>";
            body += "<h1>" + number + "</h1>";
            body += "<h3>감사합니다.</h3>";

            message.setText(body, "UTF-8", "html");
        } catch (MessagingException e) {
            e.printStackTrace();
        }

        return message;
    }

    public int sendMail(String email) {
        MimeMessage message = createMail(email);
        javaMailSender.send(message);
        return verificationCodes.get(email);
    }

    public boolean verifyCode(String email, String inputCode) {
        Integer storedCode = verificationCodes.get(email);
        return storedCode != null && storedCode.toString().equals(inputCode);
    }
}