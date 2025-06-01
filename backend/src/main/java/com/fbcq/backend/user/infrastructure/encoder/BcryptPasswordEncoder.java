package com.fbcq.backend.user.infrastructure.encoder;

import com.fbcq.backend.user.domain.service.PasswordEncoder;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Component;

@Component
public class BcryptPasswordEncoder implements PasswordEncoder {

    private static final int STRENGTH = 10;

    @Override
    public String encode(String raw) {
        return BCrypt.hashpw(raw, BCrypt.gensalt(STRENGTH));
    }

    @Override
    public boolean matches(String raw, String encoded) {
        return BCrypt.checkpw(raw, encoded);
    }
}
