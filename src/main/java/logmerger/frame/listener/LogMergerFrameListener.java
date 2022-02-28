package logmerger.frame.listener;

import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

import logmerger.frame.LogMergerFrame;


public class LogMergerFrameListener implements ComponentListener{

	private LogMergerFrame logMergerFrame = null;
	
	
	public LogMergerFrameListener(LogMergerFrame logMergerFrame) {
		this.logMergerFrame = logMergerFrame;
	}
	
	@Override
	public void componentResized(ComponentEvent e) {
		int frameWidth = getLogMergerFrame().getSize().width;
		getLogMergerFrame().getWindowComponentHolder().getMergingSplitPane().setDividerLocation(frameWidth/2);
	}

	@Override
	public void componentMoved(ComponentEvent e) {/* Didn't want to extend the ComponentAdapter*/}

	@Override
	public void componentShown(ComponentEvent e) {/* Didn't want to extend the ComponentAdapter*/}

	@Override
	public void componentHidden(ComponentEvent e) {/* Didn't want to extend the ComponentAdapter*/}

	public LogMergerFrame getLogMergerFrame() {
		return logMergerFrame;
	}

	public void setLogMergerFrame(LogMergerFrame logMergerFrame) {
		this.logMergerFrame = logMergerFrame;
	}

}
