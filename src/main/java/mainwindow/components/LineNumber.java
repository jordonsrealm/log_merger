package mainwindow.components;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JComponent;
import javax.swing.SwingUtilities;


public class LineNumber extends JComponent implements MouseMotionListener, MouseListener {
	
	private static final long serialVersionUID = 1L;
	private static final Dimension setDimension = new Dimension(34,10);
	private static Point movedPoint;
	private static int strHeight;
	private static boolean drawToolTip;
	
	
	public LineNumber() {
		addMouseListener(this);
		addMouseMotionListener(this);
		setPreferredSize(setDimension);
	}
	
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponents(g);
		
		strHeight = g.getFontMetrics().getHeight();
		
		String toolTipText = "";
		if(drawToolTip) {
			toolTipText += movedPoint.y/strHeight + 1;
		}
		
		setToolTipText(toolTipText);
		
		for(int t=0;t<=getHeight()/strHeight;t++) {
			g.drawString(t + "", 0, (int)(strHeight * (t-.5d) + 2));
		}
		
		if(movedPoint != null) {
			g.setColor(Color.decode("0xc8c8c8"));
			g.drawRoundRect(0, (movedPoint.y/strHeight)*strHeight, getWidth()-1, strHeight, 6, 6);
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		System.out.println("Done clicked");
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
		drawToolTip = Boolean.TRUE;
	}

	@Override
	public void mouseExited(MouseEvent e) {
		drawToolTip = Boolean.FALSE;
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		SwingUtilities.invokeLater(()->{
			movedPoint = e.getPoint();
			repaint();
		});
	}

}
