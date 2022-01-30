package runnables;

import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

import centerpoint.object.CenteredPointType;
import date.line.organizer.DateLineOrganizer;
import mainwindow.components.LogMergerWindow;
import mainwindow.holder.MainWindowHolder;
import threads.LoadingIcon;


public class DateLineProcessor implements Runnable {

	private LogMergerWindow logMergerWindow;
	private JScrollPane organizedScrollPane;
	
	public DateLineProcessor(LogMergerWindow logMergerWindow) {
		this.logMergerWindow = logMergerWindow;
	}
	
	@Override
	public void run() {
		MainWindowHolder mainWindowContainer = logMergerWindow.getWindowHolder();
		
    	LoadingIcon processingThread = new LoadingIcon( mainWindowContainer, CenteredPointType.ORDERED_TEXT_AREA);
		processingThread.startLoading();
		
		String textToOrder = mainWindowContainer.getUnorderedText();
		String formatString = mainWindowContainer.getRegexPatternText();
		String minDateString = mainWindowContainer.getMinDateText();
		String maxDateString = mainWindowContainer.getMaxDateText();
		
		String completeTextString = new DateLineOrganizer(textToOrder, formatString, mainWindowContainer).orderDateLines(minDateString, maxDateString);
		JTextArea orderTextArea = mainWindowContainer.getTxtHolder().getOrderedTextArea();
		
		SwingUtilities.invokeLater(()->{
			orderTextArea.setText(completeTextString);
			orderTextArea.setCaretPosition(0);
	        
	        if(organizedScrollPane != null && organizedScrollPane.getHorizontalScrollBar()!=null) {
	        	organizedScrollPane.getHorizontalScrollBar().setValue(0);
	        }
		});
        
		processingThread.stopLoading();
	}
}
