package window.components;

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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import datedline.DatedLine;


public class LineNumberComponent extends JComponent implements MouseMotionListener, MouseListener {
	
	private static final Logger logger = LoggerFactory.getLogger(LineNumberComponent.class);
	private static final long serialVersionUID = 1L;
	
	private static final Color COLOR_BORDER = Color.decode("0x494949");
	private static final Color LINE_NUM_BORDER = Color.decode("0x494949");
	private static final int ARC_BORDER = 6;
	private static final double HEIGHT_DECREASE = .25d;
	private static final int BUFFER_HEIGHT = 2;
	private final Dimension setDimension = new Dimension(34,10);
	private float lineNumberRatio = 0;
    private final JPopupMenu popupmenu = new JPopupMenu("Date Boundary");
    private transient List<DatedLine> datedLines = null;
    
    private boolean drawLoggingLevelNotes;
    private transient DatedLine rightClickedDate;
	private Point movedPoint;
	private int strFontHeight;
	private boolean drawToolTip;
	private Rectangle movedRectangle;
	private int pixelIntNumber;
	private LogMergerWindow logMergerWindow;
	
	
	public LineNumberComponent(LogMergerWindow logMergerWindow, boolean drawLoggingLevel) {
		setLogMergerWindow(logMergerWindow);
		
		addMouseListener(this);
		addMouseMotionListener(this);
		setPreferredSize(setDimension);
		JMenuItem minDate = new JMenuItem("Use as Min Date");
		JMenuItem maxDate = new JMenuItem("Use as Max Date");
		
		minDate.addActionListener((ActionEvent e)->{
			DatedLine line = getRightClickedDate();
			if(line != null) {
				setMinDateText(getRightClickedDate().getDateAsString());
			}
		});
		
		maxDate.addActionListener((ActionEvent e)->{
			DatedLine line = getRightClickedDate();
			if(line != null) {
				setMaxDateText(line.getDateAsString());
			}
		});
				
        popupmenu.add(minDate);
        popupmenu.add(maxDate);
        
        this.drawLoggingLevelNotes = drawLoggingLevel;
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponents(g);
		
		FontMetrics fm = g.getFontMetrics();
		strFontHeight = fm.getHeight();
		
		datedLines = getLogMergerWindow().getWindowComponentHolder().getDatedLines();

		drawLineNumbers(g, fm);
		drawBorder(g);
		drawLineBorder(g);
		handleToolTip(g);
	}
	
	private void drawLineNumbers(Graphics g, FontMetrics fm) {
		Color defColor = g.getColor();
		
		// Draw the colored boxes first
		if(this.datedLines != null && drawLoggingLevelNotes) {
			int rowVal = 0;
			
			for(DatedLine line: this.datedLines) {
				if(line.isVisible()) {
					g.setColor(line.getLogLevel().getLevelColor());
					g.fillRect(1, strFontHeight*rowVal, getWidth()-2, strFontHeight);
					rowVal += line.getRowCount();
				}
			}
		}
		
		g.setColor(Color.BLACK);
		
		// Now draw the line numbers over the colored boxes
		for(int t = 0; t <= getHeight()/strFontHeight; t++) {
			String intStr = String.valueOf(t);
			
			g.drawString( intStr, (getWidth() - fm.stringWidth(intStr))/2, (int)(strFontHeight*(t - HEIGHT_DECREASE) + BUFFER_HEIGHT));
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
			g.drawRoundRect(0, (int)(lineNumberRatio)*strFontHeight, getWidth()-1, strFontHeight, ARC_BORDER, ARC_BORDER);
		}
	}
	
	private void handleToolTip(Graphics g) {
		List<DatedLine> dateLines = getDatedLines();
		
		if(dateLines != null && movedPoint != null) {
			int lineCount = 1;
			for(DatedLine line: dateLines) {
				if((lineCount + line.getRowCount()) > getLine() && getLine() >= lineCount) {
					setToolTipText("Set Date Boundary: " + line.getDateAsString());
					break;
				}
				
				lineCount+=line.getRowCount();
			}
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
		movedPoint = e.getPoint();
		lineNumberRatio = getQuotient(e.getPoint().y, strFontHeight);
		pixelIntNumber = (int)lineNumberRatio*strFontHeight;
		
		List<DatedLine> dateLines = getDatedLines();
		if(e.isPopupTrigger() && dateLines != null) {
			int lineCount = 1;
			for(DatedLine line: dateLines) {
				if((lineCount + line.getRowCount()) > getLine() && getLine() >= lineCount) {
					setRightClickedDate(line);
					popupmenu.show(e.getComponent(), e.getX(), e.getY());
					break;
				}

				lineCount+=line.getRowCount();
			}
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		setDrawToolTip(Boolean.TRUE);
		movedPoint = e.getPoint();
		lineNumberRatio = getQuotient(movedPoint.y, strFontHeight);
		pixelIntNumber = (int)lineNumberRatio*strFontHeight;
		
		drawMouseMovedBorder(new Rectangle(0, pixelIntNumber, getWidth(), strFontHeight+1));
	}

	@Override
	public void mouseExited(MouseEvent e) {
		setDrawToolTip(Boolean.FALSE);
		movedPoint = null;
		
		drawMouseMovedBorder(null);
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		logger.info("MouseDragged");
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		movedPoint = e.getPoint();
		lineNumberRatio = getQuotient(movedPoint.y, strFontHeight);
		pixelIntNumber = (int)lineNumberRatio*strFontHeight;
		
		Rectangle mousepointRectangle = new Rectangle(0, pixelIntNumber, getWidth(), strFontHeight);
		
		if(movedRectangle == null) {
			movedRectangle = mousepointRectangle;
		}
		
		if(!movedRectangle.intersects(mousepointRectangle)) {
			movedRectangle = mousepointRectangle;
			drawMouseMovedBorder(new Rectangle(0, pixelIntNumber - strFontHeight, getWidth(), 4 * strFontHeight));
		}
	}
	
	private void setMinDateText(String text) {
		getLogMergerWindow().getWindowComponentHolder().getTxtHolder().getMinDateField().setText(text);
	}
	
	private void setMaxDateText(String text) {
		getLogMergerWindow().getWindowComponentHolder().getTxtHolder().getMaxDateField().setText(text);
	}
	
	protected List<DatedLine> getDatedLines() {
		return getLogMergerWindow().getWindowComponentHolder().getDatedLines();
	}
	
	private float getQuotient(int dividend, int divisor) {
		return divisor==0 ? 0 : (float)dividend/divisor;
	}
	
	private int getLine() {
		return (int)lineNumberRatio + 1;
	}

	private void drawMouseMovedBorder(Rectangle rect) {
		if(rect == null) {
			repaint();
		} else {
			repaint(rect);
		}
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

	public DatedLine getRightClickedDate() {
		return rightClickedDate;
	}

	public void setRightClickedDate(DatedLine rightClickedDate) {
		this.rightClickedDate = rightClickedDate;
	}
}
