package grid;

import javax.swing.ImageIcon;

/**
 * this class specified a Wall type
 * @author ishak
 */

public class Wall extends Ground{

	/***********		construct		***********/
	
	public Wall(int abscisse, int ordonne) {
		super(abscisse, ordonne);
		
		ImageIcon img = new ImageIcon(getClass().getResource("/images/terrain/Wall.png"));
		image = img.getImage();
	}
	
	/***********		methodes		***********/
				//getters
	
				//setters

				//others
	@Override
	public boolean isGrass() {
		return false;
	}
	@Override
	public boolean isWall() {
		return true;
	}
	@Override
	public boolean isFood() {
		return false;
	}
	@Override
	public boolean isObstacle() {
		return false;
	}
	@Override
	public int getQuantity() {
		return 0;
	}

	@Override
	public boolean setQuantity() {
		return false;
	}

	@Override
	public void setQuantity(int quantity) {
		
	}
}
