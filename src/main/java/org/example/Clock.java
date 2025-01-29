package org.example;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Clock {
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    public String todayAsString() {
        return LocalDate.now().format(DATE_FORMATTER);
    }
}
