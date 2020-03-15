package button.listeners;

import java.awt.event.ActionEvent;
import java.util.concurrent.ExecutorService;

import centerpoint.object.CenteredPointType;
import mainwindow.container.MainWindowContainer;
import threads.ProcessLogo;


public class ClearTextAreaListener extends DrawingComponentListener {
	
	
	public ClearTextAreaListener(MainWindowContainer mainWindowContainer, ExecutorService executorService){
		super(mainWindowContainer, executorService);
    }
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		ProcessLogo processingThread = new ProcessLogo( mainWindowContainer, CenteredPointType.UN_ORDERED_TEXT_AREA);
		
		processingThread.startProcessing();
		
		mainWindowContainer.getUnOrganizedText().setText("");
		
		processingThread.stopProcessing();
	}
}