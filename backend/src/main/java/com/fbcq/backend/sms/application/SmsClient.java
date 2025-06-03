package com.fbcq.backend.sms.application;

import com.fbcq.backend.sms.domain.SmsMessage;

public interface SmsClient {
    void send(SmsMessage message);
}
