package com.fbcq.backend.auth.infrastructure.repository;

import com.fbcq.backend.auth.domain.RefreshToken;
import com.fbcq.backend.auth.application.port.out.RefreshTokenPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class RefreshTokenRepositoryAdapter implements RefreshTokenPort {

    private final SpringDataRefreshTokenRepository repository;

    @Override
    public void save(RefreshToken refreshToken) {
        repository.save(refreshToken);
    }

    @Override
    public Optional<RefreshToken> findByUserId(Integer userId) {
        return repository.findById(userId);
    }

    @Override
    public void deleteByUserId(Integer userId) {
        repository.deleteById(userId);
    }
}
