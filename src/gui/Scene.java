package gui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

import engine.GridParameters;
import grid.Box;
import grid.Food;
import grid.Grid;
import mouses.Mouse;
import used.Direction;
import used.Point;

/**
 * This class is used to display the grid.
 * @author ishal,anis
 *
 */
@SuppressWarnings("serial")
public class Scene extends JPanel {

	/**********		attributs		**********/
	private Grid grid;
	private ImageIcon pics;
	private Mouse mouse;
	private Point selectedBox = null;
	private Point selectedMice = null;
	private String modeScene = "global";
	/**********		construct		**********/
	public Scene() {
		super();
		setBorder(BorderFactory.createEtchedBorder());
	}
	/**********		methodes		**********/
				//getters
	public Point getSelectedBox() {
		return selectedBox;
	}
	
	public Point getSelectedMice() {
		return selectedMice;
	}
	
	public Grid getGrid() {
		return grid;
	}
				//setters
	public void setMouseSelected(Mouse m) {
		this.mouse=m;
	}
	public void setSelectedBox(Point selectedBox) {
		this.selectedBox=selectedBox;
	}
	
	public void setModeScene(String mode) {
		this.modeScene = mode;
	}
	
	public void setSelectedMice(Point selectedMice) {
		this.selectedMice=selectedMice;
	}
	
	public void setGrid(Grid grid) {
		this.grid=grid;
	}
	
	@Override
	public void paintComponent(Graphics g) {
		
		super.paintComponent(g);
		if (modeScene.equals("global")) {
			paintGlobalGrid(g);
		} else if (modeScene.equals("vision")) {
			paintVisionGrid(g);
		} else if(modeScene.equals("miceAct")){
			paintMiceAct(g);
		}
	
	}
	
	/**
	 * Paints the grid in communication mode.
	 * @param g The graphics.
	 */
	private void paintMiceAct(Graphics g) {
		Graphics g2 = (Graphics2D) g;
		//the dimension of the grid
		int dimension = grid.getDimension();
		Box box;
		for(int y=0;y<dimension;y++) {
			for(int x=0;x<dimension;x++) {
				box = grid.getBoxAt(x, y);
				if(box.getGroundType().isGrass()){
					drawGround(new Point(x,y),g2,box);
				}else if(box.getGroundType().isWall()) {
					drawGround(new Point(x,y),g2,box);
				}
				else if(!box.getIsFree()) {
					drawObstacle(new Point(x,y),g2,box);
				}
			}
		}
		
		for(int i=0; i<grid.getFood().size(); i++) {
			Food f = grid.getFood().get(i);
			drawFood(f, g);
		}
		
		for (int i=0; i<grid.getMouses().size(); i++) {
			Mouse m = grid.getMouse(i);
			if(m.getAge()<15)
				drawNewMouse(m, g);
			
			if(m.meet()) {
				drawMeetingMouse(m, g);
			}
			
			else if (m.getIsTalking() && !m.isDead())
				drawTalkingMouse(m, g);
				
			else
				drawMouse(m, g);
				
		}
		
		if(selectedBox != null) {
			highLightBox(selectedBox, g);
		}
		
		if(selectedMice != null) {
			highLightMice(selectedMice, g);
		}
					
	}
	
	/**
	 * Paints the grid in vision mode.
	 * @param g The graphics.
	 */
	public void paintVisionGrid(Graphics g) {
		Graphics g2 = (Graphics2D) g;
		int xMouse = mouse.getPosition().getAbscisse();
		int yMouse = mouse.getPosition().getOrdonne();
		int horizon = mouse.getVision().getHorizon();
		Box box;
		int dimension = grid.getDimension();
		
		if(mouse.getLife()==0) {
			paintGlobalGrid(g);
		}
		else {
		
			for( int i=0; i < dimension ; i++ ) {
				for( int j=0; j < dimension; j++) {
				
					box = grid.getBoxAt(i, j);
					if (i>=xMouse-horizon && i <= xMouse+horizon && j>=yMouse-horizon && j <= yMouse+horizon) {
						if(box.getGroundType().isGrass()){
							drawGround(new Point(i,j),g2,box);
					}
					else {
						if(box.getGroundType().isWall()) {
							drawGround(new Point(i,j),g2,box);
						}
						else {
							if(!box.getIsFree()) {
								drawObstacle(new Point(i,j),g2,box);
							}
						}
					}
					
					for(int k=0; k<grid.getFood().size(); k++) {
						Food f = grid.getFood().get(k);	
						if(f.getPosition().equals(new Point(i,j)) ) {
						
							drawFood(f, g);
						}
						
					}
					
					for (int k=0; k<grid.getMouses().size(); k++) {
						Mouse m = grid.getMouse(k);
						if(m.getPosition().equals(new Point(i,j))) {
							if(m.getAge()<15)
								drawNewMouse(m, g);							
							if(m.meet()) {
								drawMeetingMouse(m, g);
							}
							else if (m.getIsTalking() && !m.isDead())
								drawTalkingMouse(m, g);
								
							else
								drawMouse(m, g);
						}
								
					}
				}
				for(int k=0; k<grid.getFood().size(); k++) {
					Food f = grid.getFood().get(k);
					if(	mouse.getMemory().getFoodLocationStock().contains(f.getPosition())) {
					
						drawFood(f, g);
					}
					
				}
				for(int k=0; k<grid.getFood().size(); k++) {
					Food f = grid.getFood().get(k);	
					if(	mouse.getMemory().getFoodLocationGivenByOthers().contains(f.getPosition())) {
						drawFood(f, g);
					}
				}
				if(	mouse.getMemory().getFoodLocationStock().contains(new Point(i,j))) {	
					drawGround(new Point(i,j), g2, box);
				}
			}
		}
		
		if(mouse.getAge()<15)
			drawNewMouse(mouse, g);
		
		if(mouse.meet()) {
			drawMeetingMouse(mouse, g);
		}
		
		else if (mouse.getIsTalking() && !mouse.isDead())
			drawTalkingMouse(mouse, g);
			
		else
			drawMouse(mouse, g);
		
		if(!(mouse.getMemory().getAllFoodLocation().size() == 0
				&& mouse.getMemory().getFoodLocationGivenByOthers().size() == 0
					&& mouse.getMemory().getFoodLocationStock().size() == 0 ))
				drawPathFinding(mouse,g);
			drawMouse(mouse, g);
		}
		
		if(selectedMice != null) {
			highLightMice(selectedMice, g);
		}
		
		if(mouse.getLife()==0 || mouse.getAge() == 250) {
			paintGlobalGrid(g);
		}
		
	}
	
	/**
	 * Paints mice with Path-Finding mode.
	 * @param g The graphics.
	 */
	public void drawPathFinding(Mouse mouse , Graphics g) {
		
		int xMouse = mouse.getPosition().getAbscisse();
		int yMouse = mouse.getPosition().getOrdonne();
		ImageIcon imageCible = new ImageIcon(getClass().getResource("/images/cible.png"));
		Image cible = imageCible.getImage();
		
		if(mouse.getLife()<30) {
			int xFood = mouse.getMemory().getFoodToEat().getAbscisse();
			int yFood = mouse.getMemory().getFoodToEat().getOrdonne();
			if (xMouse >= xFood && yMouse <= yFood) {
				g.setColor(Color.red);
				g.fillRect(xFood*29, yMouse*29 ,(xMouse-xFood)*29,4);
				g.setColor(Color.red);	
				g.fillRect(xFood*29, yMouse*29 ,4,(yFood-yMouse)*29);
			
			}
			else {
				if (xMouse <= xFood && yMouse >= yFood) {
					g.setColor(Color.red);
					g.fillRect(xMouse*29, yMouse*29 ,(xFood-xMouse)*29,4);
					g.setColor(Color.red);	
					g.fillRect(xFood*29, yFood*29 ,4,(yMouse-yFood)*29+4);
				}
				else {
					if(xMouse >= xFood && yMouse >= yFood) {
						g.setColor(Color.red);
						g.fillRect(xMouse*29, yFood*29 ,4,(yMouse-yFood)*29);
						g.setColor(Color.red);	
						g.fillRect(xFood*29, yFood*29 ,(xMouse-xFood)*29,4);
					}
					else {
						g.setColor(Color.red);
						g.fillRect(xMouse*29, yMouse*29 ,4,(yFood-yMouse)*29);
						g.setColor(Color.red);	
						g.fillRect(xMouse*29, yFood*29 ,(xFood-xMouse)*29+4,4);
					}
				}
			}
			g.drawImage(cible, xFood*28,yFood*28,null);
			if(grid.getMouseAt(new Point(xFood, yFood)) != null) {
				drawMouse(grid.getMouseAt(new Point(xFood, yFood)), g);
			}
		}	
	}
	
