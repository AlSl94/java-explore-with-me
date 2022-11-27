package ru.practicum.ewm.utility;

import java.time.format.DateTimeFormatter;

public class Constants {

    private Constants() {
    }

    public static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
}
