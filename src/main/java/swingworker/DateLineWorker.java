package swingworker;

import java.util.List;
import java.util.concurrent.ExecutionException;

import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import datedline.DatedLine;
import datedline.organizer.DateLineOrganizer;
import loadingicongraphicshandler.LoadingIconGraphicsHandler;
import logmerger.frame.LogMergerFrame;
import swingworker.action.PublishedAction;
import window.component.holder.TextHolder;
import window.holder.WindowComponentHolder;


public class DateLineWorker extends SwingWorker<String, String> {
	private static final Logger logger = LoggerFactory.getLogger(DateLineWorker.class);
	private LogMergerFrame logMergerWindow;
	private LoadingIconGraphicsHandler loadingIconGraphicsHandler;
	
	
	public DateLineWorker(LogMergerFrame logMergerWindow) {
		this.setLogMergerWindow(logMergerWindow);
    	this.setLoadingIconGraphicsHandler(new LoadingIconGraphicsHandler(getLogMergerWindow()));
	}

	@Override
	protected String doInBackground() throws Exception {
		getLoadingIconGraphicsHandler().setVisible(true);
		getLoadingIconGraphicsHandler().handleListener(true);
		getLoadingIconGraphicsHandler().setCurrentState("");
		
		WindowComponentHolder mainWindowContainer = getLogMergerWindow().getWindowComponentHolder();
		
		publish("Working on creating dated lines...");
		String convertedString = "";
		if(!(mainWindowContainer.getUnorderedText().isEmpty() && mainWindowContainer.getUnorderedText().isBlank())) {
			String minDateString = mainWindowContainer.getMinDateText();
			String maxDateString = mainWindowContainer.getMaxDateText();
			
			DateLineOrganizer dateLineOrganizer = new DateLineOrganizer(mainWindowContainer);
			
			publish(PublishedAction.CREATING_DATE_LINES.action());
			List<DatedLine> lines = dateLineOrganizer.getDatedLinesUsingFormat();
			
			publish(PublishedAction.DATE_BOUNDS.action());
			lines = dateLineOrganizer.handleDateRanges(lines, minDateString, maxDateString);
			
			publish(PublishedAction.ORDERING.action());
			lines = dateLineOrganizer.getOrdereDatedLines(lines);
			
			publish(PublishedAction.UPDATING_DATE_LINES.action());
	    	dateLineOrganizer.updateMainWindowHolderWithDatedLines(lines);
			
	    	publish(PublishedAction.TO_FULL_TEXT.action());
			convertedString = dateLineOrganizer.returnCompleteTextFromDatedLines(lines);
		}
		publish(PublishedAction.FINISHED.action());
		
		return convertedString;
	}
	
	@Override
	protected void process(List<String> chunks) {
		String currentState = chunks.get(chunks.size()-1);
		getLoadingIconGraphicsHandler().setCurrentState(currentState);
	}

	@Override
	protected void done() {
		TextHolder holder = getLogMergerWindow().getWindowComponentHolder().getTxtHolder();
		JTextArea orderTextArea = holder.getOrderedTextArea();
		JScrollPane organizedScrollPane = holder.getUnOrderedScrollPane();
		
		SwingUtilities.invokeLater(()->{
			try {
				String completedText = get();
				orderTextArea.setText(completedText);
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
        
		getLoadingIconGraphicsHandler().setVisible(false);
		getLoadingIconGraphicsHandler().handleListener(false);
	}

	public LogMergerFrame getLogMergerWindow() {
		return logMergerWindow;
	}

	public void setLogMergerWindow(LogMergerFrame logMergerWindow) {
		this.logMergerWindow = logMergerWindow;
	}

	public LoadingIconGraphicsHandler getLoadingIconGraphicsHandler() {
		return loadingIconGraphicsHandler;
	}

	public void setLoadingIconGraphicsHandler(LoadingIconGraphicsHandler loadingIconGraphicsHandler) {
		this.loadingIconGraphicsHandler = loadingIconGraphicsHandler;
	}

}
