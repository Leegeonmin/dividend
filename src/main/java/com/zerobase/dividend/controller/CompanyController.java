package com.zerobase.dividend.controller;

import com.zerobase.dividend.dto.AddCompanyDto;
import com.zerobase.dividend.dto.CompanyDto;
import com.zerobase.dividend.dto.GetCompanies;
import com.zerobase.dividend.service.CompanyService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequiredArgsConstructor
@RequestMapping("/company")
public class CompanyController {

    private final CompanyService companyService;
    @PostMapping
    public ResponseEntity<AddCompanyDto.Response> addCompany(@RequestBody @Valid AddCompanyDto.Request request){
        CompanyDto companyDto = companyService.addCompany(request.getTicker());

        return ResponseEntity.ok().body(AddCompanyDto.Response
                .builder()
                .name(companyDto.getName())
                .ticker(companyDto.getTicker())
                .build());
    }

    @GetMapping
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
        companyService.deleteCompany(ticker);

        return ResponseEntity.ok().body(ticker + " delete Success");
    }
}
