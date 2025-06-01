package com.fbcq.backend.user.domain.model;

import com.fbcq.backend.user.domain.service.PasswordEncoder;
import jakarta.persistence.Id;
import lombok.Getter;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "users") // 복수형 사용 예약어 방지
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    private String nickname;

    private String phoneNumber;

    private String password;

    private String address;

    private String shortAddress;

    private String refreshToken;

    // 예: DDD 관점에서 행위 메서드 포함 가능
    public void updateRefreshToken(String newToken) {
        this.refreshToken = newToken;
    }

    public boolean isPasswordMatch(String password, PasswordEncoder passwordEncoder) {
        return passwordEncoder.matches(password, this.password);
    }
}

