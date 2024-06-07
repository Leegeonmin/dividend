package com.zerobase.dividend.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class AddCompanyDto {
    @Getter
    public static class Request{
        @NotBlank
        private String ticker;
    }
    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Response{
        private String name;
        private String ticker;
    }
}
