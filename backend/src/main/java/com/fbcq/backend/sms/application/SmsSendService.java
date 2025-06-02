package com.fbcq.backend.sms.application;

// SmsSendService.java

import com.fbcq.backend.global.exception.AuthCodeResendNotAllowedException;
import com.fbcq.backend.sms.domain.AuthCodeGenerator;
import com.fbcq.backend.sms.domain.AuthCodeStore;
import com.fbcq.backend.sms.domain.SmsMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SmsSendService {
    private final AuthCodeGenerator codeGenerator;
    private final SmsClient smsClient;
    private final AuthCodeStore authCodeStore;

    private static final int AUTH_CODE_TTL_SECONDS = 300;

    public void sendAuthCode(String phoneNumber) {
        if (authCodeStore.get(phoneNumber).isPresent()) {
            throw new AuthCodeResendNotAllowedException(); // 3분 내 재전송 불가
        }
        String code = codeGenerator.generate();
        SmsMessage message = SmsMessage.authCode(phoneNumber, code);

        smsClient.send(message);
        authCodeStore.save(phoneNumber, code, AUTH_CODE_TTL_SECONDS);
    }
}
