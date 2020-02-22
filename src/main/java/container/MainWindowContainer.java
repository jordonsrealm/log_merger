package container;

import listeners.MergeButtonListener;
import threads.GlassPaneProcessingThread;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import components.MainWindow;
import factory.CenteredPointFactory;
import factory.CenteredPointType;

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.nio.charset.StandardCharsets;


public class MainWindowContainer {

    private static final Logger logger = LoggerFactory.getLogger(MainWindowContainer.class);
    private JTextField fileNameInputTextField;
    private JScrollPane unOrganizedScrollPane;
    private JScrollPane organizedScrollPane;
    private JButton clearUnorganizedText;
    private JCheckBox ascendDescendOrder;
    private JTextField patternTextField;
    private JSplitPane bottomSplitPane;
    private JTextArea unOrganizedText;
    private JTextArea organizedText;
    private JTextField minDateField;
    private JTextField maxDateField;
    private JButton selectFileBtn;
    private MainWindow mainWindow;
    private JButton inputFileBtn;
    private JButton mergeBtn;
    private JButton saveToFile;
    private JPanel topPanel;

    
    public MainWindowContainer(MainWindow frame) {
    	this.setMainWindow(frame);
    }

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

    public void setMergeButtonListener(MergeButtonListener listener){
    	this.mergeBtn.addActionListener(listener);
    }

    public JTextField getFileNameInputTextField() {
    	return fileNameInputTextField;
    }

    public void setFileNameInputTextField(JTextField fileNameInput) {
    	this.fileNameInputTextField = fileNameInput;
    }

    public JButton getSelectFileBtn() {
    	return selectFileBtn;
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

    public void setSelectFileBtn(JButton selectFileBtn) {
        selectFileBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	
            	selectFileBtn.setEnabled(false);
            	unOrganizedText.setEnabled(false);
            	
                File file = new File(fileNameInputTextField.getText());
                String result = unOrganizedText.getText();
                
                Point centeredPoint = CenteredPointFactory.getCenteredPoint(CenteredPointType.NOT_ORDERED, mainWindow).getCenteredPoint();
            	GlassPaneProcessingThread processingThread = new GlassPaneProcessingThread(mainWindow, centeredPoint);
            	processingThread.startProcessing();
            	
            	try{
                    result += IOUtils.toString(new FileInputStream(file), StandardCharsets.UTF_8);
                    result = result.strip();
                } catch(IOException exx){
                	logger.error("Unable to read file input:{} and add to text area", file, exx);
                }

                unOrganizedText.setText(result);
                unOrganizedText.setCaretPosition(0);
                
                unOrganizedScrollPane.getHorizontalScrollBar().setValue(0);
                
                processingThread.stopProcessing();
                
                selectFileBtn.setEnabled(true);
                unOrganizedText.setEnabled(true);
            }
        });
        
        this.selectFileBtn = selectFileBtn;
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

	public MainWindow getMainWindow() {
		return mainWindow;
	}

	public void setMainWindow(MainWindow mainWindow) {
		this.mainWindow = mainWindow;
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

}
