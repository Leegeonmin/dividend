package com.zerobase.dividend.controller;

import com.zerobase.dividend.dto.FinanceDto;
import com.zerobase.dividend.service.FinanceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/finance")
@RequiredArgsConstructor
@Slf4j
public class FinanceController {
    private final FinanceService financeService;
    @GetMapping("/dividend/{companyName}")

    public ResponseEntity<FinanceDto> getCompanyInfoandDividensByCompanyName(
            @PathVariable String companyName
    ){
        log.info("FinanceController -> getCompanyInfoandDividensByCompanyName");
        FinanceDto companyInfoandDividends = financeService.getCompanyInfoandDividends(companyName);
        return ResponseEntity.ok().body(companyInfoandDividends);
    }

}
