package log;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JProgressBar;

@SuppressWarnings("serial")
public class StoryFrame extends JFrame implements Runnable {
	
	private StoryViewer storyViewer;
	private ArrayList<MouseEvent> events;
	
	private static final int EVENT_TIME = 2000;
	private boolean stop = true;
	
	private JButton playStoryButton;
	private JProgressBar progressBar;
	
	public StoryFrame(String title,ArrayList<MouseEvent> events){
		super(title);
		this.events=events;
		setResizable(false);
		getContentPane().setBackground(Color.WHITE);
		setSize(1000,635);
		setLocationRelativeTo(null);
		getContentPane().setLayout(null);
		setVisible(true);
		initComponents();
		launchGUI();
	}
	
	private void launchGUI() {
		stop = false;		
		Thread chronoThread = new Thread(this);
		chronoThread.start();
		playStoryButton.setEnabled(false);
	}
	
	void initComponents() {
		
		storyViewer = new StoryViewer();
		storyViewer.setBounds(5,5,985,560);
		getContentPane().add(storyViewer);
		
		playStoryButton = new JButton(" Restart ");
		playStoryButton.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 15));
		playStoryButton.setBounds(5,571,100,24);
		playStoryButton.setFocusable(false);
		getContentPane().add(playStoryButton);
		playStoryButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				launchGUI();
			}			
		});
		
		progressBar = new JProgressBar(0,events.size()-1);
		progressBar.setForeground(Color.GREEN);
		progressBar.setBounds(110,571,880,24);
		getContentPane().add(progressBar);
				
	}
	
	public void displayStories() throws InterruptedException {
		for(int i=0;i<events.size();i++) {
			MouseEvent event = events.get(i);
			storyViewer.startEvent(event.getImageName(),event.getDescriptionText());
			progressBar.setValue(i);
			if(event.getImageName().equals("com")) {
				Thread.sleep(3*EVENT_TIME);
			}else {
				Thread.sleep(EVENT_TIME);
			}
			if(event.getImageName().equals("dead")) {
				stop = true;
				progressBar.setValue(0);
				playStoryButton.setEnabled(true);
			}
			storyViewer.endEvent();
		}
	}
	
	@Override
	public void run() {
		if(!stop) {
			storyViewer.startThread();
			try {
				displayStories();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}
