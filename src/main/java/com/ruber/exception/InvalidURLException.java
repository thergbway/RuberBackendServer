package com.ruber.exception;

public class InvalidURLException extends BadRequestBackendException {
    public InvalidURLException(String urlToParseAttempt) {
        super("malformed url specified: " + urlToParseAttempt, ErrorCodes.MALFORMED_URL.getCode());
    }
}
