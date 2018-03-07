package ru.javawebinar.topjava.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Created by Смена on 07.03.2018.
 */
public class MyDateTimeFormatter {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public static String toString(LocalDateTime localDateTime) {
        return localDateTime.format(formatter);
    }

    public static LocalDateTime toLocalDateTime(String string) {
        return LocalDateTime.parse(string, formatter);
    }
}
