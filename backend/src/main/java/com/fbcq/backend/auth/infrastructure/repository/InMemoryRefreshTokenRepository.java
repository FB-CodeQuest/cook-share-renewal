package com.fbcq.backend.auth.infrastructure.repository;

import com.fbcq.backend.auth.domain.RefreshToken;
import com.fbcq.backend.auth.application.port.out.RefreshTokenPort;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class InMemoryRefreshTokenRepository implements RefreshTokenPort {

    private final Map<Integer, RefreshToken> storage = new ConcurrentHashMap<>();

    @Override
    public void save(RefreshToken refreshToken) {
        storage.put(refreshToken.getUserId(), refreshToken);
    }

    @Override
    public Optional<RefreshToken> findByUserId(Integer userId) {
        return Optional.ofNullable(storage.get(userId));
    }

    @Override
    public void deleteByUserId(Integer userId) {
        storage.remove(userId);
    }
}

