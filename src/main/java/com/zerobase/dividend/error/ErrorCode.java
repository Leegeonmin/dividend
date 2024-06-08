package com.zerobase.dividend.error;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {
    PASSWORD_WRONG("비밀번호가 틀렸습니다", HttpStatus.BAD_REQUEST),
    USER_NOT_FOUND("일치하는 회원이 없습니다", HttpStatus.BAD_REQUEST),
    ALREADY_USERNAME_EXISTED("이미 존재하는 회원 아이디입니다", HttpStatus.BAD_REQUEST),
    INTERNAL_SERVER_ERROR("내부 서버 에러입니다", HttpStatus.INTERNAL_SERVER_ERROR),
    INVALID_REQUEST("알 수 없는 요청입니다", HttpStatus.BAD_REQUEST),
    COMPANY_NOT_FOUND("회사 정보가 존재하지 않습니다", HttpStatus.BAD_REQUEST),
    SCRAPPING_UNKNOWN_ERROR("스크래핑 중 알 수 없는 오류가 발생했습니다", HttpStatus.BAD_GATEWAY),
    MONTH_NOT_CORRECT("유효하지 않은 월 입력입니다", HttpStatus.BAD_GATEWAY),
    INVALID_TICKER("유효하지 않은 ticker입니다",HttpStatus.BAD_REQUEST),
    COMPANY_ALREADY_EXISTED("이미 등록되어있는 회사입니다", HttpStatus.BAD_REQUEST);

    private final String description;
    private final HttpStatus httpStatus;
    ErrorCode(String description, HttpStatus httpStatus) {
        this.description = description;
        this.httpStatus = httpStatus;
    }
}
