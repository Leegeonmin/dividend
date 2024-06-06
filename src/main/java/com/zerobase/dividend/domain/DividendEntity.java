package com.zerobase.dividend.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity(name = "dividend")
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class DividendEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private Long companyId;
    private LocalDateTime date;
    private String dividend;
}
