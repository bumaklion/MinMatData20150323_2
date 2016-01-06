package main.java.se.bumaklion.myrecipes.util;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * @author olle
 */
public class DateUtil {

	/**
	 * @param year
	 * @param month
	 *            January = 0
	 * @param day
	 *            the day of the month
	 * @return a Date with the passed parameters set, other time fields are set
	 *         to 0.
	 */
	public static Date getDate(int year, int month, int day) {
		return getDate(year, month, day, 0, 0, 0);
	}

	/**
	 * 
	 * @param year
	 * @param month
	 *            January = 0
	 * @param day
	 *            the day of the month
	 * @param hour
	 *            24 hour clock
	 * @param minute
	 * @param second
	 * @return a Date with the passed parameters set, other time fields are set
	 *         to 0.
	 */
	public static Date getDate(int year, int month, int day, int hour, int minute, int second) {
		Calendar c = new GregorianCalendar(year, month, day, hour, minute, second);
		return c.getTime();
	}

}
