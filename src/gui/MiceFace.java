package gui;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import mouses.Mouse;
import used.Random;

public class MiceFace extends JPanel{
	
	private static final long serialVersionUID = 1L;
	public final static String COOPERATIVE_FACE_LOCATION="/images/face/coop/";
	public final static String SELFISH_FACE_LOCATION="/images/face/igo/";
	private Mouse mouse;
	private ImageIcon[] selfish = new ImageIcon[3];
	private ImageIcon[] Cooperative = new ImageIcon[3];
	private Image selfishPics;
	private Image CooperativePics;
	
	public MiceFace() {
		super();
		
		selfish[0] = new ImageIcon(getClass().getResource(MiceFace.SELFISH_FACE_LOCATION+"1.png"));
		selfish[1] = new ImageIcon(getClass().getResource(MiceFace.SELFISH_FACE_LOCATION+"2.png"));
		selfish[2] = new ImageIcon(getClass().getResource(MiceFace.SELFISH_FACE_LOCATION+"3.png"));
		
		Cooperative[0] = new ImageIcon(getClass().getResource(MiceFace.COOPERATIVE_FACE_LOCATION+"1.png"));
		Cooperative[1] = new ImageIcon(getClass().getResource(MiceFace.COOPERATIVE_FACE_LOCATION+"2.png"));
		Cooperative[2] = new ImageIcon(getClass().getResource(MiceFace.COOPERATIVE_FACE_LOCATION+"3.png"));
	}
	 
	public void paintComponent(Graphics g) {
		if(mouse != null)
			if(mouse.getBehavior().type().equals("Cooperative")) {
				int i = Random.randomInt(0, 2);
				CooperativePics = Cooperative[i].getImage();
				g.drawImage(CooperativePics, this.getPreferredSize().width/10, this.getPreferredSize().height/90, 100, 80, null);
			}
			else {
				int i = Random.randomInt(0, 2);
				selfishPics = selfish[i].getImage();
				g.drawImage(selfishPics, this.getPreferredSize().width/10, this.getPreferredSize().height/90, 100, 80, null);
			}
	}

	public Mouse getMouse() {
		return mouse;
	}

	public void setMouse(Mouse mouse) {
		this.mouse = mouse;
	}
}
