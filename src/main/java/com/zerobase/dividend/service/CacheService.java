package com.zerobase.dividend.service;

import com.zerobase.dividend.constants.CacheKey;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class CacheService {
    private final CacheManager redisCacheManager;
    public void clearFinanceCache(String companyName){
        redisCacheManager.getCache(CacheKey.KEY_FINANCE).evict(companyName);
    }
}
