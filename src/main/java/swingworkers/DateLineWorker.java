package swingworkers;

import java.util.List;
import java.util.concurrent.ExecutionException;

import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;

import datedline.organizer.DateLineOrganizer;
import loadingicon.LoadingIcon;
import logmerger.frame.LogMergerFrame;
import window.components.holder.TextHolder;
import window.holder.WindowComponentHolder;

public class DateLineWorker extends SwingWorker<String, Integer> {
	private LogMergerFrame logMergerWindow;
	private LoadingIcon loadinIcon;
	private Thread loadingThread;
	
	
	public DateLineWorker(LogMergerFrame logMergerWindow) {
		this.setLogMergerWindow(logMergerWindow);
		
		loadinIcon = new LoadingIcon( getLogMergerWindow());
		loadinIcon.initializeLoadingIcon();
		
		loadingThread = new Thread(loadinIcon);
		loadingThread.start();
	}

	@Override
	protected String doInBackground() throws Exception {
		WindowComponentHolder mainWindowContainer = getLogMergerWindow().getWindowComponentHolder();
		
		String convertedString = "";
		if(!(mainWindowContainer.getUnorderedText().isEmpty() && mainWindowContainer.getUnorderedText().isBlank())) {
			String minDateString = mainWindowContainer.getMinDateText();
			String maxDateString = mainWindowContainer.getMaxDateText();
		
			convertedString = new DateLineOrganizer(mainWindowContainer).orderDateLines(minDateString, maxDateString);
		}
		
		return convertedString;
	}

	@Override
	protected void process(List<Integer> chunks) {
		
	}

	@Override
	protected void done() {
		TextHolder holder = getLogMergerWindow().getWindowComponentHolder().getTxtHolder();
		JTextArea orderTextArea = holder.getOrderedTextArea();
		JScrollPane organizedScrollPane = holder.getUnOrderedScrollPane();
		
		SwingUtilities.invokeLater(()->{
			try {
				orderTextArea.setText(get());
				orderTextArea.setCaretPosition(0);
				
				if(organizedScrollPane != null && organizedScrollPane.getHorizontalScrollBar()!=null) {
					organizedScrollPane.getHorizontalScrollBar().setValue(0);
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (ExecutionException e) {
				e.printStackTrace();
			}
		});
        
		loadinIcon.terminateLoadingIcon();
	}

	public LogMergerFrame getLogMergerWindow() {
		return logMergerWindow;
	}

	public void setLogMergerWindow(LogMergerFrame logMergerWindow) {
		this.logMergerWindow = logMergerWindow;
	}

}
