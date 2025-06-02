package com.fbcq.backend.sms.domain;

import java.util.Optional;

public interface AuthCodeStore {
    void save(String phoneNumber, String code, int ttlSeconds);  // 인증번호 저장
    Optional<String> get(String phoneNumber);                              // 인증번호 조회
    void remove(String phoneNumber);                             // 인증번호 사용 후 삭제
}
