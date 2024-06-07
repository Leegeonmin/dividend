package com.zerobase.dividend.controller;

import com.zerobase.dividend.dto.FinanceDto;
import com.zerobase.dividend.service.FinanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/finance")
@RequiredArgsConstructor
public class FinanceController {
    private final FinanceService financeService;
    @GetMapping("/dividend/{companyName}")

    public ResponseEntity<FinanceDto> getCompanyInfoandDividensByCompanyName(
            @PathVariable String companyName
    ){
        FinanceDto companyInfoandDividends = financeService.getCompanyInfoandDividends(companyName);
        return ResponseEntity.ok().body(companyInfoandDividends);
    }

}
