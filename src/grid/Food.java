package grid;

import java.awt.Image;

import javax.swing.ImageIcon;

import used.GenerateGameMode;
import used.Point;

/**
 * This class is contained in each Box. It contains an amount of food which is an integer and a time
 * @author ishak
 */

public class Food extends Ground {
	
	/**********		attributs		**********/
	private FoodType type;
	private int quantity;
	private int time;
	private int numberOfMouseEaten;
	/**********		construct		**********/
	/**
	 * creat a new Food with a amount and a time associate to the simulation turn
	 * @param abscisse
	 * @param ordonne
	 * @param simulationTurn
	 */
	public Food(int abscisse, int ordonne, String simulationTurn) {
		super(abscisse, ordonne);
		
		type= FoodType.randomType(simulationTurn);
		ImageIcon img = new ImageIcon(getClass().getResource("/images/nourriture/"+type+".png"));
		image = img.getImage();
		
		quantity=GenerateGameMode.foodQuantity(simulationTurn);
		time=GenerateGameMode.foodTime(simulationTurn);
		numberOfMouseEaten=0;
	}
	
	/**********		methodes		**********/
				//getters
	public int getNumberOfMouseEaten() {
		return numberOfMouseEaten;
	}
	
	public int getTime() {
		return time;
	}
	
	public int getQuantity() {
		return quantity;
	}
	
	public FoodType getType() {
		return type;
	}
	
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
	public void setNumberOfMouseEaten(int numberOfMouseEaten) {
		this.numberOfMouseEaten=numberOfMouseEaten;
	}
	
	public void setTime(int time) {
		this.time=time;
	}
	
	public void setQuantity(int quantity) {
		this.quantity=quantity;
	}
	
	public void setFoodType(FoodType type) {
		this.type=type;
	}
	
	public Point getPosition() {
		return new Point(getAbscisse(),getOrdonne());
	}
				//others
	public void mouseEatHere() {
		numberOfMouseEaten++;
	}
	
	/**
	 * mouse consume this Food source.
	 */
	
	public void foodToConsumeTime() {
		if(numberOfMouseEaten<2) 
			time-=1;
		else if(numberOfMouseEaten<4) 
			time-=2;
		else if(numberOfMouseEaten<6) 
			time-=3;
		else
			time-=4;
	}
	
	public boolean isempty() {
		return (quantity<=0);
	}
	
	public boolean obsoleteFood() {
		return (time<=0);
	}
	
	public void addQuantity() {
		quantity++;
	}
	
	public void addQuantity(int quantity) {
		this.quantity+=quantity;
	}
	
	/**
	 * check if a mice can consum this Food source.
	 * @return true if we can consum it else return false
	 */
	public boolean consum() {
		boolean hasConsumed=true;
		if(!isempty()) {
			quantity--;
		}else
			hasConsumed=false;
		
		return hasConsumed;
	}
	
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
		return true;
	}
	@Override
	public boolean isObstacle() {
		return false;
	}

	@Override
	public boolean setQuantity() {
		mouseEatHere();
		return consum();
	}
	
	public String toString() {
		return "Food: "+quantity+" + "+time;
	}
}
