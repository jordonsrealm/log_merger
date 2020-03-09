package components;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import javax.swing.JComponent;
import javax.swing.SwingUtilities;
import javax.swing.event.MouseInputListener;


public class OrganizedToolsPanel extends JComponent implements MouseInputListener {

	private static final long serialVersionUID = 1L;
	private OrderedTextArea orderedTextArea;
	
	private static final int ORDERING_PLACE_X = 8;
	private static final int ORDERING_PLACE_Y = 2;
	private static final int ORDERING_WIDTH   = 14;
	private static final int ORDERING_HEIGHT  = 14;
	private static final int DRAW_BOX_PADDING = 2;
	private Rectangle orderingTriangle = new Rectangle(ORDERING_PLACE_X, ORDERING_PLACE_Y, ORDERING_WIDTH, ORDERING_HEIGHT);
	private Color defaultColor;
	private boolean descending;
	private boolean drawBox;
	private boolean repaintBoxArea;
	
	
	public OrganizedToolsPanel(OrderedTextArea orderedTextArea) {
		Dimension setDimensions = new Dimension(0,20);
		setMinimumSize(setDimensions);
		setMaximumSize(setDimensions);
		setPreferredSize(setDimensions);
		this.orderedTextArea = orderedTextArea;
		this.descending = false;
		this.drawBox = false;
		this.addMouseListener(this);
		this.addMouseMotionListener(this);
		this.repaintBoxArea = false;
	}
	
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		defaultColor = g.getColor();

		drawBottomBorderLine(g);
		
		if(drawBox) {
			drawOrderingTriagleBox(g);
		} else {
			setToolTipText(null);
		}
		
		drawOrderingTriangle(g);
	}
	
	private void drawBottomBorderLine(Graphics g) {
		g.setColor(Color.decode("0xc8c8c8"));
		g.drawLine(0, getHeight()-1, getWidth(), getHeight()-1);
		g.setColor(defaultColor);
	}
	
	private void drawOrderingTriagleBox(Graphics g) {
		g.setColor(Color.decode("0xc0c0c0"));
		g.drawRect(ORDERING_PLACE_X, ORDERING_PLACE_Y, ORDERING_WIDTH, ORDERING_HEIGHT);
		g.setColor(Color.decode("0xd8d8d8"));
		g.drawRect(ORDERING_PLACE_X + 1, ORDERING_PLACE_Y + 1, ORDERING_WIDTH - 2, ORDERING_HEIGHT - 2);
		g.setColor(defaultColor);
	}
	
	private void drawOrderingTriangle(Graphics g) {
		drawTriangle(g);
		g.setColor(defaultColor);
	}
	
	private void drawTriangle(Graphics g) {
		Polygon arrowHead = new Polygon();
		Graphics2D newGraphics = (Graphics2D)g;
		
		if(descending) {
			g.setColor(Color.RED);
			arrowHead.addPoint(ORDERING_PLACE_X + DRAW_BOX_PADDING, ORDERING_PLACE_Y + ORDERING_HEIGHT - DRAW_BOX_PADDING);
			arrowHead.addPoint(ORDERING_PLACE_X + ORDERING_WIDTH/2, ORDERING_PLACE_Y + DRAW_BOX_PADDING);
			arrowHead.addPoint(ORDERING_PLACE_X  + ORDERING_WIDTH - DRAW_BOX_PADDING, ORDERING_PLACE_Y + ORDERING_HEIGHT - DRAW_BOX_PADDING);
		} else {
			g.setColor(Color.GREEN);
			arrowHead.addPoint(ORDERING_PLACE_X + DRAW_BOX_PADDING, ORDERING_PLACE_Y + DRAW_BOX_PADDING);
			arrowHead.addPoint(ORDERING_PLACE_X + ORDERING_WIDTH - DRAW_BOX_PADDING, ORDERING_PLACE_Y + DRAW_BOX_PADDING);
			arrowHead.addPoint(ORDERING_PLACE_X + ORDERING_WIDTH/2, ORDERING_PLACE_Y + ORDERING_HEIGHT - DRAW_BOX_PADDING);
		}	
		
		newGraphics.fill(arrowHead);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		Point ptClicked = e.getPoint();
		if(orderingTriangle.contains(ptClicked)) {
			descending = !descending;
			System.out.println("Descending: "+ descending);
			
			repaint();
			
			orderedTextArea.updateTextArea();
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		repaint();
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		repaint();
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseMoved(MouseEvent e) {
		Point movedPoint = e.getPoint();
		
		boolean oldRedrawBox = drawBox;
		
		if(orderingTriangle.contains(movedPoint)) {
			drawBox = true;
			setToolTipText(descending?"Descending":"Ascending");
		} else {
			drawBox = false;
			setToolTipText("");
		}
		
		if(oldRedrawBox!=drawBox) {
			repaintBoxArea = true;
		} else {
			repaintBoxArea = false;
		}
		
		if(repaintBoxArea) {
			SwingUtilities.invokeLater(()->{
				repaint();
			});
		}
	}

}
