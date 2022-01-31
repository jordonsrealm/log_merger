package runnables;

import javax.swing.AbstractButton;
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
	private AbstractButton button;
	private CenteredPointType centeredPointType;
	
	public DateLineProcessor(LogMergerWindow logMergerWindow, AbstractButton button, CenteredPointType centeredPointType) {
		setLogMergerWindow(logMergerWindow);
		setButton(button);
		setCenteredPointType(centeredPointType);
	}
	
	@Override
	public void run() {
		MainWindowHolder mainWindowContainer = getLogMergerWindow().getWindowHolder();

		String minDateString = mainWindowContainer.getMinDateText();
		String maxDateString = mainWindowContainer.getMaxDateText();
		
		button.setEnabled(false);
    	LoadingIcon loadingIcon = new LoadingIcon( getLogMergerWindow(), centeredPointType);
		loadingIcon.startLoading();
		
		Thread loadingThread = new Thread(loadingIcon);
		loadingThread.start();
		
		String completeTextString = new DateLineOrganizer(mainWindowContainer).orderDateLines(minDateString, maxDateString);
		JTextArea orderTextArea = mainWindowContainer.getTxtHolder().getOrderedTextArea();
		
		SwingUtilities.invokeLater(()->{
			orderTextArea.setText(completeTextString);
			orderTextArea.setCaretPosition(0);
	        
	        if(organizedScrollPane != null && organizedScrollPane.getHorizontalScrollBar()!=null) {
	        	organizedScrollPane.getHorizontalScrollBar().setValue(0);
	        }
		});
        
		loadingIcon.stopLoading();
		button.setEnabled(true);
	}

	public AbstractButton getButton() {
		return button;
	}

	public void setButton(AbstractButton button) {
		this.button = button;
	}

	public LogMergerWindow getLogMergerWindow() {
		return logMergerWindow;
	}

	public void setLogMergerWindow(LogMergerWindow logMergerWindow) {
		this.logMergerWindow = logMergerWindow;
	}

	public CenteredPointType getCenteredPointType() {
		return centeredPointType;
	}

	public void setCenteredPointType(CenteredPointType centeredPointType) {
		this.centeredPointType = centeredPointType;
	}
}
