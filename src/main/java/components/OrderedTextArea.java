package components;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import javax.swing.JFrame;
import javax.swing.JTextArea;

import factory.CenteredPointType;
import runnables.DateLineProcessor;
import threads.ProcessLogo;


public class OrderedTextArea extends JTextArea {

	private static final long serialVersionUID = 1L;
	private int CHECKBOX_X_OFFSET 			   = 110;
	private int CHECKBOX_Y_OFFSET 			   = 2; 
	private int CHECKBOX_WIDTH    	    	   = 15;
	private int CHECKBOX_HEIGHT         	   = 15;
	private Rectangle checkBoxRectangle 	   = new Rectangle(new Dimension( CHECKBOX_WIDTH, CHECKBOX_HEIGHT));
	private boolean isDescending 			   = false;
	private DateLineProcessor dateLinesRunnable;
	
	
	public OrderedTextArea(String initString, int rowCount, int columnCount) {
		super(initString, rowCount, columnCount);
		checkBoxRectangle.setLocation(new Point( CHECKBOX_X_OFFSET, CHECKBOX_Y_OFFSET));
	}
	
	public void setRunnable(DateLineProcessor runnable) {
		this.dateLinesRunnable = runnable;
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
	}

	public boolean isDescending() {
		return isDescending;
	}

	public void setDescending(boolean isDescending) {
		this.isDescending = isDescending;
	}

	public void updateTextArea() {
		
		isDescending = !isDescending;
		Object comp = this.getParent();
		while(!(comp instanceof JFrame)) {
			comp = ((Component) comp).getParent();
		}

		ProcessLogo processingThread = new ProcessLogo( ((MainWindow) comp).getMainWindowContainer(), CenteredPointType.ORDERED_TEXT_AREA);
		this.setText("");
		processingThread.startProcessing();
		dateLinesRunnable.run();
		processingThread.stopProcessing();
		
		repaint();
	}
}
