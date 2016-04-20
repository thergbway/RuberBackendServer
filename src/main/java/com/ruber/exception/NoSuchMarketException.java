package com.ruber.exception;

public class NoSuchMarketException extends ForbiddenBackendException {
    public NoSuchMarketException(Integer marketVkId, Integer userVkId) {
        super("market with vk_id = " + marketVkId + " does not exist or belong to user with vk_id = " + userVkId,
            ErrorCodes.NO_SUCH_MARKET.getCode());
    }
}
