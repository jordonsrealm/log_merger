package listeners;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import container.MainWindowContainer;
import factory.CenteredPointFactory;
import factory.CenteredPointType;
import threads.ProcessingLogoThread;


public class ClearTextAreaListener implements ActionListener{

	private MainWindowContainer mainWindowContainer;
	
	
	public ClearTextAreaListener(MainWindowContainer mainWindowContainer){
        this.mainWindowContainer = mainWindowContainer;
    }
	
	@Override
	public void actionPerformed(ActionEvent e) {
		Point centerPoint = CenteredPointFactory.getCenteredPoint( CenteredPointType.NOT_ORDERED, mainWindowContainer).getCenteredPoint();
		ProcessingLogoThread processingThread = new ProcessingLogoThread( mainWindowContainer.getGlassPane(), centerPoint);
		processingThread.startProcessing();
		
		mainWindowContainer.getUnOrganizedText().setText("");
		
		processingThread.stopProcessing();
	}
}
