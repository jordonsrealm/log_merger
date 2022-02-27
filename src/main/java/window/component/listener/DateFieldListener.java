package window.component.listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import logmerger.frame.LogMergerFrame;
import swingworker.DateLineWorker;


public class DateFieldListener implements ActionListener{

	private LogMergerFrame logMergerFrame;
	
	
	public DateFieldListener(LogMergerFrame logMergerFrame) {
		this.logMergerFrame = logMergerFrame;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		DateLineWorker worker = new DateLineWorker(getLogMergerFrame());
		worker.execute();
	}

	public LogMergerFrame getLogMergerFrame() {
		return logMergerFrame;
	}
}
