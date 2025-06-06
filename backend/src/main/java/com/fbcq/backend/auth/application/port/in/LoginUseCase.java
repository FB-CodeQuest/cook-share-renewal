package com.fbcq.backend.auth.application.port.in;

import com.fbcq.backend.auth.presentation.dto.response.TokenResponse;

public interface LoginUseCase {
    TokenResponse login(String phoneNumber, String password);
    TokenResponse reissue(String refreshToken);
}

