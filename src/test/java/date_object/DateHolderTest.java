package date_object;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Date;
import java.util.GregorianCalendar;

import org.junit.Test;


public class DateHolderTest {

	@Test
	public void testDateRangeInBounds() {
		String strDate1 = "2020-01-10";
		
		int year     = 2020;
		int month    = 0;
		int dayStart = 1;
		int dayEnd   = 15;
		
		Date minDate = new GregorianCalendar( year, month, dayStart).getTime();
		Date maxDate = new GregorianCalendar( year, month, dayEnd).getTime();
		
		DateHolder holder = new DateHolder( strDate1, "yyyy-MM-dd");
		assertTrue(holder.isDateWithinBounds(minDate, maxDate));
	}
	
	@Test
	public void testDateRangeNotInBounds() {
		String strDate1 = "2020-01-18";
		
		int year     = 2020;
		int month    = 0;
		int dayStart = 1;
		int dayEnd   = 15;
		
		Date minDate = new GregorianCalendar( year, month, dayStart).getTime();
		Date maxDate = new GregorianCalendar( year, month, dayEnd).getTime();
		
		DateHolder holder = new DateHolder( strDate1, "yyyy-MM-dd");
		assertFalse(holder.isDateWithinBounds( minDate, maxDate));
	}
}
