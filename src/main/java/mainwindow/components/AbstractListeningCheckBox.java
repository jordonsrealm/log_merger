package mainwindow.components;

import java.awt.event.ItemListener;

import javax.swing.JCheckBox;


public abstract class AbstractListeningCheckBox extends JCheckBox implements ItemListener{

	private static final long serialVersionUID = 1L;
	protected transient LogMergerWindow logMergerWindow;
	
	
	protected AbstractListeningCheckBox(LogMergerWindow logMergerWindow) {
		this(logMergerWindow, "");
	}
	
	protected AbstractListeningCheckBox(LogMergerWindow logMergerWindow, String title) {
		super(title);
		this.setLogMergerWindow(logMergerWindow);
		this.addItemListener(this);
	}

	public LogMergerWindow getLogMergerWindow() {
		return logMergerWindow;
	}

	public void setLogMergerWindow(LogMergerWindow windowHolder) {
		this.logMergerWindow = windowHolder;
	}
}
