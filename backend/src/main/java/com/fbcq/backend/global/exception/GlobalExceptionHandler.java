package com.fbcq.backend.global.exception;

import com.fbcq.backend.global.dto.response.CommonResponse;
// import com.fbcq.backend.global.dto.response.ErrorResponse; // ✅ 확장 시 에러 코드 및 URI 포함 구조로 변경 가능
import jakarta.validation.ConstraintViolationException;
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
     * 잘못된 파라미터 또는 비즈니스 로직 검증 실패에 대한 처리
     * 예: IllegalArgumentException 등 명시적 예외
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<CommonResponse<Void>> handleIllegalArgument(IllegalArgumentException e) {
        return ResponseEntity.badRequest()
                .body(CommonResponse.fail(e.getMessage()));

        // ✅ 확장 예시
        // return ResponseEntity.badRequest()
        //         .body(ErrorResponse.of("INVALID_ARGUMENT", e.getMessage(), request.getRequestURI()));
    }

    /**
     * 시스템에서 발생한 예상치 못한 예외 처리
     * 예: NPE, ClassCastException 등 일반 Exception
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
     * @RequestBody 또는 @ModelAttribute DTO 유효성 검증 실패 처리
     * (javax.validation 기반 - ex. @Valid DTO)
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<CommonResponse<Object>> handleValidation(MethodArgumentNotValidException e) {
        List<String> errors = e.getBindingResult().getFieldErrors().stream()
                .map(error -> String.format("[%s] %s", error.getField(), error.getDefaultMessage()))
                .toList();

        return ResponseEntity.badRequest()
                .body(CommonResponse.fail("유효성 검사 실패", errors));

        // ✅ 확장 예시
        // List<ErrorResponse.FieldError> errorList = ...
        // return ResponseEntity.badRequest()
        //         .body(ErrorResponse.of("VALIDATION_ERROR", "유효성 검사 실패", request.getRequestURI(), errorList));
    }

    /**
     * @RequestParam, @PathVariable 등 메서드 파라미터 유효성 검증 실패 처리
     * (ex. @Size, @Pattern, @Min 등의 제약 조건 위반)
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<CommonResponse<List<String>>> handleConstraintViolation(ConstraintViolationException e) {
        List<String> errors = e.getConstraintViolations().stream()
                .map(v -> String.format("[%s] %s", v.getPropertyPath(), v.getMessage()))
                .toList();

        return ResponseEntity.badRequest()
                .body(CommonResponse.fail("요청 파라미터 유효성 실패", errors));
    }

    /**
     * 커스텀 비즈니스 예외 처리 (ex. 도메인 규칙 위반 등)
     * 예: 인증 실패, 리소스 중복, 권한 없음 등
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
