package loadingicon;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Rectangle;

import javax.swing.JScrollPane;

import logmerger.frame.LogMergerFrame;


public class LoadingIconGraphicsHandler {

    private static final String DEFAULT_PROCESSING_STRING_LABEL = "Processing";
    public static final int MAX_NUMBER_OF_PERIODS  = 20;
    private LogMergerFrame logMergerFrame;
    private int tickCounter = 0;
    protected static final int LOGO_WIDTH = 150;
    protected static final int LOGO_HEIGHT = 40;
    private Rectangle loadingRectangle = null;
    private JScrollPane orderedJScrollPane = null;
	
	
	public LoadingIconGraphicsHandler(LogMergerFrame logMergerFrame) {
		this.logMergerFrame = logMergerFrame;
		this.orderedJScrollPane = logMergerFrame.getWindowComponentHolder().getTxtHolder().getOrderedScrollPane();
		this.loadingRectangle = new Rectangle((int)(logMergerFrame.getSize().getWidth() - LOGO_WIDTH)/2 + orderedJScrollPane.getWidth()/2, (int)(logMergerFrame.getSize().getHeight() - LOGO_HEIGHT)/2, LOGO_WIDTH, LOGO_HEIGHT);
	}
	
	protected void clearGlassPane() {
		if(getGlassPane()!=null) {
			getGlassPane().getGraphics().clearRect(0, 0, getGlassPane().getWidth(), getGlassPane().getHeight());
		}
	}
    
    protected void drawLoadingIconArea() {
    	// Create the LoadingIcon string
    	StringBuilder stringBuilder = new StringBuilder(DEFAULT_PROCESSING_STRING_LABEL);
    	for(int t = getTickCounter(); t > 0; t--) {
    		stringBuilder.append(".");
    	}
    	
    	// Clear LoadingIcon Area
    	Graphics glassPaneGraphics = getGlassPane().getGraphics();
    	Color currColor = glassPaneGraphics.getColor();
		
    	glassPaneGraphics.setColor(Color.WHITE);
    	glassPaneGraphics.fillRect( loadingRectangle.x, loadingRectangle.y, loadingRectangle.width, loadingRectangle.height);
    	
    	// Draw LoadingIcon Box Area
		glassPaneGraphics.setColor(Color.RED);
    	glassPaneGraphics.drawRect( loadingRectangle.x, loadingRectangle.y, loadingRectangle.width, loadingRectangle.height);
    	glassPaneGraphics.setColor(currColor);
    	
		// Draw LoadingIcon string
    	int textHeight = glassPaneGraphics.getFontMetrics().getHeight();
    	
    	glassPaneGraphics.setColor(Color.BLACK);
    	glassPaneGraphics.drawString( stringBuilder.toString(),  loadingRectangle.x + textHeight/2, loadingRectangle.y + (3 * textHeight)/2);
    	glassPaneGraphics.setColor(currColor);
    }
	
    public void tick() {
    	if(tickCounter > MAX_NUMBER_OF_PERIODS) {
    		tickCounter = 0;
    	} else{
    		tickCounter++;
    	}
    }

	public int getTickCounter() {
		return tickCounter;
	}

	public void setTickCounter(int tickCounter) {
		this.tickCounter = tickCounter;
	}
	
	public Component getGlassPane() {
		return logMergerFrame.getGlassPane();
	}
}
