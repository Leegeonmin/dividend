package com.zerobase.dividend.repository;

import com.zerobase.dividend.domain.DividendEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DivideneRepository extends JpaRepository<DividendEntity, Long> {
    List<DividendEntity> findAllByCompanyId(Long companyId);
    void deleteByCompanyId(Long index);
}
