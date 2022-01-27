package date_object;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;

import org.junit.Test;

import transfer.object.DatedLine;


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
		
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date embeddedDate = null;
        Exception caughtException = null;
        
        try {
        	embeddedDate = formatter.parse(strDate1);
        } catch (ParseException e) {
        	caughtException = e;
        }
        
		DatedLine holder = new DatedLine( strDate1, embeddedDate);
		assertTrue(holder.isWithinBounds(minDate, maxDate));
		assertNull(caughtException);
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
		
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date embeddedDate = null;
        Exception caughtException = null;
        
        try {
        	embeddedDate = formatter.parse(strDate1);
        } catch (ParseException e) {
        	caughtException = e;
        }
		
		DatedLine holder = new DatedLine( strDate1, embeddedDate);
		assertFalse(holder.isWithinBounds( minDate, maxDate));
		assertNull(caughtException);
	}
}
