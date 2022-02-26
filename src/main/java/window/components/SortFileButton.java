package window.components;

import java.awt.event.ActionEvent;

import logmerger.frame.LogMergerFrame;
import swingworker.DateLineWorker;


public class SortFileButton extends AbstractMainWindowContainerButton {
	
	private static final long serialVersionUID = 1L;
    private static final String BTN_TITLE = "Sort";
	
	
	public SortFileButton(LogMergerFrame mainWindow) {
		super(mainWindow, BTN_TITLE);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		DateLineWorker worker = new DateLineWorker(getLogMergerWindow());
		worker.execute();
	}
}
