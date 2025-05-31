package com.fbcq.backend.user.presentation.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record SignUpRequest(
        @NotBlank(message = "닉네임은 필수입니다.")
        String nickname,

        @Pattern(regexp = "^01[0-9]{8,9}$", message = "유효한 전화번호 형식이어야 합니다.")
        String phoneNumber,

        @Size(min = 8, message = "비밀번호는 최소 8자 이상이어야 합니다.")
        String password,

        @NotBlank(message = "주소는 필수입니다.")
        String address,

        @NotBlank(message = "동 주소는 필수입니다.")
        String shortAddress
) {}
