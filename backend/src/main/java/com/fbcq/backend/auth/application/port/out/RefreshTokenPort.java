package com.fbcq.backend.auth.application.port.out;

import com.fbcq.backend.auth.domain.RefreshToken;

import java.util.Optional;

public interface RefreshTokenPort {
    void save(RefreshToken refreshToken);
    Optional<RefreshToken> findByUserId(Integer userId);
    void deleteByUserId(Integer userId);
}

