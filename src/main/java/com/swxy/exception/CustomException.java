package com.swxy.exception;

public class CustomException extends RuntimeException {

    private String errorCode;

    public CustomException(String message) {
        super(message);
    }

    public CustomException(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public CustomException() {
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorCode() {
        return errorCode;
    }
}
