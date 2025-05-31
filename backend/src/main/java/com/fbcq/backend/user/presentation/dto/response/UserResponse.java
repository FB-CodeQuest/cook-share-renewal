package com.fbcq.backend.user.presentation.dto.response;

public record UserResponse(
        Long userId,
        String nickname
) {}