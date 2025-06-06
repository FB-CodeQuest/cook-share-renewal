package com.fbcq.backend.user.application.command;

import com.fbcq.backend.global.exception.BusinessException;
import com.fbcq.backend.global.exception.ErrorCode;
import com.fbcq.backend.user.domain.model.User;
import com.fbcq.backend.user.domain.repository.UserRepository;
import com.fbcq.backend.user.domain.service.PasswordEncoder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserCommandService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void signUp(SignUpCommand command) {
        if (userRepository.existsByNickname(command.nickname())) {
            throw new BusinessException(ErrorCode.NICKNAME_DUPLICATED);
        }

        if (userRepository.findByPhoneNumber(command.phoneNumber()).isPresent()) {
            throw new BusinessException(ErrorCode.PHONE_DUPLICATED);
        }

        String encodedPw = passwordEncoder.encode(command.password());
        User user = User.builder()
                .nickname(command.nickname())
                .phoneNumber(command.phoneNumber())
                .password(encodedPw)
                .address(command.address())
                .shortAddress(command.shortAddress())
                .build();

        userRepository.save(user);
    }

    @Transactional(readOnly = true)
    public User authenticate(String phoneNumber, String rawPassword) {
        User user = userRepository.findByPhoneNumber(phoneNumber)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 전화번호입니다."));

        if (!user.isPasswordMatch(rawPassword, passwordEncoder)) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        return user;
    }

    @Transactional(readOnly = true)
    public boolean isPhoneNumberDuplicated(String phoneNumber) {
        return userRepository.findByPhoneNumber(phoneNumber).isPresent();
    }

    @Transactional(readOnly = true)
    public boolean isNicknameDuplicated(String nickname) {
        return userRepository.existsByNickname(nickname);
    }

}

