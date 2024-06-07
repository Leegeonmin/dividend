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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
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

        addDividends(scrapDto, companyEntity);

        return CompanyDto.builder()
                .name(companyName)
                .ticker(ticker)
                .build();
    }

    private void addDividends(scrapDto scrapDto, CompanyEntity companyEntity) {
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
    }

    public Page<CompanyDto> getAll(Pageable pageable) {
        Page<CompanyEntity> companyEntities = companyRepository.findAll(pageable);

        return new PageImpl<>(companyEntities.stream()
                .map(x ->
                        CompanyDto.builder()
                                .ticker(x.getTicker())
                                .name(x.getName())
                                .build()

                )
                .collect(Collectors.toList()));
    }

    public List<String> getCompanyNamesByPrefix(String keyword) {
        Page<CompanyEntity> companyEntities = companyRepository.findByNameStartingWithIgnoreCase(keyword, PageRequest.of(0,10));
        return companyEntities.stream()
                .map(x -> x.getName())
                .collect(Collectors.toList());
    }
}
