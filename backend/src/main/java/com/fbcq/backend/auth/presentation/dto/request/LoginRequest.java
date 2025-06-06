package com.fbcq.backend.auth.presentation.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record LoginRequest(
        @NotBlank(message = "전화번호는 필수입니다.")
        @Size(min = 10, max = 11, message = "전화번호는 10~11자리여야 합니다.")
        @Pattern(regexp = "^01[0-9]{8,9}$", message = "전화번호는 숫자만 포함해야 합니다.")
        String phoneNumber,

        @NotBlank(message = "비밀번호는 필수입니다.")
        @Size(min = 8, max = 20, message = "비밀번호는 8~20자 사이여야 합니다.")
        String password
) {}
