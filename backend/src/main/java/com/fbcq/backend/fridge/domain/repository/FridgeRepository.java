package com.fbcq.backend.fridge.domain.repository;

import com.fbcq.backend.fridge.domain.model.Fridge;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FridgeRepository extends JpaRepository<Fridge, Integer> {
    List<Fridge> findByAddressRoadContaining(String keyword);
}
