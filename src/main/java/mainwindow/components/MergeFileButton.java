package mainwindow.components;

import java.awt.Graphics;
import java.awt.event.ActionEvent;

import centerpoint.object.CenteredPointType;
import mainwindow.container.MainWindowContainer;
import runnables.DateLineProcessor;
import threads.ProcessLogo;


public class MergeFileButton extends AbstractMainWindowContainerButton {

	private static final long serialVersionUID = 1L;
    private static final String BTN_TITLE = "Merge Files";
	
	
	public MergeFileButton(LogMergerWindow mainWindow) {
		super(mainWindow, BTN_TITLE);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		MainWindowContainer mainWindowContainer = getLogMergerWindow().getMainWindowContainer();
    	
		DateLineProcessor processor = new DateLineProcessor(mainWindowContainer);
    	
    	setEnabled(false);
    	
    	mainWindowContainer.getOrganizedText().setText("");
    	
    	ProcessLogo processingThread = new ProcessLogo( mainWindowContainer, CenteredPointType.ORDERED_TEXT_AREA);
    	
    	processingThread.startProcessing();
    	
    	processor.run();
    	
    	processingThread.stopProcessing();
        
        setEnabled(true);
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
	}

}
