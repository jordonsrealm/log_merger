package threads;

import java.util.List;
import java.util.concurrent.ExecutionException;

import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;

import date.line.organizer.DateLineOrganizer;
import mainwindow.components.LogMergerWindow;
import mainwindow.components.holder.TextHolder;
import mainwindow.holder.MainWindowHolder;

public class DateLineWorker extends SwingWorker<String, Integer> {
	private LogMergerWindow logMergerWindow;
	private LoadingIcon loadinIcon;
	private Thread loadingThread;
	
	
	public DateLineWorker(LogMergerWindow logMergerWindow) {
		this.setLogMergerWindow(logMergerWindow);
		
		loadinIcon = new LoadingIcon( getLogMergerWindow());
		loadinIcon.initialize();
		
		loadingThread = new Thread(loadinIcon);
		loadingThread.start();
	}

	@Override
	protected String doInBackground() throws Exception {
		MainWindowHolder mainWindowContainer = getLogMergerWindow().getWindowHolder();
		
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
		TextHolder holder = getLogMergerWindow().getWindowHolder().getTxtHolder();
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
        
		loadinIcon.terminate();
	}

	public LogMergerWindow getLogMergerWindow() {
		return logMergerWindow;
	}

	public void setLogMergerWindow(LogMergerWindow logMergerWindow) {
		this.logMergerWindow = logMergerWindow;
	}

}
