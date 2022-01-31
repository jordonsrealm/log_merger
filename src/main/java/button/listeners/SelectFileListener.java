package button.listeners;

import java.awt.event.ActionEvent;
import java.util.concurrent.ExecutorService;

import glasspane.drawing.GlassPaneSwingWorker;
import highlighter.UnOrganizedHighlighter;
import mainwindow.components.LogMergerWindow;


public class SelectFileListener extends DrawingComponentListener{

	static UnOrganizedHighlighter myHighlightPainter;
	private GlassPaneSwingWorker worker;


	public SelectFileListener( LogMergerWindow logMergerWindow, ExecutorService executorService, String highlightHexColor) {
		super(logMergerWindow, executorService);
		worker = new GlassPaneSwingWorker(logMergerWindow, highlightHexColor);
	}

	public void actionPerformed(ActionEvent e) {
		
		if(!getLogMergerWindow().getWindowHolder().getFileNameInputText().isEmpty()) {
			worker.execute();
		}
	}
}
