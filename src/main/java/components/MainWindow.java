package components;

import date_object.DateHolder;
import listeners.MergeBtnListener;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;


public class MainWindow extends JFrame {

    private JButton mergeBtn;
    private JTextField format;
    private JTextArea unOrganizedText;
    private JTextArea organizedText;
    private final String MAIN_TITLE = "FILE MERGER";
    private final String BTN_TITLE = "Merge Files";
    private final String UN_ORDERED_TEXT = "UN-ORDERED TEXT";
    private final String ORDERED_TEXT    = "ORDERED TEXT";
    private static Dimension MINIMUM_DIMENSION = new Dimension(600,400);
    private static Dimension PREFERRED_SIZE = new Dimension(1000,600);


    public void populateFrame(){
        setTitle(MAIN_TITLE);
        setLayout(new BorderLayout());

        mergeBtn        = new JButton(BTN_TITLE);
        format          = new JTextField(50);
        unOrganizedText = new JTextArea("",5,45);
        organizedText   = new JTextArea("",5,45);

        TitledBorder unOrganizedTitledBorder   = BorderFactory.createTitledBorder(UN_ORDERED_TEXT);
        TitledBorder organizedTextTitledBorder = BorderFactory.createTitledBorder(ORDERED_TEXT);

        unOrganizedText.setBorder(unOrganizedTitledBorder);
        organizedText.setBorder(organizedTextTitledBorder);

        format.setText(DateHolder.DEFAULT_FORMAT);

        MergeBtnListener listener = new MergeBtnListener(format, unOrganizedText, organizedText);
        mergeBtn.addActionListener(listener);

        // Top Panel Construction
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BorderLayout());
        topPanel.add(mergeBtn, BorderLayout.WEST);
        format.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        topPanel.add(format, BorderLayout.CENTER);

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
    }

    public void setFrameConstraints(){
        this.setVisible(true);
        this.setResizable(true);
        this.setMinimumSize(this.MINIMUM_DIMENSION);
        this.setSize(this.PREFERRED_SIZE);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    public JButton getMergeBtn() {
        return mergeBtn;
    }

    public JTextField getFormat() {
        return format;
    }

    public JTextArea getUnOrganizedText() {
        return unOrganizedText;
    }

    public JTextArea getOrganizedText() {
        return organizedText;
    }

    public String getMAIN_TITLE() {
        return MAIN_TITLE;
    }

    public String getBTN_TITLE() {
        return BTN_TITLE;
    }
}