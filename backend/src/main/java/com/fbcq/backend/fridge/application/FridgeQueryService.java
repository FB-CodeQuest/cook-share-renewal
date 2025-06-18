package com.fbcq.backend.fridge.application;

import com.fbcq.backend.fridge.domain.repository.FridgeRepository;
import com.fbcq.backend.fridge.presentation.dto.FridgeResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FridgeQueryService {

    private final FridgeRepository fridgeRepository;

    public List<FridgeResponse> getAll() {
        return fridgeRepository.findAll().stream()
                .map(FridgeResponse::from)
                .toList();
    }

    public List<FridgeResponse> getByKeyword(String keyword) {
        return fridgeRepository.findByAddressRoadContaining(keyword).stream()
                .map(FridgeResponse::from)
                .toList();
    }
}

