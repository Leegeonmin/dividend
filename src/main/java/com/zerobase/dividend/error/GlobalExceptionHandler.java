package com.zerobase.dividend.error;

import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ErrorResponse> handleCustomException(final CustomException e) {
        log.error("CustomException occurred : ", e);

        return ResponseEntity.status(e.getErrorCode().getHttpStatus()).body(
                ErrorResponse.builder()
                        .errorCode(e.getErrorCode())
                        .message(e.getMessage())
                        .build()
        );
    }

    //요청 데이터 오류
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.error("MethodArgumentNotValidException is occurred", e);

        BindingResult bindingResult = e.getBindingResult();
        FieldError error = bindingResult.getFieldError();
        String fieldError = String.format("Field: %s, Error: %s", error.getField(), error.getDefaultMessage());

        return ResponseEntity.status(ErrorCode.INVALID_REQUEST.getHttpStatus()).body(
                ErrorResponse.builder()
                        .errorCode(ErrorCode.INVALID_REQUEST)
                        .message(ErrorCode.INVALID_REQUEST.getDescription())
                        .fieldError(fieldError)
                        .build()
        );
    }

    //자주 발생되는 오류
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorResponse> handleDataIntegrityViolationException(DataIntegrityViolationException e) {
        log.error("DataIntegrityViolationException occurred", e);
        return ResponseEntity.status(ErrorCode.INVALID_REQUEST.getHttpStatus()).body(
                ErrorResponse.builder()
                        .errorCode(ErrorCode.INVALID_REQUEST)
                        .message(ErrorCode.INVALID_REQUEST.getDescription())
                        .build()
        );
    }

    @ResponseStatus
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception e) {
        log.error("Exception is occurred", e);
        return ResponseEntity.status(ErrorCode.INTERNAL_SERVER_ERROR.getHttpStatus()).body(
                ErrorResponse.builder()
                        .errorCode(ErrorCode.INTERNAL_SERVER_ERROR)
                        .message(ErrorCode.INTERNAL_SERVER_ERROR.getDescription())
                        .build()
        );
    }
}
