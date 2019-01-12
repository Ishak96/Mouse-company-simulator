package grid;

import java.util.ArrayList;
import java.util.Iterator;

import engine.GridParameters;
import gui.SelectPlayModeGui;
import mouses.Mouse;
import used.Point;

/**
 * This class contains the grid where the simulation will operate.
 * @author ishak
 */

public class Grid {
	
	public int dimension;
	
	/**********		attributes		*************/
	private Box[][] boxs;
	private ArrayList<Mouse> mouses=new ArrayList<Mouse>();;
	private ArrayList<Obstacle> obstacle;
	private ArrayList<Food> food;
	private GridParameters parameters;
	/**********		Construct		************/
	/**
	 * creat a new Grid.
	 */
	public Grid() {
		dimension = SelectPlayModeGui.getDimension();
		boxs = new Box[dimension][dimension];
		obstacle=new ArrayList<Obstacle>();
		food=new ArrayList<Food>();
	}

	/**********		methodes		**********/
	
				//getters
	/**
	 * return the Box at the position abscisse,ordonne
	 * @param abscisse
	 * @param ordonne
	 * @return Box
	 */
	public Box getBoxAt(int abscisse,int ordonne) {
		return boxs[abscisse][ordonne];
	}
	
	/**
	 * set the Box.
	 * @param abscisse
	 * @param ordonne
	 * @param box
	 */
	public void setBoxAt(int abscisse ,int ordonne,Box box) {
		boxs[abscisse][ordonne] = box;
		
	}
	/**
	 * set the accessibility of the Box
	 * @param x
	 * @param y
	 * @param bl
	 */
	public void setBoxAtFree(int x, int  y, boolean bl) {
		boxs[x][y].setIsFree(bl);
	}
	
	public void setBox(int x , int y ,Ground gr ) {
		boxs[x][y].setGroundType(gr);
	}
	
	public Box[][] getBoxs(){
		return boxs;
	}
	
	public void setBoxs(Box[][] boxs){
		this.boxs =boxs;
	}
	
	public int getDimension() {
		return dimension;
	}
	
	public ArrayList<Mouse> getMouses(){
		return mouses;
	}
	
	public ArrayList<Obstacle> getObstacle(){
		return obstacle;
	}
	
	public ArrayList<Food> getFood(){
		return food;
	}
	
	/**
	 * return food at the position abscisse,ordonne
	 * @param abscisse
	 * @param ordonne
	 * @return Food
	 */
	public Food getFoodAt(int abscisse, int ordonne) {
		Food foodPos=null;
		Point pos = new Point(abscisse, ordonne);
		for(Food f : food) {
			if(f.getPosition().equals(pos))
				foodPos=f;
		}
		return foodPos;
	}
	
	/**
	 * return idexed mice
	 * @param index
	 * @return Mouse
	 */
	public Mouse getMouse(int index) {
		return mouses.get(index);
	}
	
	/**
	 * return mice Poisition in the Grid
	 * @param m
	 * @return Point
	 */
	public Point getMoisePosition(Mouse m) {
		return m.getPosition();
	}
	
	/**
	 * return mouse at the position p
	 * @param p
	 * @return Mouse
	 */
	public Mouse getMouseAt(Point p) {
		Mouse m = null;
		for(Iterator<Mouse>it=mouses.iterator(); it.hasNext();) {
			Mouse courantMouse = it.next();
			if(courantMouse.getPosition().equals(p)) {
				m = courantMouse;
			}
		}
		
		return m;
	}
	
	/**
	 * gives us the Grid parameters
	 * @return GridParameters
	 */
	public GridParameters getGridParameters() {
		return parameters;
	}
	
				//setters
	public void setGridParameters(GridParameters parameters) {
		this.parameters=parameters;
	}

				//others
	/**
	 * add a new Mouse to the grid
	 * @param m
	 */
	public void addMouse(Mouse m) {
		if(!mouses.contains(m))
			mouses.add(m);
	}
	
	/**
	 * add new Food source to the grid.
	 * @param f
	 */
	public void addFood(Food f) {
		if(!food.contains(f))
			food.add(f);
	}
	
	/**
	 * add a new obstacle to the grid
	 * @param o
	 */
	public void addObstacle(Obstacle o) {
		if(!obstacle.contains(o))
			obstacle.add(o);
	}
	
	/**
	 * delete a mouse
	 * @param m
	 */
	public void deleteMouse(Mouse m) {
		for(int i=0; i<mouses.size();i++)
			if(m.equals(mouses.get(i)))
				mouses.remove(i);
	}
	
	/**
	 * delete a food source
	 * @param f
	 */
	public void deleteFood(Food f) {
		for(int i=0; i<food.size();i++)
			if(f.equals(food.get(i))) 
				food.remove(f);
	}
	
	/**
	 * check if its a mice position in the grid.
	 * @param p
	 * @return
	 */
	public boolean isMousePosition(Point p) {
		boolean isMousePosition = false;
		for(Iterator<Mouse>it = mouses.iterator(); it.hasNext(); ) {
			Point position = it.next().getPosition();
			if(position.equals(p)) {
				isMousePosition=true;
			}
		}
		return isMousePosition;
	}
	
	/**
	 * check if it's a food position in the grid
	 * @param p
	 * @return
	 */
	public boolean isFoodPosition(Point p) {
		boolean isMousePosition = false;
		for(Iterator<Food>it = food.iterator(); it.hasNext(); ) {
			Point position = it.next().getPosition();
			if(position.equals(p)) {
				isMousePosition=true;
			}
		}
		return isMousePosition;
	}
	
	public boolean prefDistanceObstacle(Point p) {
		
		boolean isInGoodPlace=true;
		
		for(int i=0; i<getObstacle().size(); i++) {
			Point p1 = getObstacle().get(i).position;
			if(p1.distance(p) <= 2d)
				isInGoodPlace = false;
		}
		return isInGoodPlace;
	}
	
	public boolean prefDistanceFood(Point p) {
		
		boolean isInGoodPlace=true;
		
		for(int i=0; i<getFood().size(); i++) {
			Point p1 = getFood().get(i).position;
			if(p1.distance(p) <= 2d)
				isInGoodPlace = false;
		}
		return isInGoodPlace;
	}
	
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("\t\t\t\t\t\t\t\t\t");
		for(int i=0; i<dimension; i++) {
			for(int j=0; j<dimension; j++) {
				Ground ground = getBoxAt(i, j).getGroundType();
					if(ground.isWall()) {
						sb.append(".");
					}else if(ground.isGrass()) {
						sb.append(" ");
					}else if(ground.isFood()) {
						sb.append('f');
					}else if(ground.isObstacle()) {
						sb.append("o");
					}
			}
			sb.append("\n");
			sb.append("\t\t\t\t\t\t\t\t\t");
		}
		return sb.toString();
	}
}