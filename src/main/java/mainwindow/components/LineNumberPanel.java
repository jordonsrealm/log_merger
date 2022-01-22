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


public class LineNumberPanel extends JComponent implements MouseMotionListener, MouseListener {
	
	private static final long serialVersionUID = 1L;
	private final Dimension setDimension = new Dimension(34,10);
	private Point movedPoint;
	private int strHeight;
	private boolean drawToolTip;
	private double heightDecrease = .25d;
	private int heightBuffer = 2;
	private Rectangle movedRectangle;
	private static final String COLOR_BORDER = "0x494949";
	private static final String LINE_NUM_BORDER = "0x707070";
	private static final int ARC_BORDER = 6;
	
	
	public LineNumberPanel() {
		addMouseListener(this);
		addMouseMotionListener(this);
		setPreferredSize(setDimension);
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponents(g);
		
		drawBorder(g);
		handleToolTip(g);
		drawLineBorder(g);
	}
	
	private void drawLineBorder(Graphics g) {
		if(movedPoint != null) {
			g.setColor(Color.decode(LINE_NUM_BORDER));
			g.drawRoundRect(0, (int)((double)movedPoint.y/strHeight)*strHeight, getWidth()-1, strHeight, ARC_BORDER, ARC_BORDER);
		}
	}
	
	private void handleToolTip(Graphics g) {
		String toolTipText = "";
		strHeight = getGraphics().getFontMetrics().getHeight();
		if(drawToolTip && movedPoint!=null) {
			toolTipText += movedPoint.y/strHeight + 1;
		}
		
		setToolTipText(toolTipText);
		FontMetrics fm = g.getFontMetrics();
		for(int t=0;t <= getHeight()/strHeight;t++) {
			String intStr = String.valueOf(t);
			g.drawString( intStr, (getWidth() - fm.stringWidth(intStr))/2, (int)(strHeight*(t - heightDecrease) + heightBuffer));
		}
	}
	
	private void drawBorder(Graphics g) {
		Color prevColor = g.getColor();
		g.setColor(Color.decode(COLOR_BORDER));
		g.drawRect(0, 0, getWidth()-1, getHeight());
		g.setColor(prevColor);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		System.out.println("Done mouseClicked");
	}

	@Override
	public void mousePressed(MouseEvent e) {
		System.out.println("Done mousePressed");
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		System.out.println("Done mouseReleased");
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		drawToolTip = Boolean.TRUE;
		movedPoint = e.getPoint();
		float quotient = strHeight==0?0:(float)(e.getPoint().y/strHeight);
		
		redrawRectangle(new Rectangle(0, (int)(quotient)*strHeight, getWidth(), strHeight+1));
	}

	@Override
	public void mouseExited(MouseEvent e) {
		drawToolTip = Boolean.FALSE;
		movedPoint = null;
		
		redrawRectangle(null);
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		System.out.println("Done MouseEvent");
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		movedPoint = e.getPoint();
		
		float quotient = strHeight==0 ? 0 :((float)e.getPoint().y/strHeight);
		
		Rectangle pointRectangle = new Rectangle(0, (int)quotient*strHeight, getWidth(), strHeight);
		
		if(movedRectangle == null) {
			movedRectangle = pointRectangle;
		}
		
		if(!movedRectangle.intersects(pointRectangle)) {
			movedRectangle = pointRectangle;
			redrawRectangle(new Rectangle(0, (int)quotient*strHeight - strHeight, getWidth(), 4*strHeight));
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
