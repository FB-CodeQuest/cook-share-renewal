package com.fbcq.backend.sms.infrastructure;

public class PhoneNumberUtil {
    public static String toE164Format(String rawNumber) {
        // 01012345678 -> +821012345678
        if (rawNumber.startsWith("0")) {
            return "+82" + rawNumber.substring(1);
        }
        // 이미 +로 시작하면 그대로 사용
        return rawNumber;
    }
}
