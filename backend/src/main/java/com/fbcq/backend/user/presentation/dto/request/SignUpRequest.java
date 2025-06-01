package com.fbcq.backend.user.presentation.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record SignUpRequest(
        @NotBlank(message = "닉네임은 필수입니다.")
        @Size(min = 2, max = 12, message = "닉네임은 2자 이상 12자 이하이어야 합니다.")
        String nickname,

        @NotBlank(message = "전화번호는 필수입니다.")
        @Size(min = 10, max = 11, message = "전화번호는 10~11자리여야 합니다.")
        @Pattern(regexp = "^01[0-9]{8,9}$", message = "전화번호는 숫자만 포함해야 합니다.")
        String phoneNumber,

        @NotBlank(message = "비밀번호는 필수입니다.")
        @Size(min = 8, max = 20, message = "비밀번호는 최소 8자 이상, 최대 20자 이하이어야 합니다.")
        String password,

        @NotBlank(message = "주소는 필수입니다.")
        String address,

        @NotBlank(message = "동 주소는 필수입니다.")
        String shortAddress
) {}

