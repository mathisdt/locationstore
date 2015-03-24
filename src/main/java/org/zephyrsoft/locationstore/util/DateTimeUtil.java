package org.zephyrsoft.locationstore.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class DateTimeUtil {
	
	private static final String DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";
	
	private DateTimeUtil() {
		// not instantiable
	}
	
	public static String toString(LocalDateTime localDateTime) {
		return DateTimeFormatter.ofPattern(DATE_TIME_PATTERN, Locale.GERMANY).format(localDateTime);
	}
	
	public static LocalDateTime fromString(String localDateTimeAsString) {
		return LocalDateTime.from(
			DateTimeFormatter.ofPattern(DATE_TIME_PATTERN, Locale.GERMANY).parse(localDateTimeAsString));
	}
	
}
