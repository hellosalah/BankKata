package org.example;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Clock {
    public LocalDate today() {
        return LocalDate.now();  // Return LocalDate directly
    }
}
