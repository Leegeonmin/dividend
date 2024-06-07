package com.zerobase.dividend.error;


public class CustomException extends  RuntimeException{
    private final ErrorCode errorCode;
    private final String message;

    public CustomException(ErrorCode errorCode){
        this.errorCode = errorCode;
        this.message = errorCode.getDescription();
    }
}
