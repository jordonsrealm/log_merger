package container_pattern;

import listeners.MergeBtnListener;

import javax.swing.*;


public class MainWindowContainer {

    private JButton mergeBtn;
    private JTextField patternTextField;
    private JTextArea unOrganizedText;
    private JTextArea organizedText;
    private JCheckBox ascendDescendOrder;
    private JTextField minDateField;
    private JTextField maxDateField;


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
}
