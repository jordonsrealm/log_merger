package runnables;

import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import container.MainWindowContainer;
import date.object.DateLineOrganizer;


public class DateLineProcessor implements Runnable {

	private MainWindowContainer mainWindowContainer;
	private JTextArea organizedTextArea;
	private JScrollPane organizedScrollPane;
	
	
	public DateLineProcessor(MainWindowContainer mainWindowContainer) {
		this.mainWindowContainer = mainWindowContainer;
	}
	
	@Override
	public void run() {
		
		organizedTextArea = mainWindowContainer.getOrganizedText();
		
		DateLineOrganizer dateOrganizer = new DateLineOrganizer(mainWindowContainer).orderDateLines().handleDateBoundariesReturnList();
        
        organizedTextArea.setText(dateOrganizer.toString());
        organizedTextArea.setCaretPosition(0);
        
        if(organizedScrollPane != null && organizedScrollPane.getHorizontalScrollBar()!=null) {
        	organizedScrollPane.getHorizontalScrollBar().setValue(0);
        }
	}
}
