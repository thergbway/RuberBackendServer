package com.ruber.exception;

public class NoSuchOrderException extends ForbiddenBackendException {
    public NoSuchOrderException(Integer orderId) {
        super("order " + orderId + " does not exist", ErrorCodes.NO_SUCH_ORDER.getCode());
    }
}
