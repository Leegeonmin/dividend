package com.zerobase.dividend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FinanceDto {
    private String companyName;
    private String companyTicker;

    private List<Dividend> dividends;

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static  class Dividend{
        private LocalDateTime date;
        private String dividend;
    }
}
