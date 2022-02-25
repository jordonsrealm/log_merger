package window.components;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JCheckBox;

import swingworkers.DateLineWorker;


public class ListeningCheckBox extends JCheckBox implements ActionListener{

	private static final long serialVersionUID = 1L;
	protected transient LogMergerWindow logMergerWindow;
	
	
	public ListeningCheckBox(LogMergerWindow logMergerWindow) {
		this(logMergerWindow, "");
	}
	
	public ListeningCheckBox(LogMergerWindow logMergerWindow, String title) {
		super(title);
		this.setLogMergerWindow(logMergerWindow);
		this.addActionListener(this);
	}

	public LogMergerWindow getLogMergerWindow() {
		return logMergerWindow;
	}

	public void setLogMergerWindow(LogMergerWindow windowHolder) {
		this.logMergerWindow = windowHolder;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		DateLineWorker worker = new DateLineWorker(getLogMergerWindow());
		worker.execute();
	}
}
