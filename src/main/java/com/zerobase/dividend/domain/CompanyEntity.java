package com.zerobase.dividend.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity(name = "company")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class CompanyEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String ticker;
    private String name;

}
