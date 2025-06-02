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

    @Value("${TWILIO_ACCOUNT_SID}")
    private String accountSid;

    @Value("${TWILIO_AUTH_TOKEN}")
    private String authToken;

    @Value("${TWILIO_SENDER_PHONE}")
    private String senderPhone;

    @PostConstruct
    public void init() {
        Twilio.init(accountSid, authToken);
    }

    @Override
    public void send(SmsMessage message) {
        Message.creator(
                new PhoneNumber(message.to()),
                new PhoneNumber(senderPhone),
                message.content()
        ).create();
    }
}

