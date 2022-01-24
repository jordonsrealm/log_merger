package button.listeners;

import java.awt.event.ActionEvent;
import java.util.concurrent.ExecutorService;

import centerpoint.object.CenteredPointType;
import mainwindow.holder.MainWindowHolder;
import threads.ProcessLogo;


public class ClearTextAreaListener extends DrawingComponentListener {
	
	
	public ClearTextAreaListener(MainWindowHolder windowHolder, ExecutorService executorService){
		super(windowHolder, executorService);
    }
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		windowHolder.setUnorderedText("");
		
		ProcessLogo processingThread = new ProcessLogo( windowHolder, CenteredPointType.UN_ORDERED_TEXT_AREA);
		processingThread.startProcessing();
		processingThread.stopProcessing();
	}
}
