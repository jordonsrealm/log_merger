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
	private static final Logger logger         = LoggerFactory.getLogger(LogMergerWindow.class);
	
    private transient ConfigurationGetter configGetter = new ConfigurationGetter();
    private transient MainWindowHolder windowHolder;
    private transient ExecutorService executor;
    

    public LogMergerWindow(ExecutorService executor) {
    	logger.debug("User directory: {}" , System.getProperty("user.dir"));
    	
        setLayout(new BorderLayout());
        setTitle(this.configGetter.getApplicationName());
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
			ClassLoader classloader = Thread.currentThread().getContextClassLoader();
			InputStream  inputStreamFromPng = classloader.getResourceAsStream(this.configGetter.getAppIconName());
	        
			ImageIcon icon = new ImageIcon(ImageIO.read(inputStreamFromPng));
			
			setIconImage(icon.getImage());
		} catch (IOException e) {
			logger.error("Unable to set the image icon for application. Resource: {}", this.configGetter.getAppIconName(), e);
		}
    }

    private void setFrameDimensionsAndBehaviors(){
        this.setMinimumSize(new Dimension(this.configGetter.getWindowWidth()/2,this.configGetter.getWindowHeight()/2));
        this.setSize(new Dimension(this.configGetter.getWindowWidth(),this.configGetter.getWindowHeight()));
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

	public ConfigurationGetter getConfigGetter() {
		return this.configGetter;
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