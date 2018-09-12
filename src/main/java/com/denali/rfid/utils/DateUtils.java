/**
 * 
 */
package com.denali.rfid.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/** 
 * @author zentere
 *
 */
public class DateUtils {
	
	private static final String DATE_FORMAT = "yyyy/MM/dd HH:mm:ss";
	private static final String DATE_FORMAT_OTHER = "yyyy-MM-dd HH:mm:ss";

	public static String dateFormattedWith24Hours() {
		try {
			SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
			return dateFormat.format(new Date());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static Date formattedDateWith24Hours(String dateString) {
		try {
			DateFormat format = new SimpleDateFormat(DATE_FORMAT_OTHER);
			Date date = format.parse(dateString);
			return date;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static boolean isDateInBetweenIncludingEndPoints(final Date min, final Date max, final Date date) {
		return !(date.before(min) || date.after(max));
	}
}
