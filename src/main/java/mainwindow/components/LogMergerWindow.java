package mainwindow.components;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutorService;
import configuration.ConfigurationGetter;
import mainwindow.components.holder.ButtonHolder;
import mainwindow.components.holder.CheckBoxHolder;
import mainwindow.components.holder.TextHolder;
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
    private transient MainWindowHolder windowHolder;
    private transient ExecutorService executor;
    
    private static final String DEFAULT_REGEX_HINT = "yyyy-MM-dd HH:mm:ss.SSS";
    private static final String DATE_PATTERN = "DATE PATTERN";
    private static final String MIN_DATE_STR = "Min Date";
    private static final String MAX_DATE_STR = "Max Date";
    private static final String WHITE_BACKGROUND = "0xffffff";
    

    public LogMergerWindow(ExecutorService executor) {
    	setLayout(new BorderLayout());
        setTitle(ConfigurationGetter.instance().getApplicationName());
    	setExecutor(executor);
        addComponentListener(this);
        
        setWindowHolder(new MainWindowHolder(this));
        
        getWindowHolder().setTopPanel(createTopPanel());
        getWindowHolder().setBottomPanel(createBottomSplitPane());
        
        add(getWindowHolder().getTopPanel(), BorderLayout.NORTH);
        add(getWindowHolder().getBottomPanel(), BorderLayout.CENTER);
        
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
        this.setMinimumSize(new Dimension(ConfigurationGetter.instance().getConfigWindowW(),ConfigurationGetter.instance().getConfigWindowH()));
        this.setSize(new Dimension(ConfigurationGetter.instance().getConfigWindowW(),ConfigurationGetter.instance().getConfigWindowH()));
        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setResizable(true);
        this.setVisible(true);
    }
    
    private JPanel createTopPanel() {
    	MainWindowHolder wHolder = getWindowHolder();
    	ButtonHolder btnH = wHolder.getBtnHolder();
    	TextHolder txtH = wHolder.getTxtHolder();
    	
        JPanel dateSection = new JPanel(new FlowLayout());
        dateSection.add(btnH.getSearchButton());
        dateSection.add(txtH.getFileNameInputTextField());
        dateSection.add(btnH.getAddFileButton());
        
        JPanel topMostPanel = new JPanel(new BorderLayout());
        topMostPanel.add(dateSection, BorderLayout.CENTER);
        
        return topMostPanel;
    }
    
    private JSplitPane createBottomSplitPane(){
    	MainWindowHolder wHolder = getWindowHolder();
    	ButtonHolder btnH = wHolder.getBtnHolder();
    	TextHolder txtH = wHolder.getTxtHolder();
    	CheckBoxHolder chxH = wHolder.getCheckBoxHolder();
    	
        JPanel rightUpperPanel = new JPanel(new FlowLayout());
        rightUpperPanel.add(chxH.getDescendingCheckBox());
        rightUpperPanel.add(new JLabel(MIN_DATE_STR));
        rightUpperPanel.add(txtH.getMinDateField());
        rightUpperPanel.add(new JLabel(MAX_DATE_STR));
        rightUpperPanel.add(txtH.getMaxDateField());
        rightUpperPanel.add(btnH.getSaveFileButton());
        
        JPanel rightPanel = new JPanel(new BorderLayout());
        rightPanel.add(rightUpperPanel, BorderLayout.NORTH);
        rightPanel.add(txtH.getOrderedScrollPane(), BorderLayout.CENTER);
        
        JPanel patternAndClearPanel = new JPanel(new FlowLayout());
        patternAndClearPanel.add(btnH.getClearUnOrderedTextButton());
        JLabel dateLabel = new JLabel(DATE_PATTERN);
        patternAndClearPanel.add(dateLabel);
        txtH.getRegexPatternTextField().setText(DEFAULT_REGEX_HINT);
        txtH.getRegexPatternTextField().setBackground(Color.decode(WHITE_BACKGROUND));
        patternAndClearPanel.add(txtH.getRegexPatternTextField());
        patternAndClearPanel.add(btnH.getMergeButton());
        
        JPanel leftPanel = new JPanel(new BorderLayout());
        leftPanel.add(patternAndClearPanel, BorderLayout.NORTH);
        leftPanel.add(txtH.getUnOrderedScrollPane(), BorderLayout.CENTER);

        JSplitPane bottomSplitPane;
        bottomSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftPanel, rightPanel);
        bottomSplitPane.setDividerLocation(ConfigurationGetter.instance().getConfigWindowW()/2);

        return bottomSplitPane;
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

	public MainWindowHolder getWindowHolder() {
		return this.windowHolder;
	}
	
	public void setWindowHolder(MainWindowHolder holder) {
		this.windowHolder = holder;
	}

	@Override
	public void componentMoved(ComponentEvent e) {/* Didn't want to extend the ComponentAdapter*/}

	@Override
	public void componentShown(ComponentEvent e) {/* Didn't want to extend the ComponentAdapter*/}

	@Override
	public void componentHidden(ComponentEvent e) {/* Didn't want to extend the ComponentAdapter*/}
	
}