package window.components;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JCheckBox;

import logmerger.frame.LogMergerFrame;
import swingworker.DateLineWorker;


public class ListeningCheckBox extends JCheckBox implements ActionListener{

	private static final long serialVersionUID = 1L;
	protected transient LogMergerFrame logMergerWindow;
	
	
	public ListeningCheckBox(LogMergerFrame logMergerWindow) {
		this(logMergerWindow, "");
	}
	
	public ListeningCheckBox(LogMergerFrame logMergerWindow, String title) {
		super(title);
		this.setLogMergerWindow(logMergerWindow);
		this.addActionListener(this);
	}

	public LogMergerFrame getLogMergerWindow() {
		return logMergerWindow;
	}

	public void setLogMergerWindow(LogMergerFrame windowHolder) {
		this.logMergerWindow = windowHolder;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		DateLineWorker worker = new DateLineWorker(getLogMergerWindow());
		worker.execute();
	}
}
