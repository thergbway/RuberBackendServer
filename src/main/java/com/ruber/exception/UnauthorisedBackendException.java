package com.ruber.exception;

public abstract class UnauthorisedBackendException extends BackendException {
    public UnauthorisedBackendException(String message, Integer code) {
        super(message, code);
    }

    public UnauthorisedBackendException(Throwable cause, String message, Integer code) {
        super(cause, message, code);
    }
}
