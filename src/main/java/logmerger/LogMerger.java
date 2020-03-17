package logmerger;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.swing.UIManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mainwindow.components.LogMergerWindow;

public class LogMerger {

	private static final Logger logger = LoggerFactory.getLogger(LogMerger.class);
	static int availableCores;
	static ExecutorService logMergerExecutor;
	static LogMergerWindow logMergerWindow;
	
	
	private LogMerger() {}
	
	
	public static void main(String[] args) {

		try {
			UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName()); }
		catch (Exception e) {
			logger.error("Unable to set cross platform look and feel UI", e); 
		}
		
		availableCores = Runtime.getRuntime().availableProcessors();
		
		logMergerExecutor = Executors.newFixedThreadPool(availableCores);
		logMergerWindow   = new LogMergerWindow(logMergerExecutor);
	}
}
