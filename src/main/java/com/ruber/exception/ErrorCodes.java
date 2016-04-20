package com.ruber.exception;

public enum ErrorCodes {
    INVALID_EXTERNAL_APP_CREDENTIALS(100),
    INVALID_ACCESS_TOKEN(101),
    NO_ACCESS_TOKEN(102),
    NO_SUCH_ORDER(200),
    NO_SUCH_ORDER_POSITION(300),
    NO_SUCH_PINNED_ITEM(400),
    NO_SUCH_MARKET(450),
    NOT_ENOUGH_ARGUMENTS(500),
    MALFORMED_URL(501),
    INVALID_REQUEST_JSON(502),
    MULTIPART_IO_EXCEPTION(503),
    INTERNAL_ERROR(600);

    private Integer code;

    public Integer getCode() {
        return code;
    }

    ErrorCodes(Integer code) {
        this.code = code;
    }
}