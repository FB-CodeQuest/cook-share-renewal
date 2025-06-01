package com.fbcq.backend.global.exception;

import org.springframework.http.HttpStatus;

public enum ErrorCode {
    NICKNAME_DUPLICATED("D001", HttpStatus.BAD_REQUEST, "이미 등록된 닉네임입니다."),
    PHONE_DUPLICATED("D002", HttpStatus.BAD_REQUEST, "이미 등록된 번호입니다."),
    USER_NOT_FOUND("N001", HttpStatus.NOT_FOUND, "사용자를 찾을 수 없습니다."),
    INVALID_PASSWORD("A001", HttpStatus.UNAUTHORIZED, "비밀번호가 일치하지 않습니다.");

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

