package logmerger.frame;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutorService;
import configuration.ConfigurationGetter;
import logmerger.frame.listener.LogMergerFrameListener;
import window.components.holder.ButtonHolder;
import window.components.holder.CheckBoxHolder;
import window.components.holder.TextHolder;
import window.holder.WindowComponentHolder;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.io.InputStream;
import javax.swing.*;
import java.awt.*;


public class LogMergerFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private static final Logger logger = LoggerFactory.getLogger(LogMergerFrame.class);
    private transient WindowComponentHolder windowHolder;
    private transient ExecutorService executor;

    private static final String DATE_PATTERN = "DATE PATTERN";
    private static final String MIN_DATE_STR = "Min Date";
    private static final String MAX_DATE_STR = "Max Date";
    

    public LogMergerFrame(ExecutorService executor) {
    	this.setExecutor(executor);
    	this.setLayout(new BorderLayout());
    	this.setTitle(ConfigurationGetter.instance().getApplicationName());
    	this.setWindowComponentHolder(new WindowComponentHolder(this));
        
    	this.add(createdTextAreas(), BorderLayout.CENTER);
        
    	this.setImageIconForApplication();
    	this.setFrameDimensionsAndBehaviors();
        
    	this.addComponentListener(new LogMergerFrameListener(this));
    }
    
    private void setImageIconForApplication() {
		try {
			InputStream inputStreamFromPng = getClass().getClassLoader().getResourceAsStream(ConfigurationGetter.instance().getAppIconFileName());
			ImageIcon icon = new ImageIcon(ImageIO.read(inputStreamFromPng));
			setIconImage(icon.getImage());
		} catch (IOException e) {
			logger.error("Unable to set the image icon for application. Resource: {}", ConfigurationGetter.instance().getAppIconFileName(), e);
		}
    }

    private void setFrameDimensionsAndBehaviors(){
        this.setSize(new Dimension(ConfigurationGetter.instance().getConfigWindowW(), ConfigurationGetter.instance().getConfigWindowH()));
        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setResizable(true);
        this.setVisible(true);
    }
    
    private JSplitPane createdTextAreas(){
    	WindowComponentHolder wHolder = getWindowComponentHolder();
    	CheckBoxHolder chxH = wHolder.getCheckBoxHolder();
    	ButtonHolder btnH = wHolder.getBtnHolder();
    	TextHolder txtH = wHolder.getTxtHolder();
    	
        JPanel rightUpperPanelLoggingLevel = new JPanel(new FlowLayout());
        rightUpperPanelLoggingLevel.add(chxH.getErrorCheckBox());
        rightUpperPanelLoggingLevel.add(chxH.getInfoCheckBox());
        rightUpperPanelLoggingLevel.add(chxH.getTraceCheckBox());
        rightUpperPanelLoggingLevel.add(chxH.getWarnCheckBox());
        rightUpperPanelLoggingLevel.add(chxH.getDebugCheckBox());
        rightUpperPanelLoggingLevel.add(chxH.getUnknownCheckBox());
        
        JPanel rightUpperPanelDateOrdering = new JPanel(new FlowLayout());
        rightUpperPanelDateOrdering.add(chxH.getDescendingCheckBox());
        rightUpperPanelDateOrdering.add(new JLabel(MIN_DATE_STR));
        rightUpperPanelDateOrdering.add(txtH.getMinDateField());
        rightUpperPanelDateOrdering.add(new JLabel(MAX_DATE_STR));
        rightUpperPanelDateOrdering.add(txtH.getMaxDateField());
        rightUpperPanelDateOrdering.add(btnH.getSaveFileButton());
        
        JPanel rightUpperPanelParent = new JPanel(new BorderLayout());
        rightUpperPanelParent.add(rightUpperPanelLoggingLevel, BorderLayout.NORTH);
        rightUpperPanelParent.add(rightUpperPanelDateOrdering, BorderLayout.SOUTH);
        
        JPanel rightParentPanel = new JPanel(new BorderLayout());
        rightParentPanel.add(rightUpperPanelParent, BorderLayout.NORTH);
        rightParentPanel.add(txtH.getOrderedScrollPane(), BorderLayout.CENTER);
        
        JPanel regexPatternSection = new JPanel(new FlowLayout());
        regexPatternSection.add(btnH.getClearUnOrderedTextButton());
        regexPatternSection.add(new JLabel(DATE_PATTERN));
        regexPatternSection.add(txtH.getRegexPatternTextField());
        regexPatternSection.add(btnH.getMergeButton());
        
        JPanel leftParentPanel = new JPanel(new BorderLayout());
        leftParentPanel.add(regexPatternSection, BorderLayout.NORTH);
        leftParentPanel.add(txtH.getUnOrderedScrollPane(), BorderLayout.CENTER);

        JSplitPane parentContainer;
        parentContainer = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftParentPanel, rightParentPanel);
        parentContainer.setDividerLocation(ConfigurationGetter.instance().getConfigWindowW()/2);
        
        getWindowComponentHolder().setMergingSplitPane(parentContainer);

        return parentContainer;
    }

	public ExecutorService getExecutor() {
		return this.executor;
	}

	public void setExecutor(ExecutorService executor) {
		this.executor = executor;
	}

	public WindowComponentHolder getWindowComponentHolder() {
		return this.windowHolder;
	}
	
	public void setWindowComponentHolder(WindowComponentHolder holder) {
		this.windowHolder = holder;
	}
}