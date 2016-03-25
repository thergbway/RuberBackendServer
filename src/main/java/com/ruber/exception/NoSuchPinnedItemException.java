package com.ruber.exception;

public class NoSuchPinnedItemException extends ForbiddenBackendException {
    public NoSuchPinnedItemException(Integer itemId) {
        super("item position " + itemId + " does not exist", ErrorCodes.NO_SUCH_PINNED_ITEM.getCode());
    }
}
