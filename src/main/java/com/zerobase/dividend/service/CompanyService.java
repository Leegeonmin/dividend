package com.zerobase.dividend.service;

import com.zerobase.dividend.domain.CompanyEntity;
import com.zerobase.dividend.domain.DividendEntity;
import com.zerobase.dividend.dto.CompanyDto;
import com.zerobase.dividend.dto.ScrapDto;
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
            throw new CustomException(ErrorCode.COMPANY_ALREADY_EXISTED);
        }
        String companyName = scraper.scrapCompanyNameByTicker(ticker);
        ScrapDto scrapDto = scraper.scrapByTicker(ticker);

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

    private void addDividends(ScrapDto scrapDto, CompanyEntity companyEntity) {
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
        Page<CompanyEntity> companyEntities = companyRepository.findByNameStartingWithIgnoreCase(keyword, PageRequest.of(0, 10));
        return companyEntities.stream()
                .map(CompanyEntity::getName)
                .collect(Collectors.toList());
    }

    @Transactional
    public void deleteCompany(String ticker) {
        // 회사 조회
        CompanyEntity company = companyRepository.findByTicker(ticker)
                .orElseThrow(() -> new CustomException(ErrorCode.INVALID_TICKER));
        // 회사정보 삭제
        int deleteSig = companyRepository.deleteByTicker(ticker);
        if(deleteSig == 0){
            throw new CustomException(ErrorCode.COMPANY_NOT_FOUND);
        }
        // dividend 정보 삭제
        divideneRepository.deleteByCompanyId(company.getId());

    }
}
