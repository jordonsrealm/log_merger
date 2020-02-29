package components;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

import runnables.OrderingDateLineProcessor;


public class OrderedTextArea extends JTextArea implements MouseListener {

	private static final long serialVersionUID = 1L;
	private int CHECKBOX_X_OFFSET 			   = 110;
	private int CHECKBOX_Y_OFFSET 			   = 2; 
	private int CHECKBOX_WIDTH    	    	   = 15;
	private int CHECKBOX_HEIGHT         	   = 15;
	private Rectangle checkBoxRectangle 	   = new Rectangle(new Dimension( CHECKBOX_WIDTH, CHECKBOX_HEIGHT));
	private boolean isDescending 			   = false;
	private boolean drawCheckBox 			   = true;
	private OrderingDateLineProcessor dateLinesRunnable;
	
	
	public OrderedTextArea(String initString, int rowCount, int columnCount) {
		super(initString, rowCount, columnCount);
		checkBoxRectangle.setLocation(new Point( CHECKBOX_X_OFFSET, CHECKBOX_Y_OFFSET));
		addMouseListener(this);
	}
	
	public void setRunnable(OrderingDateLineProcessor runnable) {
		this.dateLinesRunnable = runnable;
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		if(drawCheckBox) {
			drawArrow((Graphics2D)g);
		}
	}

	private void drawArrow(Graphics2D g) {
        Polygon arrowHead = new Polygon();

        if(isDescending) {
            arrowHead.addPoint(CHECKBOX_X_OFFSET, CHECKBOX_Y_OFFSET);
            arrowHead.addPoint(CHECKBOX_X_OFFSET + CHECKBOX_WIDTH, CHECKBOX_Y_OFFSET);
            arrowHead.addPoint(CHECKBOX_X_OFFSET + CHECKBOX_WIDTH/2, CHECKBOX_Y_OFFSET + (int)(CHECKBOX_HEIGHT*0.8));
        } else {
            arrowHead.addPoint(CHECKBOX_X_OFFSET, CHECKBOX_Y_OFFSET + (int)(CHECKBOX_HEIGHT*0.8));
            arrowHead.addPoint(CHECKBOX_X_OFFSET + CHECKBOX_WIDTH,  + (int)(CHECKBOX_HEIGHT*0.8) + CHECKBOX_Y_OFFSET);
            arrowHead.addPoint(CHECKBOX_X_OFFSET + CHECKBOX_WIDTH/2, CHECKBOX_Y_OFFSET);
        }
        
        g.fill(arrowHead);
	}

	public boolean isDescending() {
		return isDescending;
	}

	public void setDescending(boolean isDescending) {
		this.isDescending = isDescending;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		
		if(checkBoxRectangle.contains(e.getPoint())) {
			isDescending = !isDescending;
			SwingUtilities.invokeLater(dateLinesRunnable);
		}
		
		invalidate();
	}

	@Override
	public void mousePressed(MouseEvent e) {
	}


	@Override
	public void mouseReleased(MouseEvent e) {
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		drawCheckBox = true;
		repaint();
	}

	@Override
	public void mouseExited(MouseEvent e) {
		drawCheckBox = false;
		repaint();
	}
}
