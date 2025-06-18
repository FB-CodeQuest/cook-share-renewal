package com.fbcq.backend.auth.application.port.out;

import com.fbcq.backend.auth.domain.RefreshToken;

import java.util.Optional;

// 캐시 접근용 (Map or Redis)
public interface RefreshTokenCachePort {
    void save(RefreshToken refreshToken);
    Optional<RefreshToken> findByUserId(Integer userId);
    void deleteByUserId(Integer userId);
}

