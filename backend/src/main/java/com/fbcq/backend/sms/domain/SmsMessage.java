package com.fbcq.backend.sms.domain;

public record SmsMessage(String to, String content) {

    public static SmsMessage authCode(String phoneNumber, String code) {
        String content = "인증번호 [" + code + "]를 입력해주세요. <Cook Share>";
        return new SmsMessage(phoneNumber, content);
    }
}

