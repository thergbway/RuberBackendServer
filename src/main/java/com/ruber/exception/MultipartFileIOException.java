package com.ruber.exception;

public class MultipartFileIOException extends BackendException {
    public MultipartFileIOException(Throwable cause) {
        super(cause, "io exception while accessing specified multipart file content",
            ErrorCodes.MULTIPART_IO_EXCEPTION.getCode());
    }
}
