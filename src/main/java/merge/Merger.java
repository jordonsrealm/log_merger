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

        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new BorderLayout());

        mergeBtn = new JButton(BTN_TITLE);
        format = new JTextArea(1, 50);
        unOrganizedText = new JTextArea("",5,45);
        organizedText   = new JTextArea("",5,45);

        JScrollPane unOrganizedScrollPane = new JScrollPane(unOrganizedText);
        JScrollPane organizedScrollPane   = new JScrollPane(organizedText);

        unOrganizedScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        unOrganizedScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        organizedScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        organizedScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

        format.setText(DateHolder.DEFAULT_FORMAT);

        bottomPanel.add(unOrganizedScrollPane, BorderLayout.WEST);
        bottomPanel.add(organizedScrollPane,   BorderLayout.EAST);

        mergeBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String inputStr = unOrganizedText.getText();

                Reader inputString = new StringReader(inputStr);

                BufferedReader bufferedReader = new BufferedReader(inputString);
                String lineRead = "";
                StringBuilder builder = new StringBuilder();

                ArrayList<DateHolder> dateHolder = new ArrayList<>();

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
        frame.add(bottomPanel, BorderLayout.CENTER);

        frame.setVisible(true);
        frame.setResizable(false);
        frame.setSize(new Dimension(1000,600));
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }
}
