package com.fbcq.backend.sms.infrastructure;

import com.fbcq.backend.sms.application.SmsClient;
import com.fbcq.backend.sms.domain.SmsMessage;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import com.twilio.Twilio;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TwilioSmsClient implements SmsClient {

    @Value("${twilio.account-sid}")
    private String accountSid;

    @Value("${twilio.auth-token}")
    private String authToken;

    @Value("${twilio.sender-phone}")
    private String senderPhone;

    @PostConstruct
    public void init() {
        Twilio.init(accountSid, authToken);
    }

    @Override
    public void send(SmsMessage message) {
        String to = PhoneNumberUtil.toE164Format(message.to());

        if (to.equals(senderPhone)) {
            throw new IllegalArgumentException("수신 번호와 발신 번호가 동일할 수 없습니다.");
        }

        Message.creator(
                new PhoneNumber(to),
                new PhoneNumber(senderPhone),
                message.content()
        ).create();
    }
}

