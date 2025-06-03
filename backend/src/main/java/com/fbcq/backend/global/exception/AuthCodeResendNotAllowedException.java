package com.fbcq.backend.global.exception;

public class AuthCodeResendNotAllowedException extends BusinessException {
    public AuthCodeResendNotAllowedException() {
        super(ErrorCode.AUTH_CODE_ALREADY_SENT);
    }
}
