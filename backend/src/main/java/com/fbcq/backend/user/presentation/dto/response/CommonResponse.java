package com.fbcq.backend.user.presentation.dto.response;

public record CommonResponse<T>(
        boolean success,
        String message,
        T data
) {
    public static <T> CommonResponse<T> success(T data) {
        return new CommonResponse<>(true, "OK", data);
    }

    public static <T> CommonResponse<T> fail(String message) {
        return new CommonResponse<>(false, message, null);
    }
}
