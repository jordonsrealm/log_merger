package mainwindow.components;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

//import java.awt.event.ActionListener;

import javax.swing.JCheckBox;
import javax.swing.SwingUtilities;

import runnables.DateLineProcessor;


public class ListeningCheckBox extends JCheckBox implements ItemListener{

	private static final long serialVersionUID = 1L;
	protected transient LogMergerWindow logMergerWindow;
	
	
	public ListeningCheckBox(LogMergerWindow logMergerWindow) {
		this(logMergerWindow, "");
	}
	
	public ListeningCheckBox(LogMergerWindow logMergerWindow, String title) {
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

	@Override
	public void itemStateChanged(ItemEvent e) {
		SwingUtilities.invokeLater(()->
			getLogMergerWindow().getWindowHolder().setOrderedText("")
		);

		Thread thread = new Thread(new DateLineProcessor(getLogMergerWindow(), this));
		thread.start();
	}
}
