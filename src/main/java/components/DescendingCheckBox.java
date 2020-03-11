package components;

import java.awt.event.ItemEvent;

import container.MainWindowContainer;
import factory.CenteredPointType;
import runnables.DateLineProcessor;
import threads.ProcessLogo;


public class DescendingCheckBox extends AbstractListeningCheckBox {
	
	private static final long serialVersionUID = 1L;
	

	public DescendingCheckBox(MainWindowContainer mainWindowContainer) {
		super(mainWindowContainer);
	}
	
	public DescendingCheckBox(MainWindowContainer mainWindowContainer, String title) {
		super(mainWindowContainer, title);
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
