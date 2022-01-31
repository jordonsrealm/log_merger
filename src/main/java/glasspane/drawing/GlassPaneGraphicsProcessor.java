package glasspane.drawing;

import java.awt.Color;
import java.awt.Component;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;

import mainwindow.components.LogMergerWindow;


public class GlassPaneGraphicsProcessor {

    private static final String DEFAULT_PROCESSING_STRING_LABEL = "Processing";
    public static final int MAX_NUMBER_OF_PERIODS  = 40;
    private LogMergerWindow logMergerWindow;
    private int tickCounter = 0;
	
	
	public GlassPaneGraphicsProcessor(LogMergerWindow logMergerWindow) {
		this.setLogMergerWindow(logMergerWindow);
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
	
	protected void clearGlassPane() {
		getGlassPaneGraphics().clearRect(0, 0, getGlassPane().getWidth(), getGlassPane().getHeight());
	}
	
	protected void drawBox(String processingString, Point centerPoint) {
    	Rectangle dimensions = getProcessingLogoDimensions(processingString, centerPoint);
    	Graphics glassPaneGraphics = getGlassPaneGraphics();
    	
		glassPaneGraphics.setColor(Color.RED);
    	glassPaneGraphics.drawRect( dimensions.x, dimensions.y, 200, 50);
	}
    
    protected void clearProcessingLogoArea(String processingString, Point centerPoint) {
    	Rectangle dimensions = getProcessingLogoDimensions(processingString, centerPoint);
    	Graphics glassPaneGraphics = getGlassPaneGraphics();
		
    	glassPaneGraphics.setColor(Color.WHITE);
    	glassPaneGraphics.fillRect(dimensions.x, dimensions.y, dimensions.width, dimensions.height);
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
		return getLogMergerWindow().getGlassPane();
	}
	
	protected Graphics getGlassPaneGraphics() {
        return getGlassPane().getGraphics();
	}
	
	protected FontMetrics getGlassPaneFontMetrics() {
		Graphics g = getGlassPane().getGraphics();
		if(g!=null) {
			return g.getFontMetrics();
		}
		
		return null;
	}
	
	protected void drawGlassPaneString(String processingString, Point point) {
		Graphics g = getGlassPaneGraphics();
		if(g!=null) {
			FontMetrics fm = g.getFontMetrics();
			int periodWidth = fm.stringWidth(".");
	    	int textHeight = fm.getHeight();
	    	int textPadding = textHeight;
	    	
			g.setColor(Color.BLACK);
			g.drawString( processingString, point.x + textPadding/2 - MAX_NUMBER_OF_PERIODS * periodWidth, point.y + textHeight);
		}
	}

	public LogMergerWindow getLogMergerWindow() {
		return logMergerWindow;
	}

	public void setLogMergerWindow(LogMergerWindow logMergerWindow) {
		this.logMergerWindow = logMergerWindow;
	}
}
