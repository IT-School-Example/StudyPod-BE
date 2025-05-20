package com.itschool.study_pod.global.security.mail;

import com.itschool.study_pod.global.security.mail.dto.request.MailCheckRequest;
import com.itschool.study_pod.global.security.mail.dto.request.MailRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/user")
public class MailController {
    private final MailService mailService;

    @PostMapping("/mailSend")
    public HashMap<String, Object> mailSend(@RequestBody MailRequest request) {
        HashMap<String, Object> map = new HashMap<>();
        try {
            int number = mailService.sendMail(request.getReceiverEmail());
            map.put("success", true);
            map.put("number", number); // 실제 서비스에서는 빼는 것이 안전함
        } catch (Exception e) {
            map.put("success", false);
            map.put("error", e.getMessage());
        }
        return map;
    }

    @PostMapping("/mailCheck")
    public ResponseEntity<?> mailCheck(@RequestBody MailCheckRequest request) {
        boolean isMatch = mailService.verifyCode(request.getEmail(), request.getCode());

        HashMap<String, Object> response = new HashMap<>();
        if (isMatch) {
            response.put("success", true);
            response.put("message", "인증번호가 일치합니다.");
        } else {
            response.put("success", false);
            response.put("message", "인증번호가 일치하지 않습니다.");
        }

        return ResponseEntity.ok(response);
    }
}

