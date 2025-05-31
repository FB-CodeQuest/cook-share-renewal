package com.fbcq.backend.user.application.command;

import com.fbcq.backend.user.domain.model.User;
import com.fbcq.backend.user.domain.repository.UserRepository;
import com.fbcq.backend.user.domain.service.PasswordEncoder;
import com.fbcq.backend.user.presentation.dto.response.UserResponse;
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
        userRepository.findByPhoneNumber(command.phoneNumber())
                .ifPresent(user -> {
                    throw new IllegalArgumentException("이미 등록된 전화번호입니다.");
                });

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
    public UserResponse login(LoginCommand command) {
        User user = userRepository.findByPhoneNumber(command.phoneNumber())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 전화번호입니다."));

        if (!user.isPasswordMatch(command.password(), passwordEncoder)) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        return new UserResponse(user.getUserId(), user.getNickname());
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

