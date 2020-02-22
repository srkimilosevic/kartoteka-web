package com.luv2code.kartotekaweb.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class CalendarUtil {
    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("dd.MM.yyyy.");


    public static String parse(LocalDate date){
        date = LocalDate.now();
        return date.format(DATE_FORMAT);
    }
}
