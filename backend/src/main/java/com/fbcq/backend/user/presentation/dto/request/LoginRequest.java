package com.fbcq.backend.user.presentation.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record LoginRequest(
        @Pattern(regexp = "^01[0-9]{8,9}$", message = "유효한 전화번호 형식이어야 합니다.")
        String phoneNumber,

        @NotBlank(message = "비밀번호는 필수입니다.")
        String password
) {}