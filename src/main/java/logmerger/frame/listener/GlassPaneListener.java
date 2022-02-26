package logmerger.frame.listener;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GlassPaneListener implements MouseListener {
	
	private static final Logger logger = LoggerFactory.getLogger(GlassPaneListener.class);
	private static final String GLASS_PANE_INTERCEPT_TEXT = "UNABLE TO USE BUTTONS WHILE GLASS PANE IS VISIBLE";
	

	@Override
	public void mouseClicked(MouseEvent e) {
		logger.info(GLASS_PANE_INTERCEPT_TEXT);
	}

	@Override
	public void mousePressed(MouseEvent e) {
		logger.info(GLASS_PANE_INTERCEPT_TEXT);
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		logger.info(GLASS_PANE_INTERCEPT_TEXT);
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		logger.info(GLASS_PANE_INTERCEPT_TEXT);
	}

	@Override
	public void mouseExited(MouseEvent e) {
		logger.info(GLASS_PANE_INTERCEPT_TEXT);
	}
}
