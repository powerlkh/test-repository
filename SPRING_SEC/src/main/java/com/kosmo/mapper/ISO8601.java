package com.kosmo.mapper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

public final class ISO8601 {
    /** Transform Calendar to ISO 8601 string. */
    public static String transISO8601(Calendar calendarString) {
        Date date = calendarString.getTime();
        String formatted = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
            .format(date);
        return formatted.substring(0, 22) + ":" + formatted.substring(22);
    }

    /** Get current date and time formatted as ISO 8601 string. */
    public String now() {
        return transISO8601(GregorianCalendar.getInstance());
    }


    /** Transform ISO 8601 string to Calendar. */
    public String transCalendar(String iso8601String)
            throws ParseException {
        Calendar calendar = GregorianCalendar.getInstance();
        String s = iso8601String.replace("Z", "+00:00");
        try {
            s = s.substring(0, 22) + s.substring(23);  // to get rid of the ":"
        } catch (IndexOutOfBoundsException e) {
            throw new ParseException("Invalid length", 0);
        }

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ", Locale.KOREA);
		return format.format(calendar.getTime());
    }

    public static void main(String[] args) {

		ISO8601 timeUtil = new ISO8601();
		String ISO8601Str = timeUtil.now();
		System.out.println(ISO8601Str);


		try {
			String calendarStr = timeUtil.transCalendar(ISO8601Str);
			System.out.println(calendarStr);

		} catch (ParseException e) {
			e.printStackTrace();
		}



	}



}