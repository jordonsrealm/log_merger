package mainwindow.components;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;

import centerpoint.object.CenteredPointType;
import mainwindow.container.MainWindowContainer;
import threads.ProcessLogo;


public class ClearUnOrderedTextButton extends AbstractMainWindowContainerButton {
	
	private static final long serialVersionUID = 1L;
    private static final String BTN_TITLE = "X";
	private static final Dimension setDim = new Dimension(25,25);
    private static final String CLEAR_TOOLTIP = "Clear Text Area";
	

	public ClearUnOrderedTextButton(LogMergerWindow mainWindow) {
		super(mainWindow, BTN_TITLE);
		this.setPreferredSize(setDim);
		this.setMinimumSize(setDim);
		this.setMaximumSize(setDim);
	    setToolTipText(CLEAR_TOOLTIP);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		MainWindowContainer mainWindowContainer = getLogMergerWindow().getMainWindowContainer();
		ProcessLogo processingThread = new ProcessLogo( mainWindowContainer, CenteredPointType.UN_ORDERED_TEXT_AREA);
		
		processingThread.startProcessing();
		
		mainWindowContainer.getUnOrganizedText().setText("");
		
		processingThread.stopProcessing();
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		int width = getWidth();
		int height = getHeight();
		
		// Set background
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, width, height);
		
		g.setColor(Color.BLACK);
		g.drawLine(6, 6, width - 7, height - 7);
		
		g.drawLine(width - 7, 6, 6, height - 7);
	}
	
}
