package be.alb_mar_hen.formatters;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

public class LocalDateTimeFormatter {
    private static final String DEFAULT_PATTERN = "dd/MM/yyyy";

    public static String format(LocalDateTime dateTime) {
        return format(dateTime, DEFAULT_PATTERN);
    }

    public static String format(LocalDateTime dateTime, String pattern) {
        if (dateTime == null || pattern == null || pattern.isEmpty()) {
            return "";
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        return dateTime.format(formatter);
    }

    public static String format(Optional<LocalDateTime> dateTime) {
        return dateTime.map(LocalDateTimeFormatter::format).orElse("");
    }

    public static String format(Optional<LocalDateTime> dateTime, String pattern) {
        return dateTime.map(dt -> format(dt, pattern)).orElse("");
    }
}