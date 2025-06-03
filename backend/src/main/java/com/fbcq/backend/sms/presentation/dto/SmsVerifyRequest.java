package com.fbcq.backend.sms.presentation.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;

public record SmsVerifyRequest(
        @Schema(description = "전화번호", example = "01012345678")
        @Pattern(regexp = "^01[0-9]{8,9}$", message = "유효한 전화번호 형식이어야 합니다.")
        String phoneNumber,

        @Schema(description = "인증번호", example = "123456")
        @Pattern(regexp = "^[0-9]{6}$", message = "6자리 숫자여야 합니다.")
        String authCode
) {}

