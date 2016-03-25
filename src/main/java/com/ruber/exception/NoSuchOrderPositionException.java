package com.ruber.exception;

public class NoSuchOrderPositionException extends ForbiddenBackendException {
    public NoSuchOrderPositionException(Integer orderPositionId) {
        super("order position " + orderPositionId + " does not exist", ErrorCodes.NO_SUCH_ORDER_POSITION.getCode());
    }
}
