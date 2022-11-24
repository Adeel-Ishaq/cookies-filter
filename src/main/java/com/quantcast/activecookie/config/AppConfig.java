package com.quantcast.activecookie.config;

import java.time.format.DateTimeFormatter;

public class AppConfig {
    // CSV Columns
    public static final String COOKIE_COLUMN = "cookie";
    public static final String TIME_STAMP_COLUMN = "timestamp";

    // Command input options
    public static final String FILE_COMMAND= "file";
    public static final String DATE_COMMAND = "date";

    // Date format
    public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ISO_ZONED_DATE_TIME;
}
