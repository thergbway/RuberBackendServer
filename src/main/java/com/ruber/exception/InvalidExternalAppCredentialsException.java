package com.ruber.exception;

public class InvalidExternalAppCredentialsException extends ForbiddenBackendException {
    public InvalidExternalAppCredentialsException() {
        super("invalid external application credentials", ErrorCodes.INVALID_EXTERNAL_APP_CREDENTIALS.getCode());
    }
}
