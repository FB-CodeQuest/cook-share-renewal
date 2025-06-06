package com.fbcq.backend.sms.application;

// SmsSendService.java

import com.fbcq.backend.global.exception.AuthCodeResendNotAllowedException;
import com.fbcq.backend.global.exception.ErrorCode;
import com.fbcq.backend.global.exception.TwilioRateLimitException;
import com.fbcq.backend.sms.domain.AuthCodeGenerator;
import com.fbcq.backend.sms.domain.AuthCodeStore;
import com.fbcq.backend.sms.domain.SmsMessage;
import com.twilio.exception.TwilioException;
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

        try {
            smsClient.send(message);
            authCodeStore.save(phoneNumber, code, AUTH_CODE_TTL_SECONDS);
        } catch (TwilioException e) {
            // 가장 현실적인 처리 방식
            if (e.getMessage() != null && e.getMessage().contains("429")) {
                throw new TwilioRateLimitException();
            }
            throw new RuntimeException("SMS 전송 실패", e);
        }
    }
}
