package mainwindow.components;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingWorker;
import javax.swing.text.Document;
import javax.swing.text.Highlighter;
import javax.swing.text.JTextComponent;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import centerpoint.object.CenteredPointType;
import highlighter.UnOrganizedHighlighter;
import mainwindow.holder.MainWindowHolder;
import threads.ProcessLogo;


public class AddFileButton extends AbstractMainWindowContainerButton {
	
	private static final long serialVersionUID = 1L;
	private static final Dimension setDim = new Dimension( 25, 25);
	private static final Logger logger = LoggerFactory.getLogger(AddFileButton.class);
	private static UnOrganizedHighlighter myHighlightPainter;
	private static final String ADD_FILE_TOOL_TIP = "Add File";
	
	
	public AddFileButton(LogMergerWindow logMergerWindow) {
		super(logMergerWindow);
		this.setPreferredSize(setDim);
		this.setMinimumSize(setDim);
		this.setMaximumSize(setDim);
		logMergerWindow.getConfigGetter();
		myHighlightPainter = new UnOrganizedHighlighter(Color.decode(logMergerWindow.getConfigGetter().getHighlightHexColor()));
		setToolTipText(ADD_FILE_TOOL_TIP);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		MainWindowHolder windowHolder = getLogMergerWindow().getWindowHolder();
		
		if(!windowHolder.getTxtHolder().getFileNameInputTextField().getText().isEmpty()) {
			SearchButton selectFileBtn = windowHolder.getBtnHolder().getSearchButton();
			JTextArea unOrganizedText = windowHolder.getTxtHolder().getUnOrderedText();
			JTextField fileNameInputTextField = windowHolder.getTxtHolder().getFileNameInputTextField();
			JScrollPane unOrganizedScrollPane = windowHolder.getTxtHolder().getUnOrderedScrollPane();

			SwingWorker<String, Void> worker = new SwingWorker<String, Void>() {
				ProcessLogo glassPaneDrawingThread = new ProcessLogo(windowHolder, CenteredPointType.UN_ORDERED_TEXT_AREA);


				public String doInBackground() throws IOException {
					selectFileBtn.setEnabled(false);

					File file = new File(fileNameInputTextField.getText());

					glassPaneDrawingThread.startProcessing();

					String result = "";

					try{
						result =  unOrganizedText.getText();
						result += IOUtils.toString(new FileInputStream(file), StandardCharsets.UTF_8);
						result = result.strip();

					} catch(IOException exx){
						logger.error("Unable to read file input:{} and add to text area", file, exx);
					}

					return result;
				}

				public void done() {
					try {
						unOrganizedText.setText(get());
						unOrganizedText.setCaretPosition(0);
						
						removeHighlights(unOrganizedText);
						highlight(unOrganizedText, windowHolder.getTxtHolder().getRegexPatternTextField().getText());

						unOrganizedScrollPane.getHorizontalScrollBar().setValue(0);

						glassPaneDrawingThread.stopProcessing();

						selectFileBtn.setEnabled(true);
						unOrganizedText.setEnabled(true);
					} catch (Exception ex) {
						ex.printStackTrace();
					}
				}
			};
			worker.execute();
		}
	
	}


	public static void highlight(JTextComponent textComp, String pattern) throws Exception {
		removeHighlights(textComp);
		
		String regex = pattern.replace("dd", "\\d\\d")
		  .replace("yyyy", "\\d\\d\\d\\d")
		  .replace("MM", "\\d\\d")
		  .replace("HH", "\\d\\d")
		  .replace("mm", "\\d\\d")
		  .replace("ss", "\\d\\d")
		  .replace("SSS", "\\d\\d\\d")
		  .replace(":", "\\:")
		  .replace(".","\\.");

		Highlighter hilite = textComp.getHighlighter();
		Document doc = textComp.getDocument();
		String text = doc.getText(0, doc.getLength());
		
		final Pattern p = Pattern.compile(regex);
		final Matcher m = p.matcher(text);
		int pos = 0;

		while (m.find()) {
			pos = m.start();
		    hilite.addHighlight(pos, pos + pattern.length(), myHighlightPainter);
			pos += pattern.length();
		}
	}

	public static void removeHighlights(JTextComponent textComp) {
		Highlighter hilite = textComp.getHighlighter();
		Highlighter.Highlight[] hilites = hilite.getHighlights();

		for (int i = 0; i < hilites.length; i++) {
			if (hilites[i].getPainter() instanceof UnOrganizedHighlighter) {
				hilite.removeHighlight(hilites[i]);
			}
		}
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		int width = getWidth();
		int height = getHeight();
		
		//Set Background
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, width, height);
		
		g.setColor(Color.BLACK);
		
		// Vertical Line
		g.drawLine(width/2-1, 5, width/2-1, height - 6);
		g.drawLine(width/2, 5, width/2, height - 6);
		g.drawLine(width/2+1, 5, width/2+1, height - 6);
		
		// Horizontal Line
		g.drawLine(5, height/2-1, width - 6, height/2-1);
		g.drawLine(5, height/2, width - 6, height/2);
		g.drawLine(5, height/2+1, width - 6, height/2+1);
	}
}
