package swingworker;

import java.util.concurrent.ExecutionException;

import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import datedline.organizer.DateLineOrganizer;
import loadingicon.LoadingIcon;
import logmerger.frame.LogMergerFrame;
import window.component.holder.TextHolder;
import window.holder.WindowComponentHolder;


public class DateLineWorker extends SwingWorker<String, Integer> {
	private static final Logger logger = LoggerFactory.getLogger(DateLineWorker.class);
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
			} catch (InterruptedException ie) {
				logger.error("Error caught, reinterruping current Thread again...", ie);
				Thread.currentThread().interrupt();
			} catch (ExecutionException ee) {
				logger.error("Execution exception!...", ee);
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
