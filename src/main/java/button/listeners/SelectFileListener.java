package button.listeners;

import java.awt.event.ActionEvent;
import java.util.concurrent.ExecutorService;

import glasspane.drawing.GlassPaneSwingWorker;
import mainwindow.components.LogMergerWindow;


public class SelectFileListener extends DrawingComponentListener{

	private GlassPaneSwingWorker worker;


	public SelectFileListener( LogMergerWindow logMergerWindow, ExecutorService executorService) {
		super(logMergerWindow, executorService);
		worker = new GlassPaneSwingWorker(logMergerWindow);
	}

	public void actionPerformed(ActionEvent e) {
		
		if(!getLogMergerWindow().getWindowHolder().getFileNameInputText().isEmpty()) {
			worker.execute();
		}
	}
}
