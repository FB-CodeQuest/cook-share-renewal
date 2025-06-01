package com.fbcq.backend.sms.domain;

import java.security.SecureRandom;

public class AuthCodeGenerator {
    private static final SecureRandom random = new SecureRandom();
    private static final int CODE_LENGTH = 6;

    public String generate() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < CODE_LENGTH; i++) {
            sb.append(random.nextInt(10)); // 0 ~ 9
        }
        return sb.toString();
    }
}
