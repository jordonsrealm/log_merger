package drawing;

import java.awt.Color;
import java.awt.Component;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Point;

import container.MainWindowContainer;


public class GlassPaneGraphicsProcessor {

	private MainWindowContainer mainWindowContainer;
    private static final String PROCESSING = "Processing";
    public static final int MAX_NUMBER_OF_PERIODS  = 40;
    private int tickCounter = 0;
	
	
	public GlassPaneGraphicsProcessor(MainWindowContainer mainWindowContainer) {
		this.mainWindowContainer = mainWindowContainer;
	}
	
    
    protected void clearProcessingLogoArea(String processingString, Point centerPoint) {
    	FontMetrics metrics = getGlassPaneFontMetrics();
    	Graphics g = getGlassPaneGraphics();
    	
    	int processingStrWidth  = getGlassPaneFontMetrics().stringWidth(processingString);
    	int periodWidth = metrics.stringWidth(".");
    	int textHeight = metrics.getHeight();
    	int textPadding = textHeight;
    	
    	int finalX = centerPoint.x - textPadding;
		int finalY = centerPoint.y - textHeight;
		int finalWidth = processingStrWidth + textPadding + MAX_NUMBER_OF_PERIODS * periodWidth;
		int finalHeight = textHeight + textHeight;
		
    	g.setColor(Color.WHITE);
    	g.fillRect( finalX, finalY, finalWidth, finalHeight);
    	
    	g.setColor(Color.RED);
    	g.drawRect( finalX, finalY, finalWidth, finalHeight);
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
		return this.mainWindowContainer.getMainWindow().getGlassPane();
	}

	public MainWindowContainer getMainWindowContainer() {
		return mainWindowContainer;
	}

	public void setMainWindowContainer(MainWindowContainer mainWindowContainer) {
		this.mainWindowContainer = mainWindowContainer;
	}
	
	protected Graphics getGlassPaneGraphics() {
        return getGlassPane().getGraphics();
	}
	
	protected FontMetrics getGlassPaneFontMetrics() {
		return getGlassPane().getGraphics().getFontMetrics();
	}
	
	protected void drawGlassPaneString(String processingString, int ptX, int ptY) {
		Graphics g = getGlassPaneGraphics();
		g.setColor(Color.BLACK);
		g.drawString( processingString, ptX, ptY);
	}
}
