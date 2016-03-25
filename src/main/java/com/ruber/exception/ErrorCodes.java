package com.ruber.exception;

enum ErrorCodes {
    INVALID_EXTERNAL_APP_CREDENTIALS(100),
    INVALID_ACCESS_TOKEN(101),
    NO_SUCH_ORDER(200),
    NO_SUCH_ORDER_POSITION(300),
    NO_SUCH_PINNED_ITEM(400),
    NOT_ENOUGH_ARGUMENTS(500),
    MALFORMED_URL(501),
    INVALID_REQUEST_JSON(502),
    MULTIPART_IO_EXCEPTION(503);

    private Integer code;

    public Integer getCode() {
        return code;
    }

    ErrorCodes(Integer code) {
        this.code = code;
    }
}