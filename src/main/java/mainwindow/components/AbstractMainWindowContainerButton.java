package mainwindow.components;

import java.awt.event.ActionListener;

import javax.swing.JButton;


public abstract class AbstractMainWindowContainerButton extends JButton implements ActionListener {

	private static final long serialVersionUID = 1L;
	private LogMergerWindow logMergerWindow;
	
	public AbstractMainWindowContainerButton(LogMergerWindow logMergerWindow) {
		this(logMergerWindow,null);
	}
	
	public AbstractMainWindowContainerButton(LogMergerWindow logMergerWindow, String title) {
		super(title);
		this.logMergerWindow =  logMergerWindow;
		this.addActionListener(this);
	}

	public LogMergerWindow getLogMergerWindow() {
		return logMergerWindow;
	}
	
}