	/**
	 * Paints the grid in normal mode.
	 * @param g The graphics.
	 */
	public void paintGlobalGrid(Graphics g) {

		Graphics g2 = (Graphics2D) g;
		//the dimension of the grid
		int dimension = grid.getDimension();
		Box box;
		for(int y=0;y<dimension;y++) {
			for(int x=0;x<dimension;x++) {
				box = grid.getBoxAt(x, y);
				if(box.getGroundType().isGrass()){
					drawGround(new Point(x,y),g2,box);
				}else if(box.getGroundType().isWall()) {
					drawGround(new Point(x,y),g2,box);
				}
				else if(!box.getIsFree()) {
					drawObstacle(new Point(x,y),g2,box);
				}
			}
		}
		
		for(int i=0; i<grid.getFood().size(); i++) {
			Food f = grid.getFood().get(i);
			drawFood(f, g);
		}
		
		for (int i=0; i<grid.getMouses().size(); i++) {
			Mouse m = grid.getMouse(i);
				drawMouse(m, g);
		}
		
		if(selectedBox != null) {
			highLightBox(selectedBox, g);
		}
		
		if(selectedMice != null) {
			highLightMice(selectedMice, g);
		}
	}
	
	/**
	 * Paints the mice in normal mode.
	 * @param g The graphics.
	 * @param mouse
	 */
	public void drawMouse(Mouse mouse, Graphics g) {
		Image img = mouse.getBehavior().getPicture();
		Point pos = mouse.getPosition();
	
		g.drawImage(img,pos.getAbscisse()*28,pos.getOrdonne()*28, null);
	}
	
	/**
	 * paints baby Mice.
	 * @param mouse
	 * @param g graphics
	 */
	
	public void drawNewMouse(Mouse mouse, Graphics g) {
		g.setColor(Color.RED);
		Image img = mouse.getBehavior().getPicture();
		Point pos = mouse.getPosition();
	
		g.drawImage(img,pos.getAbscisse()*28,pos.getOrdonne()*28, null);
		
		if(mouse.getIsEating())
			g.drawRect(pos.getAbscisse()*28, pos.getOrdonne()*28, 20, 20);
		
		else
			g.drawRect(pos.getAbscisse()*28, pos.getOrdonne()*28, 15, 15);
	}
	
	/**
	 * paints talking mice.
	 * @param mouse
	 * @param g
	 */
	public void drawTalkingMouse(Mouse mouse, Graphics g) {
		Image img = mouse.getBehavior().getPicture();
		Point pos = mouse.getPosition();
		Direction dir = mouse.getLastDirection();
		String chatS="";
		String resNih="";
		ImageIcon imgchat;
		
		if(dir == Direction.Up || dir == Direction.Down)
			chatS = "Hor";
		
		if(mouse.isNihilist()) {
			resNih="refuse";
			imgchat = new ImageIcon(getClass().getResource("/images/chat/chat"+chatS+resNih+".png"));
		}
		else {
			imgchat = new ImageIcon(getClass().getResource("/images/chat/chat"+chatS+resNih+mouse.getTalkTime()+".png"));
		}
		
		Image chat = imgchat.getImage();
		
		g.drawImage(img,pos.getAbscisse()*28,pos.getOrdonne()*28, null);
		
		switch(dir) {
			case  Down:
				g.drawImage(chat, (pos.getAbscisse()+1)*28, (pos.getOrdonne())*28, null);
				break;
				
			case  Up:
				g.drawImage(chat, (pos.getAbscisse()+1)*28, (pos.getOrdonne())*28, null);
				break;
			case  Left:
				g.drawImage(chat, (pos.getAbscisse())*28, (pos.getOrdonne()-1)*28, null);
				break;
			case  Right:
				g.drawImage(chat, (pos.getAbscisse())*28, (pos.getOrdonne()-1)*28, null);
				break;
		}
	}
	
