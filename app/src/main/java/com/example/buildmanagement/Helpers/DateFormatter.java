package com.example.buildmanagement.Helpers;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class DateFormatter {
    public static String getFormatedStringDate(Calendar date) {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat format1 = new SimpleDateFormat("dd-MM-yyyy");

        String formatted = format1.format(cal.getTime());
        return formatted;
    }

    public static Calendar stringToDate(String dateString) {
        try {
            Calendar cal = Calendar.getInstance();
            SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy", Locale.ENGLISH);
            cal.setTime(sdf.parse(dateString));
            return cal;
        }
        catch (Exception e) {
            System.out.printf(e.getMessage());
            return null;
        }
    }

    public static Calendar stringToDateFromPicker(String dateString) {
        try {
            Calendar cal = Calendar.getInstance();
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
            cal.setTime(sdf.parse(dateString));
            return cal;
        }
        catch (Exception e) {
            System.out.printf(e.getMessage());
            return null;
        }
    }
}
