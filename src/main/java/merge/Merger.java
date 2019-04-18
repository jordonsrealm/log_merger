package merge;

import date_object.DateHolder;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Collections;

public class Merger {

    private static JButton mergeBtn;
    private static JTextArea format;
    private static JTextArea unOrganizedText;
    private static JTextArea organizedText;

    private static final String MAIN_TITLE = "FILE MERGER";
    private static final String BTN_TITLE = "Merge Files";


    public static void main(String[] args){
        JFrame frame = new JFrame(MAIN_TITLE);

        frame.setLayout(new BorderLayout());
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        mergeBtn = new JButton(BTN_TITLE);
        format = new JTextArea(1, 50);
        format.setText(DateHolder.DEFAULT_FORMAT);
        unOrganizedText = new JTextArea(1, 45);
        organizedText   = new JTextArea(1,45);
        unOrganizedText.setPreferredSize(new Dimension(60,100));
        organizedText.setPreferredSize(new Dimension(60,100));

        panel.add(unOrganizedText, BorderLayout.WEST);
        panel.add(organizedText,   BorderLayout.EAST);

        mergeBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String inputStr = unOrganizedText.getText();

                Reader inputString = new StringReader(inputStr);

                BufferedReader bufferedReader = new BufferedReader(inputString);
                String lineRead = "";
                StringBuilder builder = new StringBuilder();

                ArrayList<DateHolder> dateHolder = new ArrayList<DateHolder>();

                try {
                    while((lineRead = bufferedReader.readLine()) != null){
                        dateHolder.add(new DateHolder(lineRead, format.getText()));
                    }
                } catch (IOException e1) {
                    e1.printStackTrace();
                }

                Collections.sort(dateHolder);

                for(DateHolder holder: dateHolder){
                    builder.append(holder.getOriginalString() + "\n");
                }

                organizedText.setText(builder.toString());
            }
        });

        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BorderLayout());
        topPanel.add(mergeBtn, BorderLayout.NORTH);
        format.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        topPanel.add(format, BorderLayout.SOUTH);

        frame.add(topPanel, BorderLayout.NORTH);
        frame.add(panel, BorderLayout.CENTER);

        frame.setVisible(true);
        frame.setResizable(false);
        frame.setSize(new Dimension(1000,600));
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }
}
