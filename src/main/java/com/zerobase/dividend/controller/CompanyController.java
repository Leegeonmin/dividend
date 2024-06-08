package com.zerobase.dividend.controller;

import com.zerobase.dividend.dto.AddCompanyDto;
import com.zerobase.dividend.dto.CompanyDto;
import com.zerobase.dividend.dto.GetCompanies;
import com.zerobase.dividend.service.CacheService;
import com.zerobase.dividend.service.CompanyService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequiredArgsConstructor
@RequestMapping("/company")
public class CompanyController {

    private final CompanyService companyService;
    private final CacheService cacheService;
    @PostMapping
    @PreAuthorize("hasRole('WRITE')")

    public ResponseEntity<AddCompanyDto.Response> addCompany(@RequestBody @Valid AddCompanyDto.Request request){
        CompanyDto companyDto = companyService.addCompany(request.getTicker());

        return ResponseEntity.ok().body(AddCompanyDto.Response
                .builder()
                .name(companyDto.getName())
                .ticker(companyDto.getTicker())
                .build());
    }

    @GetMapping
    @PreAuthorize("hasRole('READ')")

    public ResponseEntity<Page<GetCompanies>> getCompany(final Pageable pageable){
        Page<CompanyDto> companyDtos = companyService.getAll(pageable);

        return ResponseEntity.ok().body(new PageImpl<>( companyDtos.stream().map(
                x-> GetCompanies
                        .builder()
                        .name(x.getName())
                        .ticker(x.getTicker())
                        .build()
        ).collect(Collectors.toList())));
    }

    @GetMapping("/autocomplete")
    public ResponseEntity<List<String>> getCompanyNamesByAutocomplete(@RequestParam(required = false) String keyword){
        List<String> companyNamesByPrefix = companyService.getCompanyNamesByPrefix(keyword);
        return ResponseEntity.ok().body(companyNamesByPrefix);
    }

    @DeleteMapping("/{ticker}")
    public ResponseEntity<?> deleteCompany(@PathVariable String ticker){
        String companyName = companyService.deleteCompany(ticker);
        cacheService.clearFinanceCache(companyName);
        return ResponseEntity.ok().body(ticker + " delete Success");
    }
}
