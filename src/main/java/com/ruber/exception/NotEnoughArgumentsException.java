package com.ruber.exception;

import java.util.List;

public class NotEnoughArgumentsException extends BackendException {
    public NotEnoughArgumentsException(List<String> notSpecifiedArguments) {
        super("not enough required arguments were specified: " + argsToPrettyString(notSpecifiedArguments),
            ErrorCodes.NOT_ENOUGH_ARGUMENTS.getCode());
    }

    private static String argsToPrettyString(List<String> notSpecifiedArguments) {
        StringBuilder sb = new StringBuilder();
        notSpecifiedArguments.forEach(arg -> {
            sb.append(arg);
            sb.append(", ");
        });
        sb.delete(sb.length() - 1 - 1, sb.length());

        return sb.toString();
    }
}
