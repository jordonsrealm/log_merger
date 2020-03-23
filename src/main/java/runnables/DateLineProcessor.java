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
		JTextArea organizedTextArea = mainWindowContainer.getTxtHolder().getOrderedText();
		
		DateLineOrganizer dateOrganizer = new DateLineOrganizer(mainWindowContainer).orderDateLines().handleDateBoundariesReturnList();
        
        organizedTextArea.setText(dateOrganizer.toString());
        organizedTextArea.setCaretPosition(0);
        
        if(organizedScrollPane != null && organizedScrollPane.getHorizontalScrollBar()!=null) {
        	organizedScrollPane.getHorizontalScrollBar().setValue(0);
        }
	}
}
