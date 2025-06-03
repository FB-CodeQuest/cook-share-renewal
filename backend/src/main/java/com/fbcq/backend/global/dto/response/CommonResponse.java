package com.fbcq.backend.global.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "공통 응답 포맷")
public record CommonResponse<T>(
        @Schema(description = "성공 여부", example = "true")
        boolean success,

        @Schema(description = "메시지", example = "요청 성공")
        String message,

        @Schema(description = "응답 데이터")
        T data
) {
    public static <T> CommonResponse<T> success(T data) {
        return new CommonResponse<>(true, "요청 성공", data);
    }

    public static <T> CommonResponse<T> success(T data, String message) {
        return new CommonResponse<>(true, message, data);
    }

    public static <T> CommonResponse<T> fail(String message) {
        return new CommonResponse<>(false, message, null);
    }

    public static <T> CommonResponse<T> fail(String message, T data) {
        return new CommonResponse<>(false, message, data);
    }
}

