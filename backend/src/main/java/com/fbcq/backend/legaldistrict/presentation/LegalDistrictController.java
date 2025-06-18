package com.fbcq.backend.legaldistrict.presentation;

import com.fbcq.backend.legaldistrict.application.LegalDistrictService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/districts")
@Tag(name = "법정동 API", description = "Kakao Map 기반 좌표 입력")
public class LegalDistrictController {

    private final LegalDistrictService legalDistrictService;

    @Operation(summary = "좌표 누락 항목 Kakao API로 채우기", description = "위도/경도가 비어 있는 법정동 데이터를 채웁니다.")
    @PostMapping("/fill-coordinates")
    public ResponseEntity<String> fillMissingCoordinates() {
        int updated = legalDistrictService.updateMissingCoordinates();
        return ResponseEntity.ok("업데이트 완료: " + updated + "건");
    }
}
