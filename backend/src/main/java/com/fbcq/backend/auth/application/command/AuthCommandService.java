package com.fbcq.backend.auth.application.command;

import com.fbcq.backend.auth.application.port.in.LoginUseCase;
import com.fbcq.backend.auth.application.port.out.RefreshTokenPort;
import com.fbcq.backend.auth.domain.RefreshToken;
import com.fbcq.backend.auth.infrastructure.repository.InMemoryRefreshTokenRepository;
import com.fbcq.backend.auth.presentation.dto.response.TokenResponse;
import com.fbcq.backend.auth.infrastructure.jwt.JwtTokenProvider;
import com.fbcq.backend.global.exception.ErrorCode;
import com.fbcq.backend.global.exception.UnauthorizedException;
import com.fbcq.backend.user.application.command.UserCommandService;
import com.fbcq.backend.user.domain.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AuthCommandService implements LoginUseCase {

    private final JwtTokenProvider jwtTokenProvider;
    private final UserCommandService userCommandService;
    private final InMemoryRefreshTokenRepository refreshTokenRepository;

    private final long refreshTokenTTL = 60 * 60 * 24 * 7; // 7일

    @Override
    public TokenResponse login(String phoneNumber, String password) {
        User user = userCommandService.authenticate(phoneNumber, password); // 읽기 전용 서비스

        String accessToken = jwtTokenProvider.createToken(user.getUserId(), "ROLE_USER");
        String refreshToken = jwtTokenProvider.createRefreshToken(user.getUserId());

        refreshTokenRepository.save(RefreshToken.of(user.getUserId(), refreshToken, refreshTokenTTL));

        return new TokenResponse(accessToken, refreshToken);
    }

    @Override
    public TokenResponse reissue(String refreshToken) {
        Integer userId = jwtTokenProvider.getUserIdFromRefreshToken(refreshToken); // subject 기준
        RefreshToken token = refreshTokenRepository.findByUserId(userId)
                .orElseThrow(() -> new IllegalArgumentException("토큰 없음"));

        if (!token.getToken().equals(refreshToken) || token.isExpired()) {
            throw new IllegalArgumentException("토큰 만료 또는 위조");
        }

        String newAccessToken = jwtTokenProvider.createToken(userId, "ROLE_USER");
        String newRefreshToken = jwtTokenProvider.createRefreshToken(userId);

        token.update(newRefreshToken, refreshTokenTTL);
        refreshTokenRepository.save(token);

        return new TokenResponse(newAccessToken, newRefreshToken);
    }

    public void logout(String refreshToken) {
        Integer userId = jwtTokenProvider.getUserIdFromRefreshToken(refreshToken);

        RefreshToken savedToken = refreshTokenRepository.findByUserId(userId)
                .orElseThrow(() -> new UnauthorizedException(ErrorCode.INVALID_REFRESH_TOKEN));

        if (!refreshToken.equals(savedToken.getToken())) {
            throw new UnauthorizedException(ErrorCode.INVALID_REFRESH_TOKEN);
        }

        if (savedToken.isExpired()) {
            refreshTokenRepository.deleteByUserId(userId); // 만료된 토큰도 정리
            throw new UnauthorizedException(ErrorCode.REFRESH_TOKEN_EXPIRED);
        }

        refreshTokenRepository.deleteByUserId(userId);
    }

}
