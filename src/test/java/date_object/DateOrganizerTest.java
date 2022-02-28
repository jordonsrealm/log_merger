package date_object;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

import org.junit.Test;

import logmerger.frame.component.holder.CheckBoxHolder;
import logmerger.frame.component.holder.WindowComponentHolder;
import logmerger.frame.datedline.organizer.DateLineOrganizer;


public class DateOrganizerTest {

	@Test
	public void testDateInRange() {
		String strToTest = "2022-01-06 21:09:10.758	[AWT-EventQueue-0]\n"
						 + "2022-01-06 21:09:19.428	[AWT-EventQueue-0]\n"
						 + "2022-01-06 21:10:23.021	[AWT-EventQueue-0]\n";
		String dateFormat = "yyyy-MM-dd HH:mm:ss.SSS";
		
		WindowComponentHolder windowComponentHolder = mock(WindowComponentHolder.class);
		CheckBoxHolder chbxHolder = mock(CheckBoxHolder.class);
		doReturn(dateFormat).when(windowComponentHolder).getRegexPatternText();
		doReturn(strToTest).when(windowComponentHolder).getUnorderedText();
		doReturn(chbxHolder).when(windowComponentHolder).getCheckBoxHolder();
		doReturn(true).when(chbxHolder).isUnknownSelected();
		
		DateLineOrganizer boundedDates = new DateLineOrganizer(windowComponentHolder);
		String sortedString = boundedDates.orderDateLines("", "");
		assertEquals( strToTest, sortedString);
	}
}
