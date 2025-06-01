package com.fbcq.backend.user.presentation.web;

import com.fbcq.backend.user.application.command.LoginCommand;
import com.fbcq.backend.user.application.command.SignUpCommand;
import com.fbcq.backend.user.application.command.UserCommandService;
import com.fbcq.backend.user.presentation.dto.request.LoginRequest;
import com.fbcq.backend.user.presentation.dto.request.SignUpRequest;
import com.fbcq.backend.user.presentation.dto.response.CommonResponse;
import com.fbcq.backend.user.presentation.dto.response.UserResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Tag(name = "User", description = "회원 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
@Validated // ⚠️ @RequestParam 유효성 검사를 위해 필수!
public class UserController {

    private final UserCommandService userCommandService;

    @Operation(
            summary = "회원가입",
            responses = {
                    @ApiResponse(responseCode = "200", description = "회원가입 성공",
                            content = @Content(schema = @Schema(implementation = CommonResponse.class))),
                    @ApiResponse(responseCode = "400", description = "유효성 검사 실패 또는 중복 정보",
                            content = @Content(schema = @Schema(implementation = CommonResponse.class))),
                    @ApiResponse(responseCode = "500", description = "서버 내부 오류",
                            content = @Content(schema = @Schema(implementation = CommonResponse.class)))
            }
    )
    @PostMapping("/signup")
    public ResponseEntity<CommonResponse<Void>> signUp(@Valid @RequestBody SignUpRequest req) {
        userCommandService.signUp(new SignUpCommand(
                req.nickname(), req.phoneNumber(), req.password(), req.address(), req.shortAddress()
        ));
        return ResponseEntity.ok(CommonResponse.success(null));
    }

    @Operation(
            summary = "로그인",
            responses = {
                    @ApiResponse(responseCode = "200", description = "로그인 성공",
                            content = @Content(schema = @Schema(implementation = CommonResponse.class))),
                    @ApiResponse(responseCode = "400", description = "존재하지 않는 전화번호",
                            content = @Content(schema = @Schema(implementation = CommonResponse.class))),
                    @ApiResponse(responseCode = "401", description = "인증 실패",
                            content = @Content(schema = @Schema(implementation = CommonResponse.class)))
            }
    )
    @PostMapping("/login")
    public ResponseEntity<CommonResponse<UserResponse>> login(@Valid @RequestBody LoginRequest req) {
        var res = userCommandService.login(new LoginCommand(req.phoneNumber(), req.password()));
        return ResponseEntity.ok(CommonResponse.success(res));
    }

    @Operation(
            summary = "전화번호 중복 체크",
            responses = {
                    @ApiResponse(responseCode = "200", description = "전화번호 중복 여부 확인",
                            content = @Content(schema = @Schema(implementation = CommonResponse.class)))
            }
    )
    @GetMapping("/check-phone")
    public ResponseEntity<CommonResponse<Boolean>> checkPhoneNumber(
            @RequestParam
            @NotBlank(message = "전화번호는 필수입니다.")
            @Size(min = 10, max = 11, message = "전화번호는 10~11자리여야 합니다.")
            @Pattern(regexp = "^01[0-9]{8,9}$", message = "유효한 전화번호 형식이어야 합니다.")
            String phoneNumber
    ) {
        boolean isDuplicated = userCommandService.isPhoneNumberDuplicated(phoneNumber);
        if (isDuplicated) {
            return ResponseEntity.ok(CommonResponse.fail("이미 등록된 번호입니다."));
        }
        return ResponseEntity.ok(CommonResponse.success(null, "사용 가능한 번호입니다."));
    }

    @Operation(
            summary = "닉네임 중복 체크",
            responses = {
                    @ApiResponse(responseCode = "200", description = "닉네임 중복 여부 확인",
                            content = @Content(schema = @Schema(implementation = CommonResponse.class)))
            }
    )
    @GetMapping("/check-nickname")
    public ResponseEntity<CommonResponse<Boolean>> checkNickname(
            @RequestParam
            @NotBlank(message = "닉네임은 필수입니다.")
            @Size(min = 2, max = 12, message = "닉네임은 2자 이상 12자 이하이어야 합니다.")
            String nickname
    ) {

        boolean isDuplicated = userCommandService.isNicknameDuplicated(nickname);
        if (isDuplicated) {
            return ResponseEntity.ok(CommonResponse.fail("이미 등록된 닉네임입니다."));
        }
        return ResponseEntity.ok(CommonResponse.success(null, "사용 가능한 닉네임입니다."));
    }

}