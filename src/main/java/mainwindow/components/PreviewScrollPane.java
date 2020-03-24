package mainwindow.components;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;


public class PreviewScrollPane extends JScrollPane implements MouseWheelListener, AdjustmentListener {

	private static final long serialVersionUID = 1L;
	private JPanel panel;
	private static int newValue = 0;
	
	
	public PreviewScrollPane(Component component) {
		super(component);
		this.panel = (JPanel) component;
		addMouseWheelListener(this);
		getVerticalScrollBar().addAdjustmentListener(this);
	}
	
	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		System.out.println("Mouse wheel moved");
		
		SwingUtilities.invokeLater(()->{
			
			JScrollBar bar = getVerticalScrollBar();
			int maxValue = bar.getMaximum();
			int wheelRotation = e.getWheelRotation();
			
			double incrementValue = maxValue/50.0;
			
			if(PreviewScrollPane.newValue > maxValue) {
				PreviewScrollPane.newValue = maxValue;
			}
			
			if(PreviewScrollPane.newValue < 0) {
				PreviewScrollPane.newValue = 0;
			}
			
			PreviewScrollPane.newValue += wheelRotation * incrementValue;
			
			bar.setValue(PreviewScrollPane.newValue);
		});
		
		//System.out.println("\nnewValue: " + newValue +"\nmaxValue: " + maxValue + "\ncurrentValue: " + currentValue + "\nunitsToScroll: " + unitsToScroll + "\nscrollAmout: " + scrollAmount + "\nwheelRotation: " + wheelRotation);
	}

	@Override
	public void adjustmentValueChanged(AdjustmentEvent e) {
		PreviewScrollPane.newValue = this.getVerticalScrollBar().getValue();
	}

}
