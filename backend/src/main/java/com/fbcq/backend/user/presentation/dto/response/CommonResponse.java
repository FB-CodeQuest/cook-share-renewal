package com.fbcq.backend.user.presentation.dto.response;

public record CommonResponse<T>(
        boolean success,
        String message,
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

