package window.components;

import java.awt.event.ActionListener;

import javax.swing.JButton;

import logmerger.frame.LogMergerFrame;
import window.holder.WindowComponentHolder;


public abstract class AbstractMainWindowContainerButton extends JButton implements ActionListener {

	private static final long serialVersionUID = 1L;
	private LogMergerFrame logMergerWindow;
	
	protected AbstractMainWindowContainerButton(LogMergerFrame logMergerWindow) {
		this(logMergerWindow,null);
	}
	
	protected AbstractMainWindowContainerButton(LogMergerFrame logMergerWindow, String title) {
		super(title);
		this.logMergerWindow =  logMergerWindow;
		this.addActionListener(this);
	}

	public LogMergerFrame getLogMergerWindow() {
		return logMergerWindow;
	}
	
	public WindowComponentHolder getWindowHolder() {
		return getLogMergerWindow().getWindowComponentHolder();
	}
}
