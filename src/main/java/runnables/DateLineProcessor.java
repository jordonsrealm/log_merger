package runnables;

import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import date.line.organizer.DateLineOrganizer;
import mainwindow.holder.MainWindowHolder;


public class DateLineProcessor implements Runnable {

	private MainWindowHolder mainWindowContainer;
	private JScrollPane organizedScrollPane;
	private JTextArea orderedTextArea;
	
	
	public DateLineProcessor(MainWindowHolder mainWindowContainer) {
		this.mainWindowContainer = mainWindowContainer;
		this.orderedTextArea = mainWindowContainer.getTxtHolder().getOrderedTextArea();
	}
	
	@Override
	public void run() {
		System.out.println("Descending: " + mainWindowContainer.isDescending());
		
		String textToOrder = mainWindowContainer.getUnorderedText();
		String formatString = mainWindowContainer.getRegexPatternText();
		String minDateString = mainWindowContainer.getMinDateText();
		String maxDateString = mainWindowContainer.getMaxDateText();
		
		String completeTextString = new DateLineOrganizer(textToOrder, formatString, mainWindowContainer)
										.orderDateLines(minDateString, maxDateString);
		
		orderedTextArea.setText(completeTextString);
		orderedTextArea.setCaretPosition(0);
        
        if(organizedScrollPane != null && organizedScrollPane.getHorizontalScrollBar()!=null) {
        	organizedScrollPane.getHorizontalScrollBar().setValue(0);
        }
	}
}
