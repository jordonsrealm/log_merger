package mainwindow.components;

import java.awt.event.ItemEvent;

import centerpoint.object.CenteredPointType;
import mainwindow.container.MainWindowContainer;
import runnables.DateLineProcessor;
import threads.ProcessLogo;


public class DescendingCheckBox extends AbstractListeningCheckBox {
	
	private static final long serialVersionUID = 1L;
    private static final String DESCENDING = "Descending";
	

	public DescendingCheckBox(MainWindowContainer mainWindowContainer) {
		super(mainWindowContainer, DESCENDING);
	}
	
	@Override
	public void itemStateChanged(ItemEvent e) {
    	ProcessLogo processingThread = new ProcessLogo( mainWindowContainer, CenteredPointType.ORDERED_TEXT_AREA);
		mainWindowContainer.getOrganizedText().setText("");
		processingThread.startProcessing();
		DateLineProcessor dateLinesRunnable = new DateLineProcessor(mainWindowContainer);
		dateLinesRunnable.run();
		processingThread.stopProcessing();
	}
}
