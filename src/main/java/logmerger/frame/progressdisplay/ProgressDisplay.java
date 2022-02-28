package logmerger.frame.progressdisplay;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.MouseListener;

import javax.swing.JScrollPane;

import logmerger.frame.LogMergerFrame;
import logmerger.frame.glasspane.listener.GlassPaneListener;


public class ProgressDisplay {

    public static final int MAX_NUMBER_OF_PERIODS  = 20;
    private LogMergerFrame logMergerFrame;
    protected static final int LOGO_WIDTH = 260;
    protected static final int LOGO_HEIGHT = 40;
    private Rectangle loadingRectangle = null;
    private JScrollPane orderedJScrollPane = null;
    private GlassPaneListener glassPaneListener;
	
	
	public ProgressDisplay(LogMergerFrame logMergerFrame) {
		this.logMergerFrame = logMergerFrame;
		this.orderedJScrollPane = logMergerFrame.getWindowComponentHolder().getTxtHolder().getOrderedScrollPane();
		this.loadingRectangle = new Rectangle((int)(logMergerFrame.getSize().getWidth() - LOGO_WIDTH)/2 + orderedJScrollPane.getWidth()/2, (int)(logMergerFrame.getSize().getHeight() - LOGO_HEIGHT)/2, LOGO_WIDTH, LOGO_HEIGHT);
		this.glassPaneListener = new GlassPaneListener();
	}
	
	public void initiate() {
		this.getGlassPane().setVisible(true);
		this.getGlassPane().addMouseListener(this.getGlasspaneListener());
		this.setCurrentState("");
	}
	
	public void terminate() {
		this.getGlassPane().setVisible(false);
		this.getGlassPane().removeMouseListener(this.getGlasspaneListener());
	}
    
	public void drawLoadingIconArea(String currentState) {
    	// Clear LoadingIcon Area
    	Graphics glassPaneGraphics = getGlassPane().getGraphics();
    	Color currColor = glassPaneGraphics.getColor();
    	
    	int textHeight = glassPaneGraphics.getFontMetrics().getHeight();
		
    	glassPaneGraphics.setColor(Color.WHITE);
    	glassPaneGraphics.fillRect( loadingRectangle.x, loadingRectangle.y, loadingRectangle.width, loadingRectangle.height);
    	
    	// Draw LoadingIcon Box Area
		glassPaneGraphics.setColor(Color.RED);
    	glassPaneGraphics.drawRect( loadingRectangle.x, loadingRectangle.y, loadingRectangle.width, loadingRectangle.height);
    	glassPaneGraphics.setColor(currColor);
    	
    	// Draw LoadingIcon string
    	glassPaneGraphics.setColor(Color.BLACK);
    	glassPaneGraphics.drawString( currentState,  loadingRectangle.x + textHeight/2, loadingRectangle.y + (3 * textHeight)/2);
    	glassPaneGraphics.setColor(currColor);
    }
    
	public Component getGlassPane() {
		return logMergerFrame.getGlassPane();
	}

	public void setCurrentState(String currentState) {
		this.drawLoadingIconArea(currentState);
	}

	public MouseListener getGlasspaneListener() {
		return glassPaneListener;
	}
}
