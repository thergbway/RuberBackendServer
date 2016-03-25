package com.ruber.exception;

public class InvalidRequestJsonException extends BadRequestBackendException {
    public InvalidRequestJsonException(String reason) {
        super("invalid request json specified: " + reason, ErrorCodes.INVALID_REQUEST_JSON.getCode());
    }
}
