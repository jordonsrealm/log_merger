package window.components;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.SwingUtilities;

import logmerger.frame.LogMergerFrame;


public class ClearUnOrderedTextButton extends AbstractMainWindowContainerButton {
	
	private static final long serialVersionUID = 1L;
    private static final String CLEAR_TOOLTIP = "Clear Text";
    private static final String RES_CLEAR_BUTTON_IMAGE = "/clear_btn.png";
	private transient BufferedImage clearBtnImg;
	

	public ClearUnOrderedTextButton(LogMergerFrame mainWindow) {
		super(mainWindow);
	    setToolTipText(CLEAR_TOOLTIP);
	    
	    try {
			clearBtnImg = ImageIO.read(getClass().getResource(RES_CLEAR_BUTTON_IMAGE));
			Dimension setDim = new Dimension( clearBtnImg.getWidth(), clearBtnImg.getHeight());
			this.setPreferredSize(setDim);
			this.setMinimumSize(setDim);
			this.setMaximumSize(setDim);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		if(clearBtnImg != null) {
			g.drawImage(clearBtnImg, 0, 0, null);
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		SwingUtilities.invokeLater(()->
			this.getWindowHolder().setUnorderedText("")
		);
	}
	
}
