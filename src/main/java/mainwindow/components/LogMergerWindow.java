package mainwindow.components;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutorService;
import configuration.ConfigurationGetter;
import mainwindow.components.holder.ButtonHolder;
import mainwindow.components.holder.CheckBoxHolder;
import mainwindow.components.holder.TextHolder;
import mainwindow.holder.WindowComponentHolder;
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
    private transient WindowComponentHolder windowHolder;
    private transient ExecutorService executor;

    private static final String DATE_PATTERN = "DATE PATTERN";
    private static final String MIN_DATE_STR = "Min Date";
    private static final String MAX_DATE_STR = "Max Date";
    

    public LogMergerWindow(ExecutorService executor) {
    	setLayout(new BorderLayout());
        setTitle(ConfigurationGetter.instance().getApplicationName());
    	setExecutor(executor);
        addComponentListener(this);
        
        setWindowComponentHolder(new WindowComponentHolder(this));
        
        getWindowComponentHolder().setMergingSplitPane(textAreas());
        
        add(getWindowComponentHolder().getMergingSplitPane(), BorderLayout.CENTER);
        
        setImageIconForApplication();
        setFrameDimensionsAndBehaviors();
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
        this.setSize(new Dimension(ConfigurationGetter.instance().getConfigWindowW(),ConfigurationGetter.instance().getConfigWindowH()));
        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setResizable(true);
        this.setVisible(true);
    }
    
    private JSplitPane textAreas(){
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

        return parentContainer;
    }

	public ExecutorService getExecutor() {
		return this.executor;
	}

	public void setExecutor(ExecutorService executor) {
		this.executor = executor;
	}

	@Override
	public void componentResized(ComponentEvent e) {
		getWindowComponentHolder().getMergingSplitPane().setDividerLocation(getWidth()/2);
	}

	public WindowComponentHolder getWindowComponentHolder() {
		return this.windowHolder;
	}
	
	public void setWindowComponentHolder(WindowComponentHolder holder) {
		this.windowHolder = holder;
	}

	@Override
	public void componentMoved(ComponentEvent e) {/* Didn't want to extend the ComponentAdapter*/}

	@Override
	public void componentShown(ComponentEvent e) {/* Didn't want to extend the ComponentAdapter*/}

	@Override
	public void componentHidden(ComponentEvent e) {/* Didn't want to extend the ComponentAdapter*/}
	
}