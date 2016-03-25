package com.ruber.exception;

abstract class BackendException extends RuntimeException {
    private String message;
    private Integer code;

    public BackendException(String message, Integer code) {
        this.message = message;
        this.code = code;
    }

    public BackendException(Throwable cause, String message, Integer code) {
        super(cause);
        this.message = message;
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public Integer getCode() {
        return code;
    }
}