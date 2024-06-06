package com.zerobase.dividend.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString
public class scrapDto {
    private List<Dividend> dividends;

    @ToString
    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    public static class Dividend{
        private LocalDateTime date;
        private String dividend;
    }
}
