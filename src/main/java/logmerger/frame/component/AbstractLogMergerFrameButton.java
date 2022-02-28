package logmerger.frame.component;

import java.awt.event.ActionListener;

import javax.swing.JButton;

import logmerger.frame.LogMergerFrame;
import logmerger.frame.component.holder.WindowComponentHolder;


public abstract class AbstractLogMergerFrameButton extends JButton implements ActionListener {

	private static final long serialVersionUID = 1L;
	private LogMergerFrame logMergerWindow;
	
	protected AbstractLogMergerFrameButton(LogMergerFrame logMergerWindow) {
		this(logMergerWindow,null);
	}
	
	protected AbstractLogMergerFrameButton(LogMergerFrame logMergerWindow, String title) {
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
