package components;

import date_object.DateHolder;
import listeners.MergeBtnListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;


public class MainWindow extends JFrame {

    private static final Logger logger = LoggerFactory.getLogger(MainWindow.class);

    private JButton mergeBtn;
    private JTextField patternTextField;
    private JTextArea unOrganizedText;
    private JTextArea organizedText;
    private JCheckBox ascendDescendOrder;
    private final String MAIN_TITLE = "FILE MERGER";
    private final String BTN_TITLE  = "Merge Files";
    private final String UN_ORDERED_TEXT = "UN-ORDERED TEXT";
    private final String ORDERED_TEXT    = "ORDERED TEXT";
    private static Dimension MINIMUM_DIMENSION = new Dimension(600,400);
    private static Dimension PREFERRED_SIZE = new Dimension(1000,600);


    public void populateFrame(){
        setTitle(MAIN_TITLE);
        setLayout(new BorderLayout());

        mergeBtn           = new JButton(BTN_TITLE);
        patternTextField   = new JTextField(50);
        unOrganizedText    = new JTextArea("",5,45);
        organizedText      = new JTextArea("",5,45);
        ascendDescendOrder = new JCheckBox("Descending?");

        TitledBorder unOrganizedTitledBorder   = BorderFactory.createTitledBorder(UN_ORDERED_TEXT);
        TitledBorder organizedTextTitledBorder = BorderFactory.createTitledBorder(ORDERED_TEXT);

        // Set the title border for the two text areas
        unOrganizedText.setBorder(unOrganizedTitledBorder);
        organizedText.setBorder(organizedTextTitledBorder);

        // Set default pattern
        patternTextField.setText(DateHolder.DEFAULT_FORMAT);

        MergeBtnListener listener = new MergeBtnListener(patternTextField, unOrganizedText, organizedText, ascendDescendOrder);
        mergeBtn.addActionListener(listener);

        // Top Panel Construction
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BorderLayout());
        topPanel.add(mergeBtn, BorderLayout.EAST);
        patternTextField.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        topPanel.add(patternTextField, BorderLayout.CENTER);
        topPanel.add(ascendDescendOrder, BorderLayout.WEST);

        // Bottom Panel Construction
        JScrollPane unOrganizedScrollPane = new JScrollPane(unOrganizedText);
        JScrollPane organizedScrollPane   = new JScrollPane(organizedText);

        // Set scrollbars on the scroll panes
        unOrganizedScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        unOrganizedScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        organizedScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        organizedScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

        JSplitPane splitPaneBottomPanel = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, unOrganizedScrollPane, organizedScrollPane);

        // Add to JFrame
        this.add(topPanel, BorderLayout.NORTH);
        this.add(splitPaneBottomPanel, BorderLayout.CENTER);

        // Change the icon image
        ImageIcon img = new ImageIcon("../../app_icon.png");
        logger.info("Logger user directory: {}" ,System.getProperty("user.dir"));
        setIconImage(img.getImage());
    }

    public void setFrameConstraints(){
        this.setVisible(true);
        this.setResizable(true);
        this.setMinimumSize(this.MINIMUM_DIMENSION);
        this.setSize(this.PREFERRED_SIZE);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }
}