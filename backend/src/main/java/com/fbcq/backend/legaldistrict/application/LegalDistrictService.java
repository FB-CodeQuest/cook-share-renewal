package com.fbcq.backend.legaldistrict.application;

import com.fbcq.backend.legaldistrict.domain.LatLng;
import com.fbcq.backend.legaldistrict.infrastructure.KakaoMapClient;
import com.fbcq.backend.legaldistrict.domain.LegalDistrict;
import com.fbcq.backend.legaldistrict.domain.LegalDistrictRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LegalDistrictService {

    private final LegalDistrictRepository repository;
    private final KakaoMapClient kakao;

    public int updateMissingCoordinates() {
        List<LegalDistrict> targets = repository.findByLatitudeIsNullAndLongitudeIsNull();
        int count = 0;

        for (LegalDistrict district : targets) {
            String query = district.getCity() + " " + district.getDistrict() + " " + district.getLegalDong();
            if (district.getLatitude() != null && district.getLongitude() != null) continue;
            Optional<LatLng> optional = kakao.getCoordinates(query);
            if (optional.isPresent()) {
                LatLng coords = optional.get();
                district.setLatitude(coords.latitude());
                district.setLongitude(coords.longitude());
                repository.save(district);
                count++;
            }
        }

        return count;
    }
}
