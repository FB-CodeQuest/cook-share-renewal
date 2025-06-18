package com.fbcq.backend.legaldistrict.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LegalDistrictRepository extends JpaRepository<LegalDistrict, Integer> {
    List<LegalDistrict> findByLatitudeIsNullAndLongitudeIsNull();
}

