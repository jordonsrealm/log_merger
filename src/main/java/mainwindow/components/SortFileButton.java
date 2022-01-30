package mainwindow.components;

import java.awt.event.ActionEvent;
import centerpoint.object.CenteredPointType;
import mainwindow.holder.MainWindowHolder;
import runnables.DateLineProcessor;
import threads.LoadingIcon;


public class SortFileButton extends AbstractMainWindowContainerButton {

	private static final long serialVersionUID = 1L;
    private static final String BTN_TITLE = "Sort";
	
	
	public SortFileButton(LogMergerWindow mainWindow) {
		super(mainWindow, BTN_TITLE);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		setEnabled(false);
		MainWindowHolder holder = getLogMergerWindow().getWindowHolder();
		holder.setOrderedText("");

    	LoadingIcon loadingIconThread = new LoadingIcon( holder, CenteredPointType.ORDERED_TEXT_AREA);
    	loadingIconThread.startLoading();
    	new DateLineProcessor(getLogMergerWindow()).run();
    	loadingIconThread.stopLoading();
    	
        setEnabled(true);
	}
}
