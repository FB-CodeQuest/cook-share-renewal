package com.fbcq.backend.fridge.presentation.web;

import com.fbcq.backend.fridge.application.FridgeQueryService;
import com.fbcq.backend.fridge.presentation.dto.FridgeResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Size;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "Fridge", description = "공유 냉장고 API")
@RestController
@RequestMapping("/api/fridges")
@RequiredArgsConstructor
public class FridgeController {

    private final FridgeQueryService fridgeQueryService;

    @Operation(
            summary = "공유 냉장고 목록 조회",
            description = "전체 냉장고 리스트 또는 키워드에 해당하는 냉장고 리스트를 조회합니다.",
            parameters = {
                    @Parameter(name = "keyword", description = "검색 키워드 (동 이름 등)", required = false)
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "냉장고 리스트 조회 성공",
                            content = @Content(schema = @Schema(implementation = FridgeResponse.class))),
                    @ApiResponse(responseCode = "500", description = "서버 내부 오류",
                            content = @Content)
            }
    )
    @Validated
    @GetMapping
    public ResponseEntity<List<FridgeResponse>> getFridges(
            @RequestParam(required = false)
            @Size(max = 30, message = "검색어는 최대 30자까지 입력 가능합니다.")
            String keyword
    ) {
        List<FridgeResponse> result = (keyword == null || keyword.isBlank())
                ? fridgeQueryService.getAll()
                : fridgeQueryService.getByKeyword(keyword);
        return ResponseEntity.ok(result);
    }
}

