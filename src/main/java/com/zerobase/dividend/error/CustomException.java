package com.zerobase.dividend.error;


public class CustomException extends  RuntimeException{
    private ErrorCode errorCode;
    private String message;

    public CustomException(ErrorCode errorCode){
        this.errorCode = errorCode;
        this.message = errorCode.getDescription();
    }
}
