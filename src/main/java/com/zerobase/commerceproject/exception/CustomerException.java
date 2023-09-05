package com.zerobase.commerceproject.exception;

import lombok.Getter;

@Getter
public class CustomerException extends RuntimeException{
    private final ErrorCode errorCode;

    public CustomerException(ErrorCode errorCode){
        super(errorCode.getDetail());
        this.errorCode = errorCode;
    }


}
