package mainwindow.components;

import java.awt.event.ItemEvent;

import centerpoint.object.CenteredPointType;
import mainwindow.holder.MainWindowHolder;
import runnables.DateLineProcessor;
import threads.LoadingIcon;


public class DescendingCheckBox extends AbstractListeningCheckBox {
	
	private static final long serialVersionUID = 1L;
    private static final String DESCENDING = "Descending";
	

	public DescendingCheckBox(LogMergerWindow logMergerWindow) {
		super(logMergerWindow, DESCENDING);
	}
	
	@Override
	public void itemStateChanged(ItemEvent e) {
		setEnabled(false);
		MainWindowHolder holder = getLogMergerWindow().getWindowHolder();
		holder.setOrderedText("");
		
    	LoadingIcon processingThread = new LoadingIcon( holder, CenteredPointType.ORDERED_TEXT_AREA);
		processingThread.startLoading();
		new DateLineProcessor(getLogMergerWindow()).run();
		processingThread.stopLoading();
		
		setEnabled(true);
	}
}
