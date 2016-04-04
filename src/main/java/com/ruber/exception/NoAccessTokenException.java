package com.ruber.exception;

public class NoAccessTokenException extends UnauthorisedBackendException {
    public NoAccessTokenException() {
        super("access token is not presented", ErrorCodes.NO_ACCESS_TOKEN.getCode());
    }
}
