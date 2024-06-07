package com.zerobase.dividend.service;

import com.zerobase.dividend.domain.CompanyEntity;
import com.zerobase.dividend.domain.DividendEntity;
import com.zerobase.dividend.dto.FinanceDto;
import com.zerobase.dividend.error.CustomException;
import com.zerobase.dividend.error.ErrorCode;
import com.zerobase.dividend.repository.CompanyRepository;
import com.zerobase.dividend.repository.DivideneRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FinanceService {
    private final CompanyRepository companyRepository;
    private final DivideneRepository divideneRepository;

    public FinanceDto getCompanyInfoandDividends(String companyName) {
        CompanyEntity companyEntity = companyRepository.findByName(companyName)
                .orElseThrow(() -> new CustomException(ErrorCode.COMPANY_NOT_FOUND));
        List<DividendEntity> dividendEntityList = divideneRepository.findAllByCompanyId(companyEntity.getId());

        return FinanceDto.builder()
                .companyTicker(companyEntity.getTicker())
                .companyName(companyEntity.getName())
                .dividends(
                        dividendEntityList.stream().map(
                                x -> FinanceDto.Dividend.builder()
                                        .date(x.getDate())
                                        .dividend(x.getDividend())
                                        .build()
                        ).collect(Collectors.toList())
                )
                .build();
    }
}
