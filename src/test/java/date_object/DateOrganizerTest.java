package date_object;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.junit.Test;

import date.holder.DateHolder;
import date.line.organizer.DateLineOrganizer;


public class DateOrganizerTest {

	@Test
	public void testDateInRange() {
		ArrayList<DateHolder> unboundedList = new ArrayList<>();
		
		String strDate1 = "2020-01-10";
		String strDate2 = "2020-01-20";
		String strDate3 = "2020-01-21";
		
		DateHolder holder1 = new DateHolder( strDate1, "yyyy-MM-dd");
		DateHolder holder2 = new DateHolder( strDate2, "yyyy-MM-dd");
		DateHolder holder3 = new DateHolder( strDate3, "yyyy-MM-dd");
		unboundedList.add(holder1);
		unboundedList.add(holder2);
		unboundedList.add(holder3);
		
		int year     = 2020;
		int month    = 0;
		int dayStart = 1;
		int dayEnd   = 30;
		
		Date minDate = new GregorianCalendar( year, month, dayStart).getTime();
		Date maxDate = new GregorianCalendar( year, month, dayEnd).getTime();
		
		List<DateHolder> boundedDates = DateLineOrganizer.returnListWithBoundedDates(unboundedList, minDate, maxDate);
		assertEquals( 3, boundedDates.size());
	}
	
	@Test
	public void testDateOutOfRange() {
		ArrayList<DateHolder> unboundedList = new ArrayList<>();
		
		String strDate1 = "2020-01-10";
		String strDate2 = "2020-01-20";
		String strDate3 = "2020-01-21";
		
		DateHolder holder1 = new DateHolder( strDate1, "yyyy-MM-dd");
		DateHolder holder2 = new DateHolder( strDate2, "yyyy-MM-dd");
		DateHolder holder3 = new DateHolder( strDate3, "yyyy-MM-dd");
		unboundedList.add(holder1);
		unboundedList.add(holder2);
		unboundedList.add(holder3);
		
		int year     = 2020;
		int month    = 0;
		int dayStart = 1;
		int dayEnd   = 15;
		
		Date minDate = new GregorianCalendar( year, month, dayStart).getTime();
		Date maxDate = new GregorianCalendar( year, month, dayEnd).getTime();
		
		List<DateHolder> boundedDates = DateLineOrganizer.returnListWithBoundedDates(unboundedList, minDate, maxDate);
		assertEquals( 1, boundedDates.size());
	}
}
