package logmerger.frame.component.swingworker;

import java.util.List;
import java.util.concurrent.ExecutionException;

import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import logmerger.frame.LogMergerFrame;
import logmerger.frame.component.holder.TextHolder;
import logmerger.frame.component.holder.WindowComponentHolder;
import logmerger.frame.component.swingworker.publish.action.PublishedAction;
import logmerger.frame.datedline.DatedLine;
import logmerger.frame.datedline.organizer.DateLineOrganizer;
import logmerger.frame.progressdisplay.ProgressDisplay;


public class DateLineWorker extends SwingWorker<String, String> {
	private static final Logger logger = LoggerFactory.getLogger(DateLineWorker.class);
	private LogMergerFrame logMergerWindow;
	private ProgressDisplay progressDisplay;
	
	
	public DateLineWorker(LogMergerFrame logMergerWindow) {
		this.setLogMergerWindow(logMergerWindow);
    	this.setProgressDisplay(new ProgressDisplay(getLogMergerWindow()));
	}

	@Override
	protected String doInBackground() throws Exception {
		getProgressDisplay().initiate();
		
		WindowComponentHolder mainWindowContainer = getLogMergerWindow().getWindowComponentHolder();
		
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
			
			publish(PublishedAction.FINISHED.action());
		}
		
		return convertedString;
	}
	
	@Override
	protected void process(List<String> chunks) {
		String currentState = chunks.get(chunks.size()-1);
		getProgressDisplay().setCurrentState(currentState);
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
        
		getProgressDisplay().terminate();
	}

	public LogMergerFrame getLogMergerWindow() {
		return logMergerWindow;
	}

	public void setLogMergerWindow(LogMergerFrame logMergerWindow) {
		this.logMergerWindow = logMergerWindow;
	}

	public ProgressDisplay getProgressDisplay() {
		return progressDisplay;
	}

	public void setProgressDisplay(ProgressDisplay loadingIconGraphicsHandler) {
		this.progressDisplay = loadingIconGraphicsHandler;
	}

}
