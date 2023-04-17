package com.example.blogwebsite.common.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateTimeUtils {
    private DateTimeUtils() {
        throw new IllegalStateException("Utility class");
    }
    public static final String DATETIME_FORMAT ="dd-MM-yyyy HH:mm:ss";
    public static final String DATETIME_FORMAT_VNPAY ="yyyyMMddHHmmss";
    public static final DateTimeFormatter DATETIME_FORMATTER =
            DateTimeFormatter.ofPattern(DATETIME_FORMAT);
    public static String now() {
        return LocalDateTime.now().format(DATETIME_FORMATTER);
    }
}
