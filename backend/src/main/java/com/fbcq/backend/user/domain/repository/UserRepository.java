package com.fbcq.backend.user.domain.repository;

import com.fbcq.backend.user.domain.model.User;

import java.util.Optional;

public interface UserRepository {
    User save(User user);
    Optional<User> findByPhoneNumber(String phoneNumber);

    boolean existsByNickname(String nickname);
}

