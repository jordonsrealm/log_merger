package listeners;

import date_object.DateHolder;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Collections;


public class MergeBtnListener implements ActionListener {

    private JTextField format;
    private JTextArea unOrganizedText;
    private JTextArea organizedText;


    public MergeBtnListener(JTextField format, JTextArea notOrganizedText, JTextArea organizedText){
        this.format = format;
        this.unOrganizedText = notOrganizedText;
        this.organizedText = organizedText;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String inputStr = unOrganizedText.getText();

        Reader inputString = new StringReader(inputStr);

        BufferedReader bufferedReader = new BufferedReader(inputString);
        StringBuilder builder = new StringBuilder();
        String lineRead;
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
}
