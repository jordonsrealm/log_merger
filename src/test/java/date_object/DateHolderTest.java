package date_object;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Date;
import java.util.GregorianCalendar;

import org.junit.Test;

import datedline.DatedLine;


public class DateHolderTest {

	@Test
	public void testDateRangeInBounds() {
		String strDate = "2020-01-10	Random Text Here...";
		String strDateOnly = strDate.substring(0, 10);
		String dateFormatString = "yyyy-MM-dd";
		
		int year     = 2020;
		int month    = 0;
		int dayStart = 1;
		int dayEnd   = 15;
		
		Date minDate = new GregorianCalendar( year, month, dayStart).getTime();
		Date maxDate = new GregorianCalendar( year, month, dayEnd).getTime();
        
		DatedLine holder = new DatedLine( strDate, dateFormatString);
		assertEquals( strDateOnly, holder.getDateAsString());
		assertTrue(holder.isWithinBounds(minDate, maxDate));
	}
	
	@Test
	public void testDateRangeNotInBounds() {
		String strDate = "2020-01-18	Random Text Here...";
		String strDateOnly = strDate.substring(0, 10);
		String dateFormatString = "yyyy-MM-dd";
		
		int year     = 2020;
		int month    = 0;
		int dayStart = 1;
		int dayEnd   = 15;
		
		Date minDate = new GregorianCalendar( year, month, dayStart).getTime();
		Date maxDate = new GregorianCalendar( year, month, dayEnd).getTime();
		
		DatedLine holder = new DatedLine( strDate, dateFormatString);
		assertEquals( strDateOnly, holder.getDateAsString());
		assertFalse(holder.isWithinBounds( minDate, maxDate));
	}
}
