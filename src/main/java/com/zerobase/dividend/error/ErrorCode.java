package com.zerobase.dividend.error;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
public enum ErrorCode {
    SCRAPPING_UNKNOWN_ERROR("스크래핑 중 알 수 없는 오류가 발생했습니다", HttpStatus.BAD_GATEWAY),
    MONTH_NOT_CORRECT("유효하지 않은 월 입력입니다", HttpStatus.BAD_GATEWAY),
    INVALID_TICKER("유효하지 않은 ticker입니다",HttpStatus.BAD_REQUEST),
    ALREADY_EXISTED("이미 등록되어있는 회사입니다", HttpStatus.BAD_REQUEST);

    private String description;
    private HttpStatus httpStatus;
    ErrorCode(String description, HttpStatus httpStatus) {
        this.description = description;
    }
}
