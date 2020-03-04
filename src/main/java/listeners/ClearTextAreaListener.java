package listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.ExecutorService;

import container.MainWindowContainer;
import factory.CenteredPointType;
import threads.ProcessLogo;


public class ClearTextAreaListener implements ActionListener{

	private MainWindowContainer mainWindowContainer;
	//private ExecutorService executorService;
	
	
	public ClearTextAreaListener(MainWindowContainer mainWindowContainer, ExecutorService executorService){
        this.mainWindowContainer = mainWindowContainer;
        //this.executorService = executorService;
    }
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		ProcessLogo processingThread = new ProcessLogo( mainWindowContainer, CenteredPointType.UN_ORDERED_TEXT_AREA);
		
		processingThread.startProcessing();
		
		mainWindowContainer.getUnOrganizedText().setText("");
		
		processingThread.stopProcessing();
	}
}
