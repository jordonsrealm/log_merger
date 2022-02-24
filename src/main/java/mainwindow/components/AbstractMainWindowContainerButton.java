package mainwindow.components;

import java.awt.event.ActionListener;

import javax.swing.JButton;

import mainwindow.holder.WindowComponentHolder;


public abstract class AbstractMainWindowContainerButton extends JButton implements ActionListener {

	private static final long serialVersionUID = 1L;
	private LogMergerWindow logMergerWindow;
	
	protected AbstractMainWindowContainerButton(LogMergerWindow logMergerWindow) {
		this(logMergerWindow,null);
	}
	
	protected AbstractMainWindowContainerButton(LogMergerWindow logMergerWindow, String title) {
		super(title);
		this.logMergerWindow =  logMergerWindow;
		this.addActionListener(this);
	}

	public LogMergerWindow getLogMergerWindow() {
		return logMergerWindow;
	}
	
	public WindowComponentHolder getWindowHolder() {
		return getLogMergerWindow().getWindowComponentHolder();
	}
}
