package grid;

import javax.swing.ImageIcon;

/**
 * 
 * @author ishak
 *
 */

public class Grass extends Ground{
	
	/**********		construct		**********/
	/**
	 * creat a new Grass.
	 * @param abscisse
	 * @param ordonne
	 * @param ground
	 */
	public Grass(int abscisse, int ordonne, String ground) {
		super(abscisse, ordonne);
		ImageIcon img = new ImageIcon(getClass().getResource("/images/terrain/"+ground+".png"));
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
		return true;
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
		return false;
	}

	@Override
	public boolean setQuantity() {
		return false;
	}

	@Override
	public void setQuantity(int quantity) {
		
	}
}
