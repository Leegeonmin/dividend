package com.zerobase.dividend.repository;

import com.zerobase.dividend.domain.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<MemberEntity, Long> {
    boolean existsByUsername(String name);
    Optional<MemberEntity> findByUsername(String username);
}
