package com.zerobase.dividend.controller;

import com.zerobase.dividend.dto.CompanyDto;
import com.zerobase.dividend.dto.addCompanyDto;
import com.zerobase.dividend.service.CompanyService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/company")
public class CompanyController {

    private final CompanyService companyService;
    @PostMapping
    public addCompanyDto.Response addCompany(@RequestBody @Valid addCompanyDto.Request request){
        CompanyDto companyDto = companyService.addCompany(request.getTicker());

        return addCompanyDto.Response
                .builder()
                .name(companyDto.getName())
                .ticker(companyDto.getTicker())
                .build();
    }
}
