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
	private static final Color LINE_NUM_BORDER = Color.decode("0x494949");
	private static final int ARC_BORDER = 6;
	private static final double HEIGHT_DECREASE = .25d;
	private static final int BUFFER_HEIGHT = 2;
	private final Dimension setDimension = new Dimension(34,10);
    private final JPopupMenu popupmenu = new JPopupMenu("Date Boundary");   
    private final JMenuItem minDate = new JMenuItem("Use as Min Date");  
    private final JMenuItem maxDate = new JMenuItem("Use as Max Date");
    private transient List<DatedLine> datedLines = null;
    private boolean drawLoggingLevelNotes;
    private transient DatedLine rightClickedDate;
	private Point movedPoint;
	private int strHeight;
	private boolean drawToolTip;
	private Rectangle movedRectangle;
	private int pixelIntNumber;
	private LogMergerWindow logMergerWindow;
	private float lineNumberRatio = 0;
	
	
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
	}
	
	private void drawLineNumbers(Graphics g, FontMetrics fm) {
		Color defColor = g.getColor();
		
		// Draw the colored boxes first
		if(this.datedLines != null && drawLoggingLevelNotes) {
			int rowVal = 0;
			
			for(DatedLine line: this.datedLines) {
				if(line.isVisible()) {
					g.setColor(line.getLogLevel().getLevelColor());
					g.fillRect(1, strHeight*rowVal, getWidth()-2, strHeight);
					rowVal += line.getRowCount();
				}
			}
		}
		
		g.setColor(Color.BLACK);
		
		// Now draw the line numbers over the colored boxes
		for(int t = 0; t <= getHeight()/strHeight; t++) {
			String intStr = String.valueOf(t);
			
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
			g.drawRoundRect(0, (int)(lineNumberRatio)*strHeight, getWidth()-1, strHeight, ARC_BORDER, ARC_BORDER);
		}
	}
	
	private void handleToolTip(Graphics g) {
		
		if(getDatedLines() != null && movedPoint != null) {
			int lineCount = 1;
			for(DatedLine line: getDatedLines()) {
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
		lineNumberRatio = getQuotient(e.getPoint().y, strHeight);
		pixelIntNumber = (int)lineNumberRatio*strHeight;
		
		if(e.isPopupTrigger() && getDatedLines() != null) {
			int lineCount = 1;
			for(DatedLine line: getDatedLines()) {
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
		lineNumberRatio = getQuotient(movedPoint.y, strHeight);
		pixelIntNumber = (int)lineNumberRatio*strHeight;
		
		redrawRectangle(new Rectangle(0, pixelIntNumber, getWidth(), strHeight+1));
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
		lineNumberRatio = getQuotient(movedPoint.y, strHeight);
		pixelIntNumber = (int)lineNumberRatio*strHeight;
		
		Rectangle mousepointRectangle = new Rectangle(0, pixelIntNumber, getWidth(), strHeight);
		
		if(movedRectangle == null) {
			movedRectangle = mousepointRectangle;
		}
		
		if(!movedRectangle.intersects(mousepointRectangle)) {
			movedRectangle = mousepointRectangle;
			redrawRectangle(new Rectangle(0, pixelIntNumber - strHeight, getWidth(), 4 * strHeight));
		}
	}
	
	private void setMinDateText(String text) {
		getLogMergerWindow().getWindowHolder().getTxtHolder().getMinDateField().setText(text);
	}
	
	private void setMaxDateText(String text) {
		getLogMergerWindow().getWindowHolder().getTxtHolder().getMaxDateField().setText(text);
	}
	
	protected List<DatedLine> getDatedLines() {
		return getLogMergerWindow().getWindowHolder().getDatedLines();
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
	
	private int getLine() {
		return (int)lineNumberRatio + 1;
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

	public DatedLine getRightClickedDate() {
		return rightClickedDate;
	}

	public void setRightClickedDate(DatedLine rightClickedDate) {
		this.rightClickedDate = rightClickedDate;
	}
}
