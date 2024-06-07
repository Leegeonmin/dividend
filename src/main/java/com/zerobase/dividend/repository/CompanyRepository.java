package com.zerobase.dividend.repository;

import com.zerobase.dividend.domain.CompanyEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyRepository extends JpaRepository<CompanyEntity, Long> {
    boolean existsByTicker(String ticker);
    Page<CompanyEntity> findByNameStartingWithIgnoreCase(String keyword, Pageable pageable);
}
