package com.ruber.exception;

public abstract class BadRequestBackendException extends BackendException {
    public BadRequestBackendException(String message, Integer code) {
        super(message, code);
    }

    public BadRequestBackendException(Throwable cause, String message, Integer code) {
        super(cause, message, code);
    }
}
