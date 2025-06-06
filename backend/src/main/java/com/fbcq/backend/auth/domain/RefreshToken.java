package com.fbcq.backend.auth.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity
public class RefreshToken {

    @Id
    private Integer userId;

    private String token;
    private LocalDateTime expiresAt;

    public static RefreshToken of(Integer userId, String token, long ttlSeconds) {
        return new RefreshToken(userId, token, LocalDateTime.now().plusSeconds(ttlSeconds));
    }

    public void update(String newToken, long ttlSeconds) {
        this.token = newToken;
        this.expiresAt = LocalDateTime.now().plusSeconds(ttlSeconds);
    }

    public boolean isExpired() {
        return expiresAt.isBefore(LocalDateTime.now());
    }
}
