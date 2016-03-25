package com.ruber.exception;

public abstract class ForbiddenBackendException extends BackendException {
    public ForbiddenBackendException(String message, Integer code) {
        super(message, code);
    }

    public ForbiddenBackendException(Throwable cause, String message, Integer code) {
        super(cause, message, code);
    }
}