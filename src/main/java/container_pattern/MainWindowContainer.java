package container_pattern;

import listeners.MergeBtnListener;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.nio.charset.StandardCharsets;


public class MainWindowContainer {

    //private static final Logger logger = LoggerFactory.getLogger(MainWindowContainer.class);
    private JButton mergeBtn;
    private JTextField patternTextField;
    private JTextArea unOrganizedText;
    private JTextArea organizedText;
    private JCheckBox ascendDescendOrder;
    private JTextField minDateField;
    private JTextField maxDateField;
    private JTextField fileNameInputTextField;
    private JButton selectFileBtn;
    private JButton inputFileBtn;
    private JButton clearUnorganizedText;


    public JButton getMergeBtn() {
        return mergeBtn;
    }

    public void setMergeBtn(JButton mergeBtn) {
        this.mergeBtn = mergeBtn;
    }

    public JTextField getPatternTextField() {
        return patternTextField;
    }

    public void setPatternTextField(JTextField patternTextField) {
        this.patternTextField = patternTextField;
    }

    public JTextArea getUnOrganizedText() {
        return unOrganizedText;
    }

    public void setUnOrganizedText(JTextArea unOrganizedText) {
        this.unOrganizedText = unOrganizedText;
    }

    public JTextArea getOrganizedText() {
        return organizedText;
    }

    public void setOrganizedText(JTextArea organizedText) {
        this.organizedText = organizedText;
    }

    public JCheckBox getAscendDescendOrder() {
        return ascendDescendOrder;
    }

    public void setAscendDescendOrder(JCheckBox ascendDescendOrder) {
        this.ascendDescendOrder = ascendDescendOrder;
    }

    public JTextField getMinDateField() {
        return minDateField;
    }

    public void setMinDateField(JTextField minDateField) {
        this.minDateField = minDateField;
    }

    public JTextField getMaxDateField() {
        return maxDateField;
    }

    public void setMaxDateField(JTextField maxDateField) {
        this.maxDateField = maxDateField;
    }

    public void setMergeBtnListener(MergeBtnListener listener){this.mergeBtn.addActionListener(listener);}

    public JTextField getFileNameInputTextField() {return fileNameInputTextField;}

    public void setFileNameInputTextField(JTextField fileNameInput) {this.fileNameInputTextField = fileNameInput; }

    public JButton getSelectFileBtn() { return selectFileBtn; }

    public JButton getInputFileBtn() { return inputFileBtn; }

    public void setInputFileBtn(JButton inputFileBtn) { this.inputFileBtn = inputFileBtn; }

    public JButton getClearUnorganizedText() {
        clearUnorganizedText.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                unOrganizedText.setText("");
            }
        });
        return clearUnorganizedText;
    }

    public void setClearUnorganizedText(JButton clearUnorganizedText) { this.clearUnorganizedText = clearUnorganizedText; }

    public void setSelectFileBtn(JButton selectFileBtn) {
        selectFileBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                File file = new File(fileNameInputTextField.getText());
                String result = unOrganizedText.getText();

                try{
                    result += IOUtils.toString(new FileInputStream(file), StandardCharsets.UTF_8);
                } catch(IOException exx){

                }

                result = result.strip();

                //logger.debug(result);

                unOrganizedText.setText(result);
            }
        });
        this.selectFileBtn = selectFileBtn;
    }

    public JButton getFileInputButton() { return inputFileBtn; }

    public void setFileInputButton(JButton fileInput) {
        fileInput.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());

                int returnValue = jfc.showOpenDialog(null);

                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = jfc.getSelectedFile();
                    String result = unOrganizedText.getText();

                    try{
                        result += IOUtils.toString(new FileInputStream(selectedFile), StandardCharsets.UTF_8);
                    } catch(IOException exx){

                    }

                    result = result.strip();
                    //logger.debug(result);

                    unOrganizedText.setText(result);
                }
            }
        });
        this.inputFileBtn = fileInput;
    }


}
