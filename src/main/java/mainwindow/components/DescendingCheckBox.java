package mainwindow.components;

import java.awt.event.ItemEvent;

import javax.swing.SwingUtilities;

import centerpoint.object.CenteredPointType;
import runnables.DateLineProcessor;


public class DescendingCheckBox extends AbstractListeningCheckBox {
	
	private static final long serialVersionUID = 1L;
    private static final String DESCENDING = "Descending";
	

	public DescendingCheckBox(LogMergerWindow logMergerWindow) {
		super(logMergerWindow, DESCENDING);
	}
	
	@Override
	public void itemStateChanged(ItemEvent e) {
		SwingUtilities.invokeLater(()->
			getLogMergerWindow().getWindowHolder().setOrderedText("")
		);
	
		Thread thread = new Thread(new DateLineProcessor(getLogMergerWindow(), this, CenteredPointType.ORDERED_TEXT_AREA));
		thread.start();
	}
}
