package com.org.mfm.util.date;

import java.sql.Date;
import java.util.Calendar;

public class DateUtils {

	public static String getFinancialYear(Date date) {
		// Convert java.sql.Date to java.util.Calendar
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);

		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH); // Note: MONTH is zero-based (0 = January)

		// Determine the financial year
		if (month >= Calendar.APRIL) {
			// If the month is April or later, the financial year starts in the current year
			return year + "-" + (year + 1);
		} else {
			// If the month is before April, the financial year started in the previous year
			return (year - 1) + "-" + year;
		}
	}
	
	// Method to map calendar month to financial year month
	public static int mapToFinancialYearMonth(int calendarMonth) {
		if (calendarMonth >= 4 && calendarMonth <= 12) {
			return calendarMonth - 3; // April (4) to December (12)
		} else {
			return calendarMonth + 9; // January (1) to March (3)
		}
	}
	
}
