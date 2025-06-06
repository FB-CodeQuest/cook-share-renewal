package com.fbcq.backend.global.exception;

import org.springframework.http.HttpStatus;

public enum ErrorCode {
    NICKNAME_DUPLICATED("D001", HttpStatus.BAD_REQUEST, "이미 있는 닉네임입니다!."),
    PHONE_DUPLICATED("D002", HttpStatus.BAD_REQUEST, "이미 있는 번호입니다."),
    USER_NOT_FOUND("N001", HttpStatus.NOT_FOUND, "사용자를 찾을 수 없습니다."),
    INVALID_PASSWORD("A001", HttpStatus.UNAUTHORIZED, "비밀번호가 일치하지 않습니다."),
    INVALID_AUTH_CODE("A002", HttpStatus.UNAUTHORIZED, "인증번호가 일치하지 않습니다."),
    AUTH_CODE_ALREADY_SENT("A003", HttpStatus.TOO_MANY_REQUESTS, "잠시 후 다시 시도해주세요."),
    INVALID_REFRESH_TOKEN("A004", HttpStatus.UNAUTHORIZED, "유효하지 않은 리프레시 토큰입니다."),
    REFRESH_TOKEN_EXPIRED("A005", HttpStatus.UNAUTHORIZED, "리프레시 토큰이 만료되었습니다."),
    TWILIO_RATE_LIMIT("A006", HttpStatus.TOO_MANY_REQUESTS, "Twilio 전송 제한이 초과되었습니다.");

    private final String code;
    private final HttpStatus status;
    private final String message;

    ErrorCode(String code, HttpStatus status, String message) {
        this.code = code;
        this.status = status;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}

