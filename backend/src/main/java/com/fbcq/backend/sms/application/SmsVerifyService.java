package com.fbcq.backend.sms.application;

import com.fbcq.backend.global.exception.InvalidAuthCodeException;
import com.fbcq.backend.sms.domain.AuthCodeStore;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SmsVerifyService {
    private final AuthCodeStore authCodeStore;

    public void verify(String phoneNumber, String inputCode) {
        String saved = authCodeStore.get(phoneNumber)
                .orElseThrow(InvalidAuthCodeException::new);
        if (!saved.equals(inputCode)) {
            throw new InvalidAuthCodeException();
        }

        authCodeStore.remove(phoneNumber); // 일회성으로 사용 후 삭제
    }
}

