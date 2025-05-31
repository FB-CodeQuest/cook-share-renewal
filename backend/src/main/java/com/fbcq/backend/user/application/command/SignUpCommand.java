package com.fbcq.backend.user.application.command;

public record SignUpCommand(
        String nickname,
        String phoneNumber,
        String password,
        String address,
        String shortAddress
) {}