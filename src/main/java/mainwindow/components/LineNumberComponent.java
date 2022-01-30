package mainwindow.components;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.List;

import javax.swing.JComponent;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.SwingUtilities;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import transfer.object.DatedLine;


public class LineNumberComponent extends JComponent implements MouseMotionListener, MouseListener {
	
	private static final Logger logger = LoggerFactory.getLogger(LineNumberComponent.class);
	private static final long serialVersionUID = 1L;
	
	private static final String COLOR_BORDER = "0x494949";
	private static final String LINE_NUM_BORDER = "0x707070";
	private static final int ARC_BORDER = 6;
	private static final double HEIGHT_DECREASE = .25d;
	private static final int BUFFER_HEIGHT = 2;
	private final Dimension setDimension = new Dimension(34,10);
    private final JPopupMenu popupmenu = new JPopupMenu("Date Boundary");   
    private final JMenuItem minDate = new JMenuItem("Use as Min Date");  
    private final JMenuItem maxDate = new JMenuItem("Use as Max Date");
    
	private Point movedPoint;
	private int strHeight;
	private boolean drawToolTip;
	private Rectangle movedRectangle;
	private int pixelLineNumber;
	private LogMergerWindow logMergerWindow;
	private float quotient = 0;
	
	
	public LineNumberComponent(LogMergerWindow logMergerWindow) {
		setLogMergerWindow(logMergerWindow);
		
		addMouseListener(this);
		addMouseMotionListener(this);
		setPreferredSize(setDimension);
		 
        popupmenu.add(minDate);
        popupmenu.add(maxDate);
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
		
		minDate.addActionListener((ActionEvent e)->{
			DatedLine line = getDatedLine((int)quotient);
			if(line != null) {
				setMinDateText(line.getDateAsString());
			}
		});
		
		maxDate.addActionListener((ActionEvent e)->{
			DatedLine line = getDatedLine((int)quotient);
			if(line != null) {
				setMaxDateText(line.getDateAsString());
			}
		});
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
		DatedLine line = getDatedLine((int)quotient);
		if(line!=null && line.getDateAsString()!=null) {
			setToolTipText("Set Date Boundary: " + line.getDateAsString());
		}
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		logger.info("MouseClicked");
	}

	@Override
	public void mousePressed(MouseEvent e) {
		logger.info("MousePressed");
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		logger.info("MouseReleased");
		
		if(e.isPopupTrigger()) {
			quotient = getQuotient(e.getPoint().y, strHeight);
	        popupmenu.show(e.getComponent(), e.getX(), e.getY());
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		setDrawToolTip(Boolean.TRUE);
		movedPoint = e.getPoint();
		quotient = getQuotient(movedPoint.y, strHeight);
		pixelLineNumber = (int)quotient*strHeight;
		
		redrawRectangle(new Rectangle(0, pixelLineNumber, getWidth(), strHeight+1));
	}

	@Override
	public void mouseExited(MouseEvent e) {
		setDrawToolTip(Boolean.FALSE);
		movedPoint = null;
		
		redrawRectangle(null);
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		logger.info("MouseDragged");
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		movedPoint = e.getPoint();
		quotient = getQuotient(movedPoint.y, strHeight);
		pixelLineNumber = (int)quotient * strHeight;
		
		Rectangle mousepointRectangle = new Rectangle(0, pixelLineNumber, getWidth(), strHeight);
		
		if(movedRectangle == null) {
			movedRectangle = mousepointRectangle;
		}
		
		if(!movedRectangle.intersects(mousepointRectangle)) {
			movedRectangle = mousepointRectangle;
			redrawRectangle(new Rectangle(0, pixelLineNumber - strHeight, getWidth(), 4 * strHeight));
		}
	}
	
	private void setMinDateText(String text) {
		getLogMergerWindow().getWindowHolder().getTxtHolder().getMinDateField().setText(text);
	}
	
	private void setMaxDateText(String text) {
		getLogMergerWindow().getWindowHolder().getTxtHolder().getMaxDateField().setText(text);
	}
	
	protected DatedLine getDatedLine(int index) {
		DatedLine line = null;
		List<DatedLine> lines = getLogMergerWindow().getWindowHolder().getDatedLines();
		if(lines==null || lines.isEmpty()) {
			return line;
		} else {
			if(index < lines.size()) {
				line = lines.get(index);
			}
		}
		
		return line;
	}
	
	private float getQuotient(int dividend, int divisor) {
		return divisor==0 ? 0 : (float)dividend/divisor;
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

	public LogMergerWindow getLogMergerWindow() {
		return logMergerWindow;
	}

	public void setLogMergerWindow(LogMergerWindow logMergerWindow) {
		this.logMergerWindow = logMergerWindow;
	}

	public boolean isDrawToolTip() {
		return drawToolTip;
	}

	public void setDrawToolTip(boolean drawToolTip) {
		this.drawToolTip = drawToolTip;
	}
}
