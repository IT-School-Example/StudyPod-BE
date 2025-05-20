package com.itschool.study_pod.global.security.mail;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@Slf4j
@RequiredArgsConstructor
@RestController
public class MailController {
    private final MailService mailService;
    private int number; // 이메일 인증 숫자를 저장하는 변수

    @PostMapping("/mailSend")
    public HashMap<String, Object> mailSend(@RequestBody MailRequest request) {
        HashMap<String, Object> map = new HashMap<>();

        try {
            number = mailService.sendMail(request.getReceiverEmail());
            map.put("success", true);
            map.put("number", String.valueOf(number));
        } catch (Exception e) {
            map.put("success", false);
            map.put("error", e.getMessage());
        }

        return map;
    }


    // 인증번호 일치여부 확인
    @GetMapping("/mailCheck")
    public ResponseEntity<?> mailCheck(@RequestParam String userNumber) {

        boolean isMatch = userNumber.equals(String.valueOf(number));

        return ResponseEntity.ok(isMatch);
    }

}
