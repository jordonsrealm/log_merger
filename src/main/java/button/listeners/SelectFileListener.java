package button.listeners;

import java.awt.event.ActionEvent;
import java.util.concurrent.ExecutorService;

import glasspane.drawing.GlassPaneSwingWorker;
import highlighter.UnOrganizedHighlighter;
import mainwindow.holder.MainWindowHolder;


public class SelectFileListener extends DrawingComponentListener{

	static UnOrganizedHighlighter myHighlightPainter;
	private GlassPaneSwingWorker worker;


	public SelectFileListener( MainWindowHolder mainWindowContainer, ExecutorService executorService, String highlightHexColor) {
		super(mainWindowContainer, executorService);
		worker = new GlassPaneSwingWorker(this.windowHolder, highlightHexColor);
	}

	public void actionPerformed(ActionEvent e) {
		
		if(!this.windowHolder.getTxtHolder().getFileNameInputTextField().getText().isEmpty()) {
			worker.execute();
		}
	}
}
