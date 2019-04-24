package listeners;

import container_pattern.MainWindowContainer;
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
import java.util.Date;


public class MergeBtnListener implements ActionListener {

    private JTextField format;
    private JTextArea unOrganizedText;
    private JTextArea organizedText;
    private JCheckBox checkBox;
    private JTextField minDate;
    private JTextField maxDate;


    public MergeBtnListener(MainWindowContainer mainWindowContainer){
        this.format          = mainWindowContainer.getPatternTextField();
        this.unOrganizedText = mainWindowContainer.getUnOrganizedText();
        this.organizedText   = mainWindowContainer.getOrganizedText();
        this.checkBox        = mainWindowContainer.getAscendDescendOrder();
        this.minDate         = mainWindowContainer.getMinDateField();
        this.maxDate         = mainWindowContainer.getMaxDateField();
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        organizedText.setText("");

        Reader inputString = new StringReader(unOrganizedText.getText());

        BufferedReader bufferedReader = new BufferedReader(inputString);
        StringBuilder builder = new StringBuilder();
        String lineRead;
        ArrayList<DateHolder> dateHolder = new ArrayList<>();

        try {
            DateHolder.invertComparison = checkBox.isSelected();
            while((lineRead = bufferedReader.readLine()) != null){
                dateHolder.add(new DateHolder(lineRead, format.getText()));
            }
        } catch (IOException e1) {
            e1.printStackTrace();
        }

        Collections.sort(dateHolder);

        for(DateHolder holder: dateHolder){
            String appendingStr = "";

            Date minimumDate = DateHolder.getDateFromString(minDate.getText());
            Date maximumDate = DateHolder.getDateFromString(maxDate.getText());

            if(minimumDate != null && maximumDate != null){
                if(holder.getDateObject().after(minimumDate) && holder.getDateObject().before(maximumDate)){
                    appendingStr = holder.getOriginalString() + "\n";
                }
            } else if(minimumDate != null){
                if(holder.getDateObject().after(minimumDate)){
                    appendingStr = holder.getOriginalString() + "\n";
                }
            } else if(maximumDate != null){
                if(holder.getDateObject().before(maximumDate)){
                    appendingStr = holder.getOriginalString() + "\n";
                }
            } else{
                appendingStr = holder.getOriginalString() + "\n";
            }

            builder.append(appendingStr);
        }

        organizedText.setText(builder.toString());
    }
}
