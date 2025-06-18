package com.fbcq.backend.auth.infrastructure.repository;

import com.fbcq.backend.auth.application.port.out.RefreshTokenCachePort;
import com.fbcq.backend.auth.domain.RefreshToken;
import com.fbcq.backend.auth.application.port.out.RefreshTokenPort;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class InMemoryRefreshTokenRepository implements RefreshTokenCachePort {

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

//Redis 전환용
//@Repository
//@Profile("prod")
//@RequiredArgsConstructor
//public class RedisRefreshTokenCacheRepository implements RefreshTokenCachePort {
//    private final StringRedisTemplate redisTemplate;
//
//    private final long ttl = 60 * 60 * 24 * 7;
//
//    @Override
//    public void save(RefreshToken token) {
//        redisTemplate.opsForValue().set("refresh:" + token.getUserId(), token.getToken(), ttl, TimeUnit.SECONDS);
//    }
//
//    @Override
//    public Optional<RefreshToken> findByUserId(Integer userId) {
//        String token = redisTemplate.opsForValue().get("refresh:" + userId);
//        return Optional.ofNullable(token).map(t -> RefreshToken.of(userId, t, ttl));
//    }
//
//    @Override
//    public void deleteByUserId(Integer userId) {
//        redisTemplate.delete("refresh:" + userId);
//    }
//}

