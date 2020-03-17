package button.listeners;

import java.awt.event.ActionListener;
import java.util.concurrent.ExecutorService;

import javax.swing.JButton;

import mainwindow.container.MainWindowContainer;


public abstract class DrawingComponentListener implements ActionListener {

	protected MainWindowContainer mainWindowContainer;
	protected ExecutorService executorService;
	protected JButton btnPressed;
	
	
	public DrawingComponentListener(MainWindowContainer mainWindowContainer, ExecutorService executorService) {
		this.setMainWindowContainer(mainWindowContainer);
		this.setExecutorService(executorService);
	}


	public MainWindowContainer getMainWindowContainer() {
		return mainWindowContainer;
	}

	public void setMainWindowContainer(MainWindowContainer mainWindowContainer) {
		this.mainWindowContainer = mainWindowContainer;
	}

	public ExecutorService getExecutorService() {
		return executorService;
	}

	public void setExecutorService(ExecutorService executorService) {
		this.executorService = executorService;
	}
}
