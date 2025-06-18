package com.fbcq.backend.fridge.presentation.dto;

import com.fbcq.backend.fridge.domain.model.Fridge;
import lombok.Builder;

@Builder
public record FridgeResponse(
        Integer fridgeId,
        String name,
        String address,
        Double latitude,
        Double longitude
) {
    public static FridgeResponse from(Fridge fridge) {
        return FridgeResponse.builder()
                .fridgeId(fridge.getFridgeId())
                .name(fridge.getFridgeName())
                .address(fridge.getAddressRoad())
                .latitude(fridge.getLatitude())
                .longitude(fridge.getLongitude())
                .build();
    }
}
