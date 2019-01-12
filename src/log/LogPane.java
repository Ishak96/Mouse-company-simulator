package log;

import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;

public class LogPane extends JPanel {

	private static final long serialVersionUID = 1L;
	
	private JTextArea logTextArea;
	private JScrollPane scrPane;
	private LogWriter logWr;
	
	public LogPane() {
		logWr = LogWriter.getInstance();
		init();
	}
	
	public void init() {
		logTextArea = new JTextArea();
		logTextArea.setEditable(false);
		scrPane = new JScrollPane(logTextArea,ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
											  ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrPane.setPreferredSize(new Dimension(370,213));
		add(scrPane);
	}
	
	public void updateText() {
		logTextArea.setText(logWr.getInfos());
		int length = logTextArea.getText().length();
		logTextArea.setCaretPosition(length);
	}
	
	public void appendText(String text) {
		logTextArea.append(text);
		int length = logTextArea.getText().length();
		logTextArea.setCaretPosition(length);
	}
		
}
