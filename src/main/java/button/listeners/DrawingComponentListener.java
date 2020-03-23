package button.listeners;

import java.awt.event.ActionListener;
import java.util.concurrent.ExecutorService;

import javax.swing.JButton;

import mainwindow.holder.MainWindowHolder;


public abstract class DrawingComponentListener implements ActionListener {

	protected MainWindowHolder windowHolder;
	protected ExecutorService executorService;
	protected JButton btnPressed;
	
	
	public DrawingComponentListener(MainWindowHolder windowHolder, ExecutorService executorService) {
		this.windowHolder = windowHolder;
		this.executorService = executorService;
	}


	public MainWindowHolder getWindowHolder() {
		return this.windowHolder;
	}


	public void setWindowHolder(MainWindowHolder windowHolder) {
		this.windowHolder = windowHolder;
	}


	public ExecutorService getExecutorService() {
		return this.executorService;
	}


	public void setExecutorService(ExecutorService executorService) {
		this.executorService = executorService;
	}
}
