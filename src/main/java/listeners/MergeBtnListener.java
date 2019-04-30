package listeners;

import container_pattern.MainWindowContainer;
import date_object.DateHolder;
import transfer_object.DateResult;

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
import java.util.Date;


public class MergeBtnListener implements ActionListener {

    private JTextField format;
    private JTextArea unOrganizedText;
    private JTextArea organizedText;
    private JCheckBox checkBox;
    private JTextField minDate;
    private JTextField maxDate;
    private JFrame frame;


    public MergeBtnListener(MainWindowContainer mainWindowContainer, JFrame frame){
        this.format          = mainWindowContainer.getPatternTextField();
        this.unOrganizedText = mainWindowContainer.getUnOrganizedText();
        this.organizedText   = mainWindowContainer.getOrganizedText();
        this.checkBox        = mainWindowContainer.getAscendDescendOrder();
        this.minDate         = mainWindowContainer.getMinDateField();
        this.maxDate         = mainWindowContainer.getMaxDateField();
        this.frame           = frame;
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
            String formattedStr = format.getText();
            while((lineRead = bufferedReader.readLine()) != null){
                DateResult res = DateHolder.getDateFromEntireString(lineRead,formattedStr);

                if(res.isValidDate()){
                    dateHolder.add(new DateHolder(lineRead, formattedStr));
                } else{
                    dateHolder.get(dateHolder.size() - 1).appendStrToOrigStr("\n"+lineRead);
                }
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
                if(holder.getDate().after(minimumDate) && holder.getDate().before(maximumDate)){
                    appendingStr = holder.getOgStr() + "\n";
                }
            } else if(minimumDate != null){
                if(holder.getDate().after(minimumDate)){
                    appendingStr = holder.getOgStr() + "\n";
                }
            } else if(maximumDate != null){
                if(holder.getDate().before(maximumDate)){
                    appendingStr = holder.getOgStr() + "\n";
                }
            } else{
                appendingStr = holder.getOgStr() + "\n";
            }

            builder.append(appendingStr);
        }

        organizedText.setText(builder.toString());
    }
}
