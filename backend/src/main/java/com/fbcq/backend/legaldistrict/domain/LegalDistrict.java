package com.fbcq.backend.legaldistrict.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name="legal_districts")
public class LegalDistrict {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String city;
    private String district;
    private String town;
    private String legalDong;
    private Double latitude;
    private Double longitude;
    private LocalDate baseDate;
}
