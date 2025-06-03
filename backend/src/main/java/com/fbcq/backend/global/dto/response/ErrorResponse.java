package com.fbcq.backend.global.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;
import java.util.List;

@Schema(description = "에러 응답 포맷")
public record ErrorResponse(
        @Schema(description = "에러 코드", example = "A001")
        String code,

        @Schema(description = "에러 메시지", example = "비밀번호가 일치하지 않습니다.")
        String message,

        @Schema(description = "요청 경로", example = "/api/user/login")
        String path,

        @Schema(description = "에러 발생 시각", example = "2025-06-02T13:45:00")
        LocalDateTime timestamp,

        @Schema(description = "필드 검증 실패 목록", example = "[{\"field\": \"email\", \"reason\": \"이메일 형식이 아닙니다.\"}]")
        List<FieldError> errors
) {

    public static ErrorResponse of(String code, String message, String path) {
        return new ErrorResponse(code, message, path, LocalDateTime.now(), null);
    }

    public static ErrorResponse of(String code, String message, String path, List<FieldError> errors) {
        return new ErrorResponse(code, message, path, LocalDateTime.now(), errors);
    }

    public record FieldError(
            @Schema(description = "문제 발생 필드", example = "phoneNumber")
            String field,

            @Schema(description = "사유", example = "형식에 맞지 않습니다.")
            String reason
    ) {
        public static FieldError of(String field, String reason) {
            return new FieldError(field, reason);
        }
    }
}
