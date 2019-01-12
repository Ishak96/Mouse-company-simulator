package grid;

import java.awt.Image;

import used.Point;

/**
 * This class is contained in each Box.
 * @author ishak
 */

public abstract class Ground {
		
	/**********		attributs		**********/
	public Point position;
	public Image image;
	/**********		construct		**********/
	
	public Ground(int abscisse, int ordonne) {
		position = new Point(abscisse,ordonne);
	}
	/**********		methods			**********/
				//getters
	public int getAbscisse() {
		return position.getAbscisse();
	}
	public int getOrdonne() {
		return position.getOrdonne();
	}
	public Image getImage() {
		return image;
	}
				//setters
	public void setAbscisse(int abscisse) {
		position.setAbscisse(abscisse);
	}
	public void setOrdonne(int ordonne) {
		position.setOrdonne(ordonne);
	}
				//others
	public abstract boolean isGrass();
	public abstract boolean isWall();
	public abstract boolean isFood();
	public abstract boolean isObstacle();
	public abstract int getQuantity();
	public abstract boolean setQuantity();
	public abstract void setQuantity(int quantity);
}
