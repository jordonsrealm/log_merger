package mainwindow.components;

import java.awt.event.ActionEvent;

import centerpoint.object.CenteredPointType;
import mainwindow.holder.MainWindowHolder;
import runnables.DateLineProcessor;
import threads.ProcessLogo;


public class MergeFileButton extends AbstractMainWindowContainerButton {

	private static final long serialVersionUID = 1L;
    private static final String BTN_TITLE = "Sort";
	
	
	public MergeFileButton(LogMergerWindow mainWindow) {
		super(mainWindow, BTN_TITLE);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		MainWindowHolder mainWindowContainer = getLogMergerWindow().getWindowHolder();
    	
		DateLineProcessor processor = new DateLineProcessor(mainWindowContainer);
    	
    	setEnabled(false);
    	
    	mainWindowContainer.getTxtHolder().getOrderedText().setText("");
    	
    	ProcessLogo processingThread = new ProcessLogo( mainWindowContainer, CenteredPointType.ORDERED_TEXT_AREA);
    	
    	processingThread.startProcessing();
    	
    	processor.run();
    	
    	processingThread.stopProcessing();
        
        setEnabled(true);
	}
}
