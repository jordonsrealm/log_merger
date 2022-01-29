package date_object;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

import org.junit.Test;

import date.line.organizer.DateLineOrganizer;
import mainwindow.holder.MainWindowHolder;


public class DateOrganizerTest {

	@Test
	public void testDateInRange() {
		String strToTest = "2022-01-06 21:09:10.758	[AWT-EventQueue-0]\n"
						 + "2022-01-06 21:09:19.428	[AWT-EventQueue-0]\n"
						 + "2022-01-06 21:10:23.021	[AWT-EventQueue-0]\n";
		String dateFormat = "yyyy-MM-dd HH:mm:ss.SSS";
		
		MainWindowHolder holder = mock(MainWindowHolder.class);
		
		DateLineOrganizer boundedDates = new DateLineOrganizer(strToTest, dateFormat, holder);
		String sortedString = boundedDates.orderDateLines("", "");
		System.out.println(sortedString.length());
		System.out.println(strToTest.length());
		assertEquals( strToTest, sortedString);
	}
}
