package mainwindow.components;

import java.awt.Dimension;
import java.awt.event.ActionEvent;


public class ClearUnOrderedTextButton extends AbstractMainWindowContainerButton {
	
	private static final long serialVersionUID = 1L;
    private static final String BTN_TITLE = "X";
	private static final Dimension setDim = new Dimension(100,25);
    private static final String CLEAR_TOOLTIP = "Clear Text";
    private LogMergerWindow logMergerWindow;
	

	public ClearUnOrderedTextButton(LogMergerWindow mainWindow) {
		super(mainWindow, BTN_TITLE);
		this.setPreferredSize(setDim);
		this.setMinimumSize(setDim);
		this.setMaximumSize(setDim);
	    setToolTipText(CLEAR_TOOLTIP);
	    setText(CLEAR_TOOLTIP);
	    logMergerWindow = mainWindow;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		logMergerWindow.getWindowHolder().setUnorderedText("");
	}
	
}
