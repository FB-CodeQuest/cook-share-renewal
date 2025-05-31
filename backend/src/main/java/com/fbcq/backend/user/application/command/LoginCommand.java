package com.fbcq.backend.user.application.command;

public record LoginCommand(
        String phoneNumber,
        String password
) {}
