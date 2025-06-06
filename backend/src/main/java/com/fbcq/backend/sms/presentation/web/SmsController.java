package com.fbcq.backend.sms.presentation.web;

import com.fbcq.backend.sms.application.SmsSendService;
import com.fbcq.backend.sms.application.SmsVerifyService;
import com.fbcq.backend.sms.presentation.dto.SmsVerifyRequest;
import com.fbcq.backend.global.dto.response.CommonResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Sms", description = "인증 관련 API")
@Validated
@RestController
@RequestMapping("api/sms")
@RequiredArgsConstructor
public class SmsController {
    private final SmsSendService smsSendService;
    private final SmsVerifyService smsVerifyService;

    /*
     * 인증번호 전송
     */
    @Operation(summary = "인증번호 전송")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "인증번호 전송 성공",
                    content = @Content(schema = @Schema(implementation = CommonResponse.class))),
            @ApiResponse(responseCode = "400", description = "이미 가입된 번호 or 형식 오류",
                    content = @Content(schema = @Schema(implementation = CommonResponse.class))),
            @ApiResponse(responseCode = "429", description = "3분 내 중복 요청 제한",
                    content = @Content(schema = @Schema(implementation = CommonResponse.class))),
            @ApiResponse(responseCode = "429", description = "Twilio 자체 전송 제한 (Rate Limit)",
                    content = @Content(schema = @Schema(implementation = CommonResponse.class)))
    })
    @PostMapping("/phone")
    public ResponseEntity<CommonResponse<String>> sendAuthCode(@RequestParam
        @Pattern(regexp = "^01[0-9]{8,9}$", message = "유효한 전화번호 형식이어야 합니다.")
        String phoneNumber) {
        smsSendService.sendAuthCode(phoneNumber);
        return ResponseEntity.ok(CommonResponse.success(null, "인증번호가 전송되었습니다."));
    }

    /*
     * 인증번호 검증
     */
    @Operation(summary = "인증번호 확인")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "인증번호 전송 성공",
                    content = @Content(schema = @Schema(implementation = CommonResponse.class))),
            @ApiResponse(responseCode = "400", description = "이미 가입된 번호 or 형식 오류",
                    content = @Content(schema = @Schema(implementation = CommonResponse.class))),
            @ApiResponse(responseCode = "429", description = "3분 내 중복 요청 제한",
                    content = @Content(schema = @Schema(implementation = CommonResponse.class))),
            @ApiResponse(responseCode = "429", description = "Twilio 자체 전송 제한 (Rate Limit)",
                    content = @Content(schema = @Schema(implementation = CommonResponse.class)))
    })
    @PostMapping("/verify")
    public ResponseEntity<CommonResponse<String>> verifyAuthCode(@Valid @RequestBody SmsVerifyRequest request) {
        smsVerifyService.verify(request.phoneNumber(), request.authCode());
        return ResponseEntity.ok(CommonResponse.success(null, "인증이 완료되었습니다."));
    }
}
