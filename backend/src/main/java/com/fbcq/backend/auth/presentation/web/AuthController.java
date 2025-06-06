package com.fbcq.backend.auth.presentation.web;

import com.fbcq.backend.auth.application.command.AuthCommandService;
import com.fbcq.backend.auth.presentation.dto.request.LoginRequest;
import com.fbcq.backend.auth.presentation.dto.response.TokenResponse;
import com.fbcq.backend.global.dto.response.CommonResponse;
import com.fbcq.backend.global.exception.ErrorCode;
import com.fbcq.backend.global.exception.UnauthorizedException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Optional;

@Tag(name = "Auth", description = "인증 관련 API")
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthCommandService authCommandService;

    @Operation(
            summary = "로그인",
            description = "전화번호와 비밀번호로 로그인하고 액세스/리프레시 토큰을 발급받습니다.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "로그인 성공",
                            content = @Content(schema = @Schema(implementation = TokenResponse.class))),
                    @ApiResponse(responseCode = "401", description = "인증 실패",
                            content = @Content(schema = @Schema(implementation = CommonResponse.class))),
                    @ApiResponse(responseCode = "500", description = "서버 내부 오류",
                            content = @Content(schema = @Schema(implementation = CommonResponse.class)))
            }
    )

    @PostMapping("/login")
    public ResponseEntity<CommonResponse<TokenResponse>> login(
            @Valid @RequestBody LoginRequest request,
            HttpServletResponse response
    ) {
        TokenResponse tokens = authCommandService.login(request.phoneNumber(), request.password());

        // RefreshToken은 HttpOnly + Secure 쿠키로 응답
        Cookie refreshTokenCookie = new Cookie("refreshToken", tokens.refreshToken());
        refreshTokenCookie.setHttpOnly(true);
        refreshTokenCookie.setSecure(true); // https 환경
        refreshTokenCookie.setPath("/api/auth/reissue");
        refreshTokenCookie.setMaxAge(60 * 60 * 24 * 7); // 7일

        response.addCookie(refreshTokenCookie);

        return ResponseEntity.ok(CommonResponse.success(new TokenResponse(tokens.accessToken(), tokens.refreshToken())));
    }

    @Operation(
            summary = "토큰 재발급",
            description = "리프레시 토큰을 이용해 새로운 액세스/리프레시 토큰을 발급받습니다.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "토큰 재발급 성공",
                            content = @Content(schema = @Schema(implementation = TokenResponse.class))),
                    @ApiResponse(responseCode = "401", description = "리프레시 토큰이 유효하지 않음",
                            content = @Content(schema = @Schema(implementation = CommonResponse.class))),
                    @ApiResponse(responseCode = "500", description = "서버 내부 오류",
                            content = @Content(schema = @Schema(implementation = CommonResponse.class)))
            }
    )
    @PostMapping("/reissue")
    public ResponseEntity<CommonResponse<TokenResponse>> reissue(
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        // 쿠키에서 refreshToken 추출
        String refreshToken = Arrays.stream(Optional.ofNullable(request.getCookies()).orElse(new Cookie[0]))
                .filter(c -> "refreshToken".equals(c.getName()))
                .findFirst()
                .map(Cookie::getValue)
                .orElseThrow(() -> new UnauthorizedException(ErrorCode.INVALID_REFRESH_TOKEN));

        TokenResponse tokens = authCommandService.reissue(refreshToken);

        Cookie newCookie = new Cookie("refreshToken", tokens.refreshToken());
        newCookie.setHttpOnly(true);
        newCookie.setSecure(true);
        newCookie.setPath("/api/auth/reissue");
        newCookie.setMaxAge(60 * 60 * 24 * 7);
        response.addCookie(newCookie);
        return ResponseEntity.ok(CommonResponse.success(new TokenResponse(tokens.accessToken(), tokens.refreshToken())));
    }


    @Operation(
            summary = "로그아웃",
            description = "리프레시 토큰을 무효화하여 로그아웃 처리합니다.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "로그아웃 성공",
                            content = @Content(schema = @Schema(implementation = CommonResponse.class))),
                    @ApiResponse(responseCode = "401", description = "토큰이 유효하지 않거나 이미 만료됨",
                            content = @Content(schema = @Schema(implementation = CommonResponse.class))),
                    @ApiResponse(responseCode = "500", description = "서버 내부 오류",
                            content = @Content(schema = @Schema(implementation = CommonResponse.class)))
            }
    )
    @PostMapping("/logout")
    public ResponseEntity<CommonResponse<Void>> logout(
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        String refreshToken = Arrays.stream(Optional.ofNullable(request.getCookies()).orElse(new Cookie[0]))
                .filter(c -> "refreshToken".equals(c.getName()))
                .findFirst()
                .map(Cookie::getValue)
                .orElseThrow(() -> new UnauthorizedException(ErrorCode.INVALID_REFRESH_TOKEN));
        authCommandService.logout(refreshToken);

        // 쿠키 삭제
        Cookie deleteCookie = new Cookie("refreshToken", null);
        deleteCookie.setHttpOnly(true);
        deleteCookie.setSecure(true);
        deleteCookie.setPath("/api/auth/reissue");
        deleteCookie.setMaxAge(0); // 즉시 만료
        response.addCookie(deleteCookie);

        return ResponseEntity.ok(CommonResponse.success(null, "로그아웃 되었습니다."));
    }

}
