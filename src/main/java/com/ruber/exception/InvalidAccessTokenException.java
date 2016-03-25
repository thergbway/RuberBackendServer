package com.ruber.exception;

public class InvalidAccessTokenException extends BackendException {
    public InvalidAccessTokenException() {
        super("invalid access token", ErrorCodes.INVALID_ACCESS_TOKEN.getCode());
    }
}
