package mainwindow.components;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mainwindow.container.MainWindowContainer;

import java.util.concurrent.ExecutorService;
import configuration.ConfigurationGetter;
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
	
    private ConfigurationGetter configGetter   = new ConfigurationGetter();
    private MainWindowContainer mainWindowContainer;
    private ExecutorService executor;
    

    public LogMergerWindow(ExecutorService executor) {
    	logger.debug("User directory: {}" , System.getProperty("user.dir"));
    	
        setLayout(new BorderLayout());
        setTitle(configGetter.getApplicationName());
    	setExecutor(executor);
        addComponentListener(this);
        
        mainWindowContainer = new MainWindowContainer(this);
        
        add( mainWindowContainer.getTopPanel(), BorderLayout.NORTH);
        add( mainWindowContainer.getBottomPanel(), BorderLayout.CENTER);
        
        setImageIconForApplication();
        setFrameDimensionsAndBehaviors();
    }
    
    private void setImageIconForApplication() {
    	
		try {
			ClassLoader classloader = Thread.currentThread().getContextClassLoader();
			InputStream  inputStreamFromPng = classloader.getResourceAsStream(configGetter.getAppIconName());
	        
			ImageIcon icon = new ImageIcon(ImageIO.read(inputStreamFromPng));
			
			setIconImage(icon.getImage());
		} catch (IOException e) {
			logger.error("Unable to set the image icon for application. Resource: {}", configGetter.getAppIconName(), e);
		}
    }

    private void setFrameDimensionsAndBehaviors(){
        this.setMinimumSize(new Dimension(configGetter.getWindowWidth()/2,configGetter.getWindowHeight()/2));
        this.setSize(new Dimension(configGetter.getWindowWidth(),configGetter.getWindowHeight()));
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setResizable(true);
        this.setVisible(true);
    }

	public ExecutorService getExecutor() {
		return executor;
	}

	public void setExecutor(ExecutorService executor) {
		this.executor = executor;
	}

	public ConfigurationGetter getConfigGetter() {
		return configGetter;
	}

	@Override
	public void componentResized(ComponentEvent e) {
		this.mainWindowContainer.getBottomPanel().setDividerLocation(getWidth()/2);
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

	public MainWindowContainer getMainWindowContainer() {
		return mainWindowContainer;
	}
	
}