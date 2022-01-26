package mainwindow.components;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JComponent;
import javax.swing.SwingUtilities;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class LineNumberComponent extends JComponent implements MouseMotionListener, MouseListener {
	
	private static final Logger logger = LoggerFactory.getLogger(LineNumberComponent.class);
	private static final long serialVersionUID = 1L;
	
	private static final String COLOR_BORDER = "0x494949";
	private static final String LINE_NUM_BORDER = "0x707070";
	private static final int ARC_BORDER = 6;
	private static final double HEIGHT_DECREASE = .25d;
	private static final int BUFFER_HEIGHT = 2;
	
	private final Dimension setDimension = new Dimension(34,10);
	private Point movedPoint;
	private int strHeight;
	private boolean drawToolTip;
	private Rectangle movedRectangle;
	private int lineNumber;
	
	
	public LineNumberComponent() {
		addMouseListener(this);
		addMouseMotionListener(this);
		setPreferredSize(setDimension);
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponents(g);
		
		FontMetrics fm = g.getFontMetrics();
		strHeight = fm.getHeight();

		drawLineNumbers(g, fm);
		drawBorder(g);
		drawLineBorder(g);
		handleToolTip(g);
	}
	
	private void drawLineNumbers(Graphics g, FontMetrics fm) {
		for(int t = 0; t <= getHeight()/strHeight; t++) {
			String intStr = String.valueOf(t);
			g.drawString( intStr, (getWidth() - fm.stringWidth(intStr))/2, (int)(strHeight*(t - HEIGHT_DECREASE) + BUFFER_HEIGHT));
		}
	}
	
	private void drawBorder(Graphics g) {
		Color prevColor = g.getColor();
		g.setColor(Color.decode(COLOR_BORDER));
		g.drawRect(0, 0, getWidth()-1, getHeight());
		g.setColor(prevColor);
	}
	
	private void drawLineBorder(Graphics g) {
		if(movedPoint != null) {
			g.setColor(Color.decode(LINE_NUM_BORDER));
			g.drawRoundRect(0, (int)((float)movedPoint.y/strHeight)*strHeight, getWidth()-1, strHeight, ARC_BORDER, ARC_BORDER);
		}
	}
	
	private void handleToolTip(Graphics g) {
		String toolTipText = "";
		if(drawToolTip && movedPoint!=null) {
			toolTipText += movedPoint.y/strHeight + 1;
		}
		
		setToolTipText(toolTipText);
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		logger.info("Done mouseClicked");
	}

	@Override
	public void mousePressed(MouseEvent e) {
		logger.info("Done mousePressed");
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		logger.info("Done mouseReleased");
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		drawToolTip = Boolean.TRUE;
		movedPoint = e.getPoint();
		float quotient = strHeight==0 ? 0 : (float)e.getPoint().y/strHeight;
		lineNumber = (int)quotient*strHeight;
		
		redrawRectangle(new Rectangle(0, lineNumber, getWidth(), strHeight+1));
	}

	@Override
	public void mouseExited(MouseEvent e) {
		drawToolTip = Boolean.FALSE;
		movedPoint = null;
		
		redrawRectangle(null);
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		logger.info("Done MouseEvent");
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		movedPoint = e.getPoint();
		
		float quotient = strHeight==0 ? 0 :((float)e.getPoint().y/strHeight);
		lineNumber = (int)quotient*strHeight;
		
		Rectangle mousepointRectangle = new Rectangle(0, lineNumber, getWidth(), strHeight);
		
		if(movedRectangle == null) {
			movedRectangle = mousepointRectangle;
		}
		System.out.println("Linenumber: " + ((int)quotient + 1));
		if(!movedRectangle.intersects(mousepointRectangle)) {
			movedRectangle = mousepointRectangle;
			redrawRectangle(new Rectangle(0, lineNumber - strHeight, getWidth(), 4*strHeight));
		}
	}

	private void redrawRectangle(Rectangle rect) {
		SwingUtilities.invokeLater(()->{
				if(rect == null) {
					repaint();
				} else {
					repaint(rect);
				}
			}
		);
	}
}
