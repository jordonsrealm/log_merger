package window.component;

import java.awt.Component;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;


public class PreviewScrollPane extends JScrollPane implements MouseWheelListener, AdjustmentListener {

	private static final long serialVersionUID = 1L;
	private static int newValue = 0;
	
	
	public PreviewScrollPane(Component component) {
		super(component);
		addMouseWheelListener(this);
		getVerticalScrollBar().addAdjustmentListener(this);
	}
	
	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
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
	}

	@Override
	public void adjustmentValueChanged(AdjustmentEvent e) {
		PreviewScrollPane.newValue = this.getVerticalScrollBar().getValue();
	}

}
