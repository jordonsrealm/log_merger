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
import transfer.object.LoggingLevel;


public class LineNumberComponent extends JComponent implements MouseMotionListener, MouseListener {
	
	private static final Logger logger = LoggerFactory.getLogger(LineNumberComponent.class);
	private static final long serialVersionUID = 1L;
	
	private static final Color COLOR_BORDER = Color.decode("0x494949");
	private static final Color LINE_NUM_BORDER = Color.decode("0xffffff");
	private static final Color ERROR_LEVEL = Color.decode("0xff0000");
	private static final Color INFO_LEVEL = Color.decode("0xd0d0d0");
	private static final Color DEBUG_LEVEL = Color.decode("0xb6d7a8");
	private static final Color TRACE_LEVEL = Color.decode("0xfcfccd");
	private static final int ARC_BORDER = 6;
	private static final double HEIGHT_DECREASE = .25d;
	private static final int BUFFER_HEIGHT = 2;
	private final Dimension setDimension = new Dimension(34,10);
    private final JPopupMenu popupmenu = new JPopupMenu("Date Boundary");   
    private final JMenuItem minDate = new JMenuItem("Use as Min Date");  
    private final JMenuItem maxDate = new JMenuItem("Use as Max Date");
    private List<DatedLine> datedLines = null;
    private boolean drawLoggingLevelNotes;
    
	private Point movedPoint;
	private int strHeight;
	private boolean drawToolTip;
	private Rectangle movedRectangle;
	private int pixelLineNumber;
	private LogMergerWindow logMergerWindow;
	private float quotient = 0;
	
	
	public LineNumberComponent(LogMergerWindow logMergerWindow, boolean drawLoggingLevel) {
		setLogMergerWindow(logMergerWindow);
		
		addMouseListener(this);
		addMouseMotionListener(this);
		setPreferredSize(setDimension);
		 
        popupmenu.add(minDate);
        popupmenu.add(maxDate);
        this.drawLoggingLevelNotes = drawLoggingLevel;
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponents(g);
		
		FontMetrics fm = g.getFontMetrics();
		strHeight = fm.getHeight();
		
		datedLines = getLogMergerWindow().getWindowHolder().getDatedLines();

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
		Color defColor = g.getColor();
		
		for(int t = 0; t <= getHeight()/strHeight; t++) {
			
			if(datedLines!=null && t< datedLines.size() && drawLoggingLevelNotes) {
				if(datedLines.get(t).getLogLevel().equals(LoggingLevel.ERROR)) {
					g.setColor(ERROR_LEVEL);
				} else if(datedLines.get(t).getLogLevel().equals(LoggingLevel.INFO)){
					g.setColor(INFO_LEVEL);
				} else if(datedLines.get(t).getLogLevel().equals(LoggingLevel.DEBUG)){
					g.setColor(DEBUG_LEVEL);
				} else if(datedLines.get(t).getLogLevel().equals(LoggingLevel.TRACE)){
					g.setColor(TRACE_LEVEL);
				} else {
					g.setColor(defColor);
				}
				
				g.fillRoundRect(2, strHeight*t, getWidth()-2, strHeight, ARC_BORDER, ARC_BORDER);
			}
			
			String intStr = String.valueOf(t);
			g.setColor(Color.BLACK);
			g.drawString( intStr, (getWidth() - fm.stringWidth(intStr))/2, (int)(strHeight*(t - HEIGHT_DECREASE) + BUFFER_HEIGHT));
		}
		
		g.setColor(defColor);
	}
	
	private void drawBorder(Graphics g) {
		Color prevColor = g.getColor();
		g.setColor(COLOR_BORDER);
		g.drawRect(0, 0, getWidth()-1, getHeight());
		g.setColor(prevColor);
	}
	
	private void drawLineBorder(Graphics g) {
		if(movedPoint != null) {
			g.setColor(LINE_NUM_BORDER);
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
