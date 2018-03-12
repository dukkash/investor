package com.dukkash.investor.util;

		import java.time.LocalDate;
		import java.time.ZoneId;
		import java.util.Date;

public class DateUtil {

	public static Date getDate(String dateAsStr) {

		if(dateAsStr == null || dateAsStr.trim().isEmpty()) {
			return null;
		}

		if(dateAsStr.contains("-")) { // format YYYY-MM-DD
			String[] dateStr = dateAsStr.trim().split("\\-");
			LocalDate date = LocalDate.of(Integer.valueOf(dateStr[0]), Integer.valueOf(dateStr[1]),
					Integer.valueOf(dateStr[2]));
			return Date.from(date.atStartOfDay(ZoneId.systemDefault()).toInstant());
		} else { // format DD.MM.YYYY
			String[] dateStr = dateAsStr.trim().split("\\.");
			LocalDate date = LocalDate.of(Integer.valueOf(dateStr[2]), Integer.valueOf(dateStr[1]),
					Integer.valueOf(dateStr[0]));
			return Date.from(date.atStartOfDay(ZoneId.systemDefault()).toInstant());
		}
	}

}
