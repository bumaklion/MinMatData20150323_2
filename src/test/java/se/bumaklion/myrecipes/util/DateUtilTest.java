package test.java.se.bumaklion.myrecipes.util;

import static org.junit.Assert.assertEquals;

import java.util.Calendar;
import java.util.GregorianCalendar;

import org.junit.Test;

import main.java.se.bumaklion.myrecipes.util.DateUtil;
import test.java.se.bumaklion.myrecipes.BumTest;

/**
 * @author olle
 */
public class DateUtilTest extends BumTest {

	@Test
	public void getDateYearMonthDay() {
		Calendar c = new GregorianCalendar();
		c.setTime(DateUtil.getDate(2013, 4, 20));

		assertEquals(c.get(Calendar.YEAR), 2013);
		assertEquals(c.get(Calendar.MONTH), 4);
		assertEquals(c.get(Calendar.DAY_OF_MONTH), 20);
		assertEquals(c.get(Calendar.HOUR_OF_DAY), 0);
		assertEquals(c.get(Calendar.MINUTE), 0);
		assertEquals(c.get(Calendar.SECOND), 0);
		assertEquals(c.get(Calendar.MILLISECOND), 0);
	}

	@Test
	public void getDatePrecise() {
		Calendar c = new GregorianCalendar();
		c.setTime(DateUtil.getDate(2013, 4, 20, 23, 30, 20));

		assertEquals(c.get(Calendar.YEAR), 2013);
		assertEquals(c.get(Calendar.MONTH), 4);
		assertEquals(c.get(Calendar.DAY_OF_MONTH), 20);
		assertEquals(c.get(Calendar.HOUR_OF_DAY), 23);
		assertEquals(c.get(Calendar.MINUTE), 30);
		assertEquals(c.get(Calendar.SECOND), 20);
		assertEquals(c.get(Calendar.MILLISECOND), 0);
	}

}
