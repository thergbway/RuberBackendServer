package com.ruber.exception;

public class InvalidURLException extends BackendException {
    public InvalidURLException(String urlToParseAttempt) {
        super("malformed url specified: " + urlToParseAttempt, ErrorCodes.MALFORMED_URL.getCode());
    }
}