	/**
	 * paints meeting mice
	 * @param mouse
	 * @param g
	 */
	
	public void drawMeetingMouse(Mouse mouse, Graphics g) {
		Image img = mouse.getBehavior().getPicture();
		Point pos = mouse.getPosition();
		Direction dir = mouse.getLastDirection();
		String HeartS="Ver";
		
		if(dir == Direction.Up || dir == Direction.Down)
			HeartS = "";
		
		ImageIcon imgchat = new ImageIcon(getClass().getResource("/images/chat/heart"+HeartS+".png"));
		Image chat = imgchat.getImage();
		
		g.drawImage(img,pos.getAbscisse()*28,pos.getOrdonne()*28, null);
		
		int i = mouse.getTalkTime();
		
		switch(dir) {
			case  Down:
				g.drawImage(chat, (pos.getAbscisse()+i)*28, (pos.getOrdonne())*28, null);
				break;
				
			case  Up:
				g.drawImage(chat, (pos.getAbscisse()+i)*28, (pos.getOrdonne())*28, null);
				break;
			case  Left:
				g.drawImage(chat, (pos.getAbscisse())*28, (pos.getOrdonne()-i)*28, null);
				break;
			case  Right:
				g.drawImage(chat, (pos.getAbscisse())*28, (pos.getOrdonne()-i)*28, null);
				break;
		}
	}
	
	/**
	 * paitns food source
	 * @param f
	 * @param g
	 */
	
	public void drawFood(Food f,Graphics g) {
		pics = new ImageIcon(getClass().getResource("/images/terrain/"+GridParameters.getInstance().getGround()+".png"));
		Image t = pics.getImage();

		g.drawImage(t,f.getAbscisse()*28,f.getOrdonne()*28,null);
		g.drawImage(f.getImage(),f.getAbscisse()*28,f.getOrdonne()*28,null);
		
	}
	
	/**
	 * paints obstacles
	 * @param p
	 * @param g
	 * @param box
	 */
	
	public void drawObstacle(Point p,Graphics g,Box box) {
		pics = new ImageIcon(getClass().getResource("/images/terrain/"+GridParameters.getInstance().getGround()+".png"));
		Image t = pics.getImage();
		g.drawImage(t,p.getAbscisse()*28,p.getOrdonne()*28,null);
		g.drawImage(box.getGroundType().getImage(),p.getAbscisse()*28,p.getOrdonne()*28,null);
	}
	
	/**
	 * paints ground.
	 * @param p
	 * @param g
	 * @param box
	 */
	
	public void drawGround(Point p,Graphics g,Box box) {
		g.drawImage(box.getGroundType().getImage(),p.getAbscisse()*28,p.getOrdonne()*28,null);
	}
	
	/**
	 * Highlights a Box.
	 * @param p
	 * @param g
	 */
	public void highLightBox(Point p, Graphics g) {
		
		Graphics2D g2 = (Graphics2D) g;
		
		g2.setColor(Color.black);
		
		g2.setStroke(new BasicStroke(1));
		g2.drawRect(p.getAbscisse() * 28, p.getOrdonne() * 28, 28,
			28);
	}
	
	/**
	 * Highlights a mouse.
	 * @param p
	 * @param g
	 */
	
	public void highLightMice(Point p, Graphics g) {
		
		Graphics2D g2 = (Graphics2D) g;
		
		g2.setColor(Color.BLUE);
		
		g2.setStroke(new BasicStroke(1));
		g2.drawRect(p.getAbscisse() * 28, p.getOrdonne() * 28, 28,
			28);
	}
}
