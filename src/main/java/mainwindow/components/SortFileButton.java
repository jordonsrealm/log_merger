package mainwindow.components;

import java.awt.event.ActionEvent;

import javax.swing.SwingUtilities;

import centerpoint.object.CenteredPointType;
import runnables.DateLineProcessor;


public class SortFileButton extends AbstractMainWindowContainerButton {
	
	private static final long serialVersionUID = 1L;
    private static final String BTN_TITLE = "Sort";
	
	
	public SortFileButton(LogMergerWindow mainWindow) {
		super(mainWindow, BTN_TITLE);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		SwingUtilities.invokeLater(()->
			getLogMergerWindow().getWindowHolder().setOrderedText("")
		);
		
		Thread thread = new Thread(new DateLineProcessor(getLogMergerWindow(), this, CenteredPointType.ORDERED_TEXT_AREA));
		thread.start();
	}
}
