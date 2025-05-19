package com.itschool.study_pod.global.security.mail;

import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MailService {

    private final JavaMailSender javaMailSender;
    private final SenderEmail senderEmail;
    private static int number;

    public static void createNumber() {
        number = (int)(Math.random() * (90000)) + 100000;
    }

    public MimeMessage CreateMail(String mail) {
        createNumber();
        MimeMessage message = javaMailSender.createMimeMessage();

        try {
            message.setFrom(new InternetAddress(mailConfigLoader.getSenderEmail())); // ✅ JSON에서 가져옴
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(mail));
            message.setSubject("이메일 인증");

            String body = "<h3>요청하신 인증 번호입니다.</h3>";
            body += "<h1>" + number + "</h1>";
            body += "<h3>감사합니다.</h3>";

            message.setText(body,"UTF-8", "html");
        } catch (MessagingException e) {
            e.printStackTrace();
        }

        return message;
    }

    public int sendMail(String mail) {
        MimeMessage message = CreateMail(mail);
        javaMailSender.send(message);
        return number;
    }
}
