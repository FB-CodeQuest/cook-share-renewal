package com.fbcq.backend.sms.infrastructure;

import com.fbcq.backend.sms.domain.AuthCodeStore;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class InMemoryAuthCodeStore implements AuthCodeStore {
    private final Map<String, TimedCode> store = new ConcurrentHashMap<>();

    @Override
    public void save(String phone, String code, int ttlSeconds) {
        store.put(phone, new TimedCode(code, System.currentTimeMillis() + ttlSeconds * 1000));
    }

    @Override
    public Optional<String> get(String phone) {
        TimedCode timed = store.get(phone);
        if (timed == null || timed.expired()) {
            return Optional.empty();  // ✅ null 반환 금지
        }
        return Optional.of(timed.code());
    }

    @Override
    public void remove(String phone) {
        store.remove(phone);
    }

    private record TimedCode(String code, long expiresAt) {
        boolean expired() {
            return System.currentTimeMillis() > expiresAt;
        }
    }
}

