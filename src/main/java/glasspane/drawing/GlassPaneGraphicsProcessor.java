package glasspane.drawing;

import java.awt.Color;
import java.awt.Component;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;


public class GlassPaneGraphicsProcessor {

    private static final String DEFAULT_PROCESSING_STRING_LABEL = "Processing";
    public static final int MAX_NUMBER_OF_PERIODS  = 40;
    private Component glassPane;
    private int tickCounter = 0;
	
	
	public GlassPaneGraphicsProcessor(Component glassPane) {
		this.glassPane = glassPane;
	}
	
	private Rectangle getProcessingLogoDimensions(String strToDraw, Point centeredPoint) {
		FontMetrics metrics = getGlassPaneFontMetrics();
    	
    	int processingStrWidth  = getGlassPaneFontMetrics().stringWidth(strToDraw);
    	int periodWidth = metrics.stringWidth(".");
    	int textHeight = metrics.getHeight();
    	int textPadding = textHeight;
    	
    	int finalX = centeredPoint.x - MAX_NUMBER_OF_PERIODS * periodWidth;
		int finalY = centeredPoint.y - textHeight/2;
		int finalWidth = textPadding + processingStrWidth + MAX_NUMBER_OF_PERIODS * periodWidth;
		int finalHeight = textHeight + textHeight;
		
		return new Rectangle(finalX, finalY, finalWidth, finalHeight);
	}
    
    protected void clearProcessingLogoArea(String processingString, Point centerPoint) {
    	Rectangle dimensions = getProcessingLogoDimensions(processingString, centerPoint);
    	Graphics glassPaneGraphics = getGlassPaneGraphics();
		
    	glassPaneGraphics.setColor(Color.WHITE);
    	glassPaneGraphics.fillRect( dimensions.x, dimensions.y, dimensions.width, dimensions.height);
    	
    	glassPaneGraphics.setColor(Color.RED);
    	glassPaneGraphics.drawRect( dimensions.x, dimensions.y, dimensions.width, dimensions.height);
    }
    
    protected String buildProcessingLogo() {
    	String processingString = DEFAULT_PROCESSING_STRING_LABEL;
    	
    	for(int t=getTickCounter(); t > 0; t--) {
    		processingString+=".";
    	}
    	
    	return processingString;
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
		return glassPane;
	}
	
	protected Graphics getGlassPaneGraphics() {
        return glassPane.getGraphics();
	}
	
	protected FontMetrics getGlassPaneFontMetrics() {
		Graphics g = glassPane.getGraphics();
		return g.getFontMetrics();
	}
	
	protected void drawGlassPaneString(String processingString, Point point) {
		Graphics g = getGlassPaneGraphics();
		FontMetrics fm = g.getFontMetrics();
		int periodWidth = fm.stringWidth(".");
    	int textHeight = fm.getHeight();
    	int textPadding = textHeight;
		g.setColor(Color.BLACK);
		g.drawString( processingString, point.x + textPadding/2 - MAX_NUMBER_OF_PERIODS * periodWidth, point.y + textHeight);
	}
}
