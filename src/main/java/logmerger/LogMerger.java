package logmerger;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import configuration.ConfigurationGetter;
import logmerger.frame.LogMergerFrame;

public class LogMerger {

	private static final Logger logger = LoggerFactory.getLogger(LogMerger.class);
	private static final ExecutorService logMergerExecutor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
	
	private LogMerger() {}
	
	public static void main(String[] args) {

		if(ConfigurationGetter.instance().useLookAndFeel()) {
			try {
				UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName()); }
			catch (Exception e) {
				logger.error("Unable to set cross platform look and feel UI", e); 
			}
		}
		
		SwingUtilities.invokeLater(()->{
			new LogMergerFrame(logMergerExecutor);
		});
	}
}
