package com.fbcq.backend.fridge.domain.model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "public_fridge")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Fridge {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer fridgeId;

    private String cityName;

    private String fridgeNumber;

    private String fridgeName;

    private String addressRoad;

    private String addressJibun;

    private Double latitude;

    private Double longitude;

    private LocalDate referenceDate;

    private boolean isActive;

}

