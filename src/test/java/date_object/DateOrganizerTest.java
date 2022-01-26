package date_object;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.junit.Test;

import date.line.organizer.DateLineOrganizer;
import transfer.object.DatedLine;


public class DateOrganizerTest {

//	@Test
//	public void testDateInRange() {
//		ArrayList<DatedLine> unboundedList = new ArrayList<>();
//		
//		String strDate1 = "2020-01-10";
//		String strDate2 = "2020-01-20";
//		String strDate3 = "2020-01-21";
//		
//		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
//        Date embeddedDate1 = null;
//        Date embeddedDate2 = null;
//        Date embeddedDate3 = null;
//        Exception caughtException = null;
//        
//        try {
//        	embeddedDate1 = formatter.parse(strDate1);
//            embeddedDate2 = formatter.parse(strDate2);
//            embeddedDate3 = formatter.parse(strDate3);
//        } catch (ParseException e) {
//        	caughtException = e;
//        }
//		
//		DatedLine holder1 = new DatedLine( strDate1, embeddedDate1);
//		DatedLine holder2 = new DatedLine( strDate2, embeddedDate2);
//		DatedLine holder3 = new DatedLine( strDate3, embeddedDate3);
//		unboundedList.add(holder1);
//		unboundedList.add(holder2);
//		unboundedList.add(holder3);
//		
//		int year     = 2020;
//		int month    = 0;
//		int dayStart = 1;
//		int dayEnd   = 30;
//		
//		Date minDate = new GregorianCalendar( year, month, dayStart).getTime();
//		Date maxDate = new GregorianCalendar( year, month, dayEnd).getTime();
//		
//		List<DatedLine> boundedDates = DateLineOrganizer.returnListWithBoundedDates(unboundedList, minDate, maxDate);
//		assertEquals( 3, boundedDates.size());
//		assertNull(caughtException);
//	}
//	
//	@Test
//	public void testDateOutOfRange() {
//		ArrayList<DatedLine> unboundedList = new ArrayList<>();
//		
//		String strDate1 = "2020-01-10";
//		String strDate2 = "2020-01-20";
//		String strDate3 = "2020-01-21";
//		
//		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
//        Date embeddedDate1 = null;
//        Date embeddedDate2 = null;
//        Date embeddedDate3 = null;
//        Exception caughtException = null;
//        
//        try {
//        	embeddedDate1 = formatter.parse(strDate1);
//            embeddedDate2 = formatter.parse(strDate2);
//            embeddedDate3 = formatter.parse(strDate3);
//        } catch (ParseException e) {
//        	caughtException = e;
//        }
//		
//		DatedLine holder1 = new DatedLine( strDate1, embeddedDate1);
//		DatedLine holder2 = new DatedLine( strDate2, embeddedDate2);
//		DatedLine holder3 = new DatedLine( strDate3, embeddedDate3);
//		unboundedList.add(holder1);
//		unboundedList.add(holder2);
//		unboundedList.add(holder3);
//		
//		int year     = 2020;
//		int month    = 0;
//		int dayStart = 1;
//		int dayEnd   = 15;
//		
//		Date minDate = new GregorianCalendar( year, month, dayStart).getTime();
//		Date maxDate = new GregorianCalendar( year, month, dayEnd).getTime();
//		
//		List<DatedLine> boundedDates = DateLineOrganizer.returnListWithBoundedDates(unboundedList, minDate, maxDate);
//		assertEquals( 1, boundedDates.size());
//		assertNull(caughtException);
//	}
}
