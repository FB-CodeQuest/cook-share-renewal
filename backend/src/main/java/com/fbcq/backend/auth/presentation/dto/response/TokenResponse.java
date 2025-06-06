package com.fbcq.backend.auth.presentation.dto.response;

public record TokenResponse(
        String accessToken,
        String refreshToken
) {}

