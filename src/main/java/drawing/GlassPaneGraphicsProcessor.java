package drawing;

import java.awt.Color;
import java.awt.Component;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Point;


public class GlassPaneGraphicsProcessor {

    private static final String PROCESSING = "Processing";
    public static final int MAX_NUMBER_OF_PERIODS  = 40;
    protected Component glassPane;
    private int tickCounter = 0;
	
	
	public GlassPaneGraphicsProcessor(Component glassPane) {
		this.glassPane = glassPane;
	}
	
    
    protected void clearProcessingLogoArea(String processingString, Point centerPoint) {
    	FontMetrics metrics = getGlassPaneFontMetrics();
    	Graphics glassPaneGraphics = getGlassPaneGraphics();
    	
    	int processingStrWidth  = getGlassPaneFontMetrics().stringWidth(processingString);
    	int periodWidth = metrics.stringWidth(".");
    	int textHeight = metrics.getHeight();
    	int textPadding = textHeight;
    	
    	int finalX = centerPoint.x - textPadding;
		int finalY = centerPoint.y - textHeight;
		int finalWidth = processingStrWidth + textPadding + MAX_NUMBER_OF_PERIODS * periodWidth;
		int finalHeight = textHeight + textHeight;
		
    	glassPaneGraphics.setColor(Color.WHITE);
    	glassPaneGraphics.fillRect( finalX, finalY, finalWidth, finalHeight);
    	
    	glassPaneGraphics.setColor(Color.RED);
    	glassPaneGraphics.drawRect( finalX, finalY, finalWidth, finalHeight);
    }
    
    protected String buildProcessingLogo() {
    	String processingString = PROCESSING;
    	
    	for(int t=getTickCounter(); t > 0; t--) {
    		processingString+=".";
    	}
    	
    	return processingString;
    }
    
    public void tick() {
    	tickCounter++;
    	
    	if(tickCounter > MAX_NUMBER_OF_PERIODS) {
    		tickCounter = 0;
    	}
    }

	public int getTickCounter() {
		return tickCounter;
	}

	public void setTickCounter(int tickCounter) {
		this.tickCounter = tickCounter;
	}
	
	protected Component getGlassPane() {
		return glassPane;
	}
	
	protected Graphics getGlassPaneGraphics() {
        return glassPane.getGraphics();
	}
	
	protected FontMetrics getGlassPaneFontMetrics() {
		return glassPane.getGraphics().getFontMetrics();
	}
	
	protected void drawGlassPaneString(String processingString, Point point) {
		Graphics g = getGlassPaneGraphics();
		g.setColor(Color.BLACK);
		g.drawString( processingString, point.x, point.y);
	}
}
