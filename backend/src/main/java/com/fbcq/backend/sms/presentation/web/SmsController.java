package com.fbcq.backend.sms.presentation;

import com.fbcq.backend.sms.application.SmsSendService;
import com.fbcq.backend.sms.application.SmsVerifyService;
import com.fbcq.backend.user.presentation.dto.response.CommonResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/sms")
@RequiredArgsConstructor
public class SmsController {
    private final SmsSendService smsSendService;
    private final SmsVerifyService smsVerifyService;

    /*
     * 인증번호 전송
     */
    @PostMapping("/phone")
    public ResponseEntity<CommonResponse<String>> sendAuthCode(@RequestParam String phoneNumber) {
        smsSendService.sendAuthCode(phoneNumber);
        return ResponseEntity.ok(CommonResponse.success(null, "인증번호가 전송되었습니다."));
    }

    /*
     * 인증번호 검증
     */
    @PostMapping("/verify")
    public ResponseEntity<CommonResponse<String>> verifyAuthCode(
            @RequestParam String phoneNumber,
            @RequestParam String authCode
    ) {
        smsVerifyService.verify(phoneNumber, authCode);
        return ResponseEntity.ok(CommonResponse.success(null, "인증이 완료되었습니다."));
    }
}
