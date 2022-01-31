package button.listeners;

import java.awt.event.ActionListener;
import java.util.concurrent.ExecutorService;

import javax.swing.JButton;

import mainwindow.components.LogMergerWindow;


public abstract class DrawingComponentListener implements ActionListener {

	protected LogMergerWindow logMergerWindow;
	protected ExecutorService executorService;
	protected JButton btnPressed;
	
	protected DrawingComponentListener(LogMergerWindow logMergerWindow, ExecutorService executorService) {
		this.logMergerWindow = logMergerWindow;
		this.executorService = executorService;
	}

	public ExecutorService getExecutorService() {
		return this.executorService;
	}

	public void setExecutorService(ExecutorService executorService) {
		this.executorService = executorService;
	}

	public LogMergerWindow getLogMergerWindow() {
		return logMergerWindow;
	}

	public void setLogMergerWindow(LogMergerWindow logMergerWindow) {
		this.logMergerWindow = logMergerWindow;
	}
}
