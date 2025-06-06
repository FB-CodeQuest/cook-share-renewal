package com.fbcq.backend.auth.infrastructure.repository;

import com.fbcq.backend.auth.domain.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringDataRefreshTokenRepository extends JpaRepository<RefreshToken, Integer> {
}
