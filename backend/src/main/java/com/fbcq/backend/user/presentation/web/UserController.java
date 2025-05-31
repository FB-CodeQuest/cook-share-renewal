package com.fbcq.backend.user.presentation.web;

import com.fbcq.backend.user.application.command.LoginCommand;
import com.fbcq.backend.user.application.command.SignUpCommand;
import com.fbcq.backend.user.application.command.UserCommandService;
import com.fbcq.backend.user.presentation.dto.request.LoginRequest;
import com.fbcq.backend.user.presentation.dto.request.SignUpRequest;
import com.fbcq.backend.user.presentation.dto.response.CommonResponse;
import com.fbcq.backend.user.presentation.dto.response.UserResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "User", description = "회원 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    private final UserCommandService userCommandService;

    @Operation(summary = "회원가입")
    @PostMapping("/signup")
    public ResponseEntity<CommonResponse<Void>> signUp(@Valid @RequestBody SignUpRequest req) {
        userCommandService.signUp(new SignUpCommand(
                req.nickname(), req.phoneNumber(), req.password(), req.address(), req.shortAddress()
        ));
        return ResponseEntity.ok(CommonResponse.success(null));
    }

    @Operation(summary = "로그인")
    @PostMapping("/login")
    public ResponseEntity<CommonResponse<UserResponse>> login(@Valid @RequestBody LoginRequest req) {
        var res = userCommandService.login(new LoginCommand(req.phoneNumber(), req.password()));
        return ResponseEntity.ok(CommonResponse.success(res));
    }

    @Operation(summary = "전화번호 중복 체크")
    @GetMapping("/check-phone")
    public ResponseEntity<CommonResponse<Boolean>> checkPhoneNumber(@RequestParam String phoneNumber) {
        boolean isDuplicated = userCommandService.isPhoneNumberDuplicated(phoneNumber);
        return ResponseEntity.ok(CommonResponse.success(isDuplicated));
    }

    @Operation(summary = "닉네임 중복 체크")
    @GetMapping("/check-nickname")
    public ResponseEntity<CommonResponse<Boolean>> checkNickname(@RequestParam String nickname) {
        boolean isDuplicated = userCommandService.isNicknameDuplicated(nickname);
        return ResponseEntity.ok(CommonResponse.success(isDuplicated));
    }

}

