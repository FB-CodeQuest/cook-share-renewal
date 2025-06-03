package com.fbcq.backend.global.exception;

public class InvalidAuthCodeException extends BusinessException {
	public InvalidAuthCodeException() {
		super(ErrorCode.INVALID_AUTH_CODE);
	}
}
