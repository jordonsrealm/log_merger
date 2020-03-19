package mainwindow.components;

import java.awt.event.ItemEvent;

import centerpoint.object.CenteredPointType;
import mainwindow.holder.MainWindowHolder;
import runnables.DateLineProcessor;
import threads.ProcessLogo;


public class DescendingCheckBox extends AbstractListeningCheckBox {
	
	private static final long serialVersionUID = 1L;
    private static final String DESCENDING = "Descending";
	

	public DescendingCheckBox(MainWindowHolder windowHolder) {
		super(windowHolder, DESCENDING);
	}
	
	@Override
	public void itemStateChanged(ItemEvent e) {
    	ProcessLogo processingThread = new ProcessLogo( windowHolder, CenteredPointType.ORDERED_TEXT_AREA);
		windowHolder.getTxtHolder().getOrganizedText().setText("");
		processingThread.startProcessing();
		DateLineProcessor dateLinesRunnable = new DateLineProcessor(windowHolder);
		dateLinesRunnable.run();
		processingThread.stopProcessing();
	}
}
