package com.zerobase.dividend.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "company")
public class CompanyEntity {
    @Id @GeneratedValue
    private Long id;

    @Column(unique = true)
    private String ticker;
    private String name;

}
