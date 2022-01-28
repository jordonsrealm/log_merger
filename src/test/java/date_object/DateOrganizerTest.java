package date_object;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

import date.line.organizer.DateLineOrganizer;


public class DateOrganizerTest {

	@Test
	public void testDateInRange() {
		String strToTest = "2022-01-06 21:09:10.758	[AWT-EventQueue-0]\n"
						 + "2022-01-06 21:09:19.428	[AWT-EventQueue-0]\n"
						 + "2022-01-06 21:10:23.021	[AWT-EventQueue-0]\n";
		
		DateLineOrganizer boundedDates = new DateLineOrganizer(strToTest, "yyyy-MM-dd HH:mm:ss.SSS", false);
		String sortedString = boundedDates.orderDateLines("", "");
		System.out.println(sortedString.length());
		System.out.println(strToTest.length());
		assertEquals( strToTest, sortedString);
	}
}
