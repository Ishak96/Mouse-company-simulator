package log;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import gui.SelectPlayModeGui;

@SuppressWarnings("serial")
/**
 * This class allows to visualize the story of a mouse in JPanel.
 * @author Rassem
 */
public class StoryViewer extends JPanel implements Runnable {
	
	private int imgIndex;
	private int x,y;
	private int backgroundX1,backgroundX2;
	private int iconEventXPos,iconEventYPos;
	private int walkComXPos;
	private int communicationTime;
	private String eventText;
	private String imgName;	
	private ImageIcon background;        
	private boolean eventPerformed;
	private boolean stop;
	private static final int THREAD_TIME = 100;
	
	
	public StoryViewer() {
		eventPerformed=false;
		stop = true;
		selectBckgroundImage();
		initPositions();
	}
	
	public void startThread() {
		stop = false;		
		Thread chronoThread = new Thread(this);
		chronoThread.start();
	}
	
	public void selectBckgroundImage() {
		String imagePath = "/images/log/background"+SelectPlayModeGui.getGround()+".png";
		background = new ImageIcon(getClass().getResource(imagePath));	
	}
	
	private void initPositions() {
		x = 100;
		y = 270;
		iconEventXPos=x+300;
		iconEventYPos=500;
		walkComXPos = x+800;
		communicationTime=0;
		backgroundX1=0;
		backgroundX2=background.getIconWidth();
		imgIndex = 1;
	}
	
	private void updatePositions() {
		imgIndex++;
		if(imgIndex==8) imgIndex=1;
		walkComXPos-=25;
		if(walkComXPos < x+100 && communicationTime<15) {
			walkComXPos+=25;
			communicationTime++;
		}
		iconEventXPos-=27;
		iconEventYPos-=14;
		backgroundX1-=5;
		backgroundX2-=5;
		if(backgroundX2==0) {
			backgroundX2=background.getIconWidth();
			backgroundX1=0;
		}
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(background.getImage(),backgroundX1,0,null);
		g.drawImage(background.getImage(),backgroundX2,0,null);
		ImageIcon mouseTV = new ImageIcon(getClass().getResource("/images/log/mousetv.png"));
		g.drawImage(mouseTV.getImage(),880,10,null);
		if(eventPerformed) {
			ImageIcon imgIconWalk = new ImageIcon(getClass().getResource("/images/log/walk"+imgIndex+".png"));
			if(!imgName.equals("walk") && !imgName.equals("none")){
				if(imgName.equals("dead")) {
					ImageIcon imgIconEvent = new ImageIcon(getClass().getResource("/images/log/"+imgName+".png"));
					g.drawImage(imgIconEvent.getImage(),0,0,null);
					g.drawImage(imgIconWalk.getImage(),x, y,null);
					stop=true;
				}else if(imgName.equals("com")){
					ImageIcon imgIconWalkGauche = new ImageIcon(getClass().getResource("/images/log/walkGauche"+imgIndex+".png"));
					g.drawImage(imgIconWalkGauche.getImage(),walkComXPos, y,null);
				}else {
					ImageIcon imgIconEvent = new ImageIcon(getClass().getResource("/images/log/"+imgName+".png"));
					g.drawImage(imgIconEvent.getImage(),iconEventXPos,iconEventYPos,null);	
				}	
			}
			g.drawImage(imgIconWalk.getImage(),x, y,null);
			g.setColor(Color.darkGray);
			g.setFont(new Font("Tahoma",Font.ITALIC, 20));
			g.drawString(eventText,40,100);	
		}
	}
	
	public void startEvent(String imgName,String eventText) {
		this.imgName=imgName;
		this.eventText=eventText;
		this.eventPerformed=true;	
		
	}
	
	public void endEvent() {
		eventPerformed=false;
		iconEventXPos=x+300;
		iconEventYPos=500;
		walkComXPos = x+800;
		communicationTime=0;
	}
		
	public void run() {
		while(!stop) {
			updatePositions();
			repaint();
			try {
				Thread.sleep(THREAD_TIME);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
