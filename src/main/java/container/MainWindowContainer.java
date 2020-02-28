package container;

import listeners.MergeButtonListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import components.OrderedTextArea;
import javax.swing.*;
import javax.swing.filechooser.FileSystemView;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;


public class MainWindowContainer {

    private static final Logger logger = LoggerFactory.getLogger(MainWindowContainer.class);
    private JTextField fileNameInputTextField;
    private JScrollPane unOrganizedScrollPane;
    private JScrollPane organizedScrollPane;
    private JButton clearUnorganizedText;
    private JTextField patternTextField;
    private JSplitPane bottomSplitPane;
    private JTextArea unOrganizedText;
    private OrderedTextArea organizedText;
    private JTextField minDateField;
    private JTextField maxDateField;
    private JButton useFileBtn;
    private JButton inputFileBtn;
    private JButton mergeBtn;
    private JButton saveToFile;
    private JPanel topPanel;
    private Component glassPane;
    

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
    	patternTextField.setBackground(Color.decode("0xffffff"));
        this.patternTextField = patternTextField;
    }

    public JTextArea getUnOrganizedText() {
        return unOrganizedText;
    }

    public void setUnOrganizedText(JTextArea unOrganizedText) {
        this.unOrganizedText = unOrganizedText;
    }

    public OrderedTextArea getOrganizedText() {
        return organizedText;
    }

    public void setOrganizedText(OrderedTextArea organizedText) {
        this.organizedText = organizedText;
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

    public void setMergeButtonListener(MergeButtonListener listener){
    	this.mergeBtn.addActionListener(listener);
    }

    public JTextField getFileNameInputTextField() {
    	return fileNameInputTextField;
    }

    public void setFileNameInputTextField(JTextField fileNameInput) {
    	this.fileNameInputTextField = fileNameInput;
    }

    public JButton getUseFileBtn() {
    	return useFileBtn;
    }

    public JButton getInputFileBtn() {
    	return inputFileBtn;
    }

    public void setInputFileBtn(JButton inputFileBtn) {
    	this.inputFileBtn = inputFileBtn;
    }

    public JButton getClearUnorganizedText() {
        clearUnorganizedText.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                unOrganizedText.setText("");
            }
        });
        
        return clearUnorganizedText;
    }

    public JButton getSaveToFile() {
        return saveToFile;
    }

    public void setSaveToFile(JButton saveToFile) {
        saveToFile.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
                jfc.setDialogTitle("Specify a file to save");

                int userSelection = jfc.showSaveDialog(null);

                if (userSelection == JFileChooser.APPROVE_OPTION) {
                    File fileToSave = jfc.getSelectedFile();
                    logger.debug("Saving file as: " + fileToSave.getAbsolutePath());

                    try(FileOutputStream outputStream = new FileOutputStream(fileToSave)){
                        byte[] bytes = organizedText.getText().getBytes();
                        outputStream.write(bytes);
                    } catch(FileNotFoundException ex){
                        logger.error("Unable to find the file to save to...", ex);
                    } catch (IOException ioEx){
                        logger.error("Uable to create file to save to...", ioEx);
                    }

                    logger.info("Saved to file: {}", fileToSave.getName());
                }
            }
        });
        this.saveToFile = saveToFile;
    }

    public void setClearUnorganizedText(JButton clearUnorganizedText) { 
    	this.clearUnorganizedText = clearUnorganizedText; 
    }

    public void setUseFileBtn(JButton selectFileBtn) {
        this.useFileBtn = selectFileBtn;
    }

    public JScrollPane getUnOrganizedScrollPane() {
		return unOrganizedScrollPane;
	}

	public void setUnOrganizedScrollPane(JScrollPane unOrganizedScrollPane) {
		this.unOrganizedScrollPane = unOrganizedScrollPane;
	}

	public JScrollPane getOrganizedScrollPane() {
		return organizedScrollPane;
	}

	public void setOrganizedScrollPane(JScrollPane organizedScrollPane) {
		this.organizedScrollPane = organizedScrollPane;
	}

	public JButton getFileInputButton() { 
		return inputFileBtn; 
	}

    public void setFileInputButton(JButton fileInput) {
        fileInput.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());

                int returnValue = jfc.showOpenDialog(null);

                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = jfc.getSelectedFile();
                    
                    fileNameInputTextField.setText(selectedFile.getAbsolutePath());
                }
            }
        });
        
        this.inputFileBtn = fileInput;
    }

	public void setTopPanel(JPanel topPanel) {
		this.topPanel = topPanel;
	}
	
	public JPanel getTopPanel() {
		return this.topPanel;
	}
	
	public JSplitPane getBottomSplitPane() {
		return this.bottomSplitPane;
	}
	
	public void setBottomSplitPane(JSplitPane splitPane) {
		this.bottomSplitPane = splitPane;
	}

	public Component getGlassPane() {
		return glassPane;
	}

	public void setGlassPane(Component glassPane) {
		this.glassPane = glassPane;
	}
}
