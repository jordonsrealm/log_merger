package listeners;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.ExecutorService;

import container.MainWindowContainer;
import factory.CenteredPointFactory;
import factory.CenteredPointType;
import threads.ProcessLogo;


public class ClearTextAreaListener implements ActionListener{

	private MainWindowContainer mainWindowContainer;
	private ExecutorService executorService;
	
	
	public ClearTextAreaListener(MainWindowContainer mainWindowContainer, ExecutorService executorService){
        this.mainWindowContainer = mainWindowContainer;
        this.executorService = executorService;
    }
	
	@Override
	public void actionPerformed(ActionEvent e) {
		Point centerPoint = CenteredPointFactory.getCenteredPoint( CenteredPointType.NOT_ORDERED, mainWindowContainer).getCenteredPoint();
		ProcessLogo processingThread = new ProcessLogo( mainWindowContainer.getGlassPane(), centerPoint);
		
		processingThread.startProcessing();
		
		mainWindowContainer.getUnOrganizedText().setText("");
		
		processingThread.stopProcessing();
	}
}
