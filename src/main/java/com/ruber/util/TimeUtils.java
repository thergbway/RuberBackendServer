package com.ruber.util;

public class TimeUtils {
    private TimeUtils() {
    }

    public static Long getCurrentTimestamp() {
        return System.currentTimeMillis() / 1000L;
    }
}
