package com.fbcq.backend.global.exception;

import com.fbcq.backend.global.dto.response.CommonResponse;
// import com.fbcq.backend.global.dto.response.ErrorResponse; // ✅ 향후 확장 시 사용
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 잘못된 파라미터 등 클라이언트 입력 오류 처리
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<CommonResponse<Void>> handleIllegalArgument(IllegalArgumentException e) {
        return ResponseEntity.badRequest()
                .body(CommonResponse.fail(e.getMessage()));

        // ✅ 확장 구조 예시
        // return ResponseEntity.badRequest().body(ErrorResponse.of("INVALID_ARGUMENT", e.getMessage(), request.getRequestURI()));
    }

    /**
     * 예외 누락 대비 시스템 오류 처리
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<CommonResponse<Void>> handleSystemError(Exception e) {
        log.error("System error: {}", e.getMessage(), e);
        return ResponseEntity.internalServerError()
                .body(CommonResponse.fail("예상치 못한 오류가 발생했습니다."));

        // return ResponseEntity.internalServerError()
        //         .body(ErrorResponse.of("INTERNAL_ERROR", "예상치 못한 오류가 발생했습니다.", request.getRequestURI()));
    }

    /**
     * javax.validation 기반 유효성 실패 대응
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<CommonResponse<Object>> handleValidation(MethodArgumentNotValidException e) {
        List<String> errors = e.getBindingResult().getFieldErrors().stream()
                .map(error -> String.format("[%s] %s", error.getField(), error.getDefaultMessage()))
                .toList();

        return ResponseEntity.badRequest()
                .body(CommonResponse.fail("유효성 검사 실패", errors));

        // List<ErrorResponse.FieldError> errorList = ...
        // return ResponseEntity.badRequest().body(ErrorResponse.of("VALIDATION_ERROR", "유효성 검사 실패", request.getRequestURI(), errorList));
    }

    /**
     * 커스텀 비즈니스 예외 처리
     */
    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<CommonResponse<Void>> handleBusinessException(BusinessException e) {
        ErrorCode code = e.getErrorCode();
        log.warn("BusinessException: [{}] {}", code.getCode(), code.getMessage());

        return ResponseEntity
                .status(code.getStatus())
                .body(CommonResponse.fail(code.getMessage()));

        // return ResponseEntity
        //         .status(code.getStatus())
        //         .body(ErrorResponse.of(code.getCode(), code.getMessage(), request.getRequestURI()));
    }
}
