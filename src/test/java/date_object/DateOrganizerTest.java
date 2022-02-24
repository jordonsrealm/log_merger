package date_object;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

import org.junit.Test;

import date.line.organizer.DateLineOrganizer;
import mainwindow.components.ListeningCheckBox;
import mainwindow.components.holder.CheckBoxHolder;
import mainwindow.holder.WindowComponentHolder;


public class DateOrganizerTest {

	@Test
	public void testDateInRange() {
		String strToTest = "2022-01-06 21:09:10.758	[AWT-EventQueue-0]\n"
						 + "2022-01-06 21:09:19.428	[AWT-EventQueue-0]\n"
						 + "2022-01-06 21:10:23.021	[AWT-EventQueue-0]\n";
		String dateFormat = "yyyy-MM-dd HH:mm:ss.SSS";
		
		WindowComponentHolder holder = mock(WindowComponentHolder.class);
		CheckBoxHolder chbxHolder = mock(CheckBoxHolder.class);
		ListeningCheckBox listenBox = mock(ListeningCheckBox.class);
		
		doReturn(dateFormat).when(holder).getRegexPatternText();
		doReturn(strToTest).when(holder).getUnorderedText();
		doReturn(chbxHolder).when(holder).getCheckBoxHolder();
		doReturn(listenBox).when(chbxHolder).getInfoCheckBox();
		doReturn(listenBox).when(chbxHolder).getDebugCheckBox();
		doReturn(listenBox).when(chbxHolder).getTraceCheckBox();
		doReturn(listenBox).when(chbxHolder).getWarnCheckBox();
		doReturn(listenBox).when(chbxHolder).getUnknownCheckBox();
		doReturn(listenBox).when(chbxHolder).getErrorCheckBox();
		doReturn(true).when(listenBox).isSelected();
		
		DateLineOrganizer boundedDates = new DateLineOrganizer(holder);
		String sortedString = boundedDates.orderDateLines("", "");
		assertEquals( strToTest, sortedString);
	}
}
