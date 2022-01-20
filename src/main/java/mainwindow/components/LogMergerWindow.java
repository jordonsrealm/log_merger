package mainwindow.components;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutorService;
import configuration.ConfigurationGetter;
import mainwindow.holder.MainWindowHolder;

import java.awt.event.ComponentListener;
import java.awt.event.ComponentEvent;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.io.InputStream;
import javax.swing.*;
import java.awt.*;


public class LogMergerWindow extends JFrame implements ComponentListener {

	private static final long serialVersionUID = 1L;
	private static final Logger logger = LoggerFactory.getLogger(LogMergerWindow.class);
	;
    private transient MainWindowHolder windowHolder;
    private transient ExecutorService executor;
    

    public LogMergerWindow(ExecutorService executor) {
    	setLayout(new BorderLayout());
        setTitle(ConfigurationGetter.instance().getApplicationName());
    	setExecutor(executor);
        addComponentListener(this);
        
        this.windowHolder = new MainWindowHolder(this);
        
        add( this.windowHolder.getTopPanel(), BorderLayout.NORTH);
        add( this.windowHolder.getBottomPanel(), BorderLayout.CENTER);
        
        setImageIconForApplication();
        setFrameDimensionsAndBehaviors();
    }
    
    private void setImageIconForApplication() {
    	
		try {
			InputStream  inputStreamFromPng = getClass().getClassLoader().getResourceAsStream(ConfigurationGetter.instance().getAppIconName());
			ImageIcon icon = new ImageIcon(ImageIO.read(inputStreamFromPng));
			setIconImage(icon.getImage());
		} catch (IOException e) {
			logger.error("Unable to set the image icon for application. Resource: {}", ConfigurationGetter.instance().getAppIconName(), e);
		}
    }

    private void setFrameDimensionsAndBehaviors(){
        this.setMinimumSize(new Dimension(ConfigurationGetter.instance().getWindowWidth()/2,ConfigurationGetter.instance().getWindowHeight()/2));
        this.setSize(new Dimension(ConfigurationGetter.instance().getWindowWidth(),ConfigurationGetter.instance().getWindowHeight()));
        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setResizable(true);
        this.setVisible(true);
    }

	public ExecutorService getExecutor() {
		return this.executor;
	}

	public void setExecutor(ExecutorService executor) {
		this.executor = executor;
	}

	@Override
	public void componentResized(ComponentEvent e) {
		this.windowHolder.getBottomPanel().setDividerLocation(getWidth()/2);
	}

	@Override
	public void componentMoved(ComponentEvent e) {
		// Didn't want to extend the ComponentAdapter
	}

	@Override
	public void componentShown(ComponentEvent e) {
		// Didn't want to extend the ComponentAdapter
	}

	@Override
	public void componentHidden(ComponentEvent e) {
		// Didn't want to extend the ComponentAdapter
	}

	public MainWindowHolder getWindowHolder() {
		return this.windowHolder;
	}
	
}