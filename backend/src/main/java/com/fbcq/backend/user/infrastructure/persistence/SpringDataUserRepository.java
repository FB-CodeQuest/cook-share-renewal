package com.fbcq.backend.user.infrastructure.persistence;

import com.fbcq.backend.user.domain.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SpringDataUserRepository extends JpaRepository<User, Long> {
    Optional<User> findByPhoneNumber(String phoneNumber);
    boolean existsByNickname(String nickname);
}
