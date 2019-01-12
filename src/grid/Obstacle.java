package grid;

import javax.swing.ImageIcon;

import engine.GridParameters;

/**
 * 
 * @author ishak
 *
 */

public class Obstacle extends Ground{

	/**********		attributs		**********/
	private ObstacleType type;
	/**********		construct		**********/
	public Obstacle(int abscisse, int ordonne) {
		super(abscisse, ordonne);
		
		type=ObstacleType.randomType();
		ImageIcon img = new ImageIcon(getClass().getResource("/images/"+GridParameters.getInstance().getGround()+"Obstacle/"+type+".png"));
		image = img.getImage();
	}
	public Obstacle(int abscisse, int ordonne,int index) {
		super(abscisse, ordonne);
		index=index+1;
		ImageIcon img = new ImageIcon(getClass().getResource("/images/"+GridParameters.getInstance().getGround()+"Obstacle/Obstacle"+index+".png"));
		image = img.getImage();
	}
	/**********		methodes		**********/
				//getters
	public int getQuantity() {
		return 0;
	}
				//setters
	
				//others
	@Override
	public boolean isGrass() {
		return false;
	}
	@Override
	public boolean isWall() {
		return false;
	}
	@Override
	public boolean isFood() {
		return false;
	}
	@Override
	public boolean isObstacle() {
		return true;
	}
	@Override
	public boolean setQuantity() {
		return false;
	}
	@Override
	public void setQuantity(int quantity) {
		
	}
}
