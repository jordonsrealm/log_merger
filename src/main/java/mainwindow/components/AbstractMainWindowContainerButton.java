package mainwindow.components;

import java.awt.event.ActionListener;

import javax.swing.JButton;

import mainwindow.container.MainWindowContainer;


public abstract class AbstractMainWindowContainerButton extends JButton implements ActionListener {

	private static final long serialVersionUID = 1L;
	private LogMergerWindow mainWindow;
	
	public AbstractMainWindowContainerButton(LogMergerWindow mainWindow) {
		this(mainWindow,null);
	}
	
	public AbstractMainWindowContainerButton(LogMergerWindow mainWindow, String title) {
		super(title);
		this.mainWindow =  mainWindow;
		this.addActionListener(this);
	}

	public LogMergerWindow getMainWindow() {
		return mainWindow;
	}

	public MainWindowContainer getMainWindowContainer() {
		return mainWindow.getMainWindowContainer();
	}
	
}
