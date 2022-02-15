package mainwindow.components;

import java.awt.event.ItemEvent;
import javax.swing.SwingUtilities;

import runnables.DateLineProcessor;

public class DescendingCheckBox extends ListeningCheckBox{
	
	private static final long serialVersionUID = 1L;

	public DescendingCheckBox(LogMergerWindow logMergerWindow, String title) {
		super(logMergerWindow, title);
		addItemListener(this);
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
