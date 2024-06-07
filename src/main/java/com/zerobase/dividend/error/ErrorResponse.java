package com.zerobase.dividend.error;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Builder
@RequiredArgsConstructor
@AllArgsConstructor
@Getter
public class ErrorResponse {
    private final ErrorCode errorCode;
    private final String message;
    private String fieldError = "";
}
