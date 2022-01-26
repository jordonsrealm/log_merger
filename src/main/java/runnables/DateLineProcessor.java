package runnables;

import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import date.line.organizer.DateLineOrganizer;
import mainwindow.holder.MainWindowHolder;


public class DateLineProcessor implements Runnable {

	private MainWindowHolder mainWindowContainer;
	private JScrollPane organizedScrollPane;
	
	
	public DateLineProcessor(MainWindowHolder mainWindowContainer) {
		this.mainWindowContainer = mainWindowContainer;
	}
	
	@Override
	public void run() {
		JTextArea orderedTextArea = mainWindowContainer.getTxtHolder().getOrderedTextArea();
		String textToOrder = mainWindowContainer.getUnorderedText();
		String formatString = mainWindowContainer.getRegexPatternText();
		String minDateString = mainWindowContainer.getMinDateText();
		String maxDateString = mainWindowContainer.getMaxDateText();
		
		String completeTextString = new DateLineOrganizer(textToOrder, formatString, mainWindowContainer.isDescending()).orderDateLines(minDateString, maxDateString);
		
		orderedTextArea.setText(completeTextString);
		orderedTextArea.setCaretPosition(completeTextString.length());
        
        if(organizedScrollPane != null && organizedScrollPane.getHorizontalScrollBar()!=null) {
        	organizedScrollPane.getHorizontalScrollBar().setValue(0);
        }
	}
}
