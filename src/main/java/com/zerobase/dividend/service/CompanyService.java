package com.zerobase.dividend.service;

import com.zerobase.dividend.domain.CompanyEntity;
import com.zerobase.dividend.domain.DividendEntity;
import com.zerobase.dividend.dto.CompanyDto;
import com.zerobase.dividend.dto.scrapDto;
import com.zerobase.dividend.error.CustomException;
import com.zerobase.dividend.error.ErrorCode;
import com.zerobase.dividend.repository.CompanyRepository;
import com.zerobase.dividend.repository.DivideneRepository;
import com.zerobase.dividend.scraper.Scraper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CompanyService {
    private final CompanyRepository companyRepository;
    private final Scraper scraper;
    private final DivideneRepository divideneRepository;

    @Transactional
    public CompanyDto addCompany(String ticker) {
        boolean companyExists = companyRepository.existsByTicker(ticker);
        if (companyExists) {
            throw new CustomException(ErrorCode.ALREADY_EXISTED);
        }
        String companyName = scraper.scrapCompanyNameByTicker(ticker);
        scrapDto scrapDto = scraper.scrapByTicker(ticker);

        CompanyEntity companyEntity = companyRepository.save(CompanyEntity.builder()
                .name(companyName)
                .ticker(ticker)
                .build());

        divideneRepository.saveAll(scrapDto.getDividends()
                .stream()
                .map(x -> DividendEntity.
                        builder()
                        .date(x.getDate())
                        .companyId(companyEntity.getId())
                        .dividend(x.getDividend())
                        .build()
                ).collect(Collectors.toList())
        );

        return  CompanyDto.builder()
                .name(companyName)
                .ticker(ticker)
                .build();
    }
}
