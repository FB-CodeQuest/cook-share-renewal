package com.fbcq.backend.user.domain.service;

public interface PasswordEncoder {
    String encode(String raw);
    boolean matches(String raw, String encoded);
}

