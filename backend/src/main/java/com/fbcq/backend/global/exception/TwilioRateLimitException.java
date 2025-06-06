package com.fbcq.backend.global.exception;

import com.fbcq.backend.global.exception.BusinessException;
import com.fbcq.backend.global.exception.ErrorCode;

public class TwilioRateLimitException extends BusinessException {
    public TwilioRateLimitException() {
        super(ErrorCode.TWILIO_RATE_LIMIT);
    }
}

