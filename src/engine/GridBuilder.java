package engine;

import grid.BoxFactory;
import grid.Food;
import grid.Grid;
import grid.Obstacle;
import gui.SelectPlayModeGui;
import mouses.MiceFactory;
import mouses.Mouse;
import used.Point;
import used.Random;

/**
 * This class initializes the grid's settings for the first time.
 * @author ishak,anis
 *
 */

public class GridBuilder {
	
	/**********		attributs		**********/
	private Grid grid ;
	private int dimension;
	private int numberOfMice;
	private int foodDansity;
	private int obstacleDansity;
	/**********		construct		**********/
	
	/**
	 * The parameters of the grid which will be generated must be specified in the argument.
	 * @param params The parameters of the grid.
	 */
	
	public GridBuilder(GridParameters parameters) {
		grid = new Grid();
		dimension = parameters.getDimension();
		numberOfMice=parameters.getNbMouse();
		foodDansity=parameters.getFreqFood();
		obstacleDansity=parameters.getFreqObstacle();
		buildeGrid(parameters.getGround());
		grid.setGridParameters(parameters);
	}
	/**********		methodes		**********/
				//getters
	
	/**
	 * Returns the grid.
	 * @return Grid
	 */
	
	public Grid getGrid() {
		return grid;
	}
				//others
	
	/**
	 * This method places obstacles and foods and mice in the grid according to the obstacle,food,mice density,
	 * @param ground
	 */
	
	public void buildeGrid(String ground) {
		for(int i=0;i<dimension;i++) {
			for(int j=0;j<dimension;j++) {
				if(i==0 || i==dimension-1 || j==0 || j==dimension-1) {
					grid.setBoxAt(i, j,  BoxFactory.creatBox(BoxFactory.creatWall(i, j)));
					grid.setBoxAtFree(i, j, false);
				}else {
					grid.setBoxAt(i, j,  BoxFactory.creatBox(BoxFactory.creatGrass(i, j, ground)));
				}
			}
		}
		
		randomPositionFood();
		
		randomPositionObstacle();
		
		randomPositionMouse();
	}
	
		//random
	
	/**
	 * this methode random obstacle positions
	 */
	
	public void randomPositionObstacle() {
		int x,y;
		Point p;
		for(int i = 0 ; i< obstacleDansity ; i++ ) {
			do
			{
				 x= Random.randomInt(2,SelectPlayModeGui.getDimension()-3);
				 y= Random.randomInt(2,SelectPlayModeGui.getDimension()-3);
				 p= new Point(x,y);
			}
			while(!grid.getBoxAt(x, y).getIsFree() || !grid.prefDistanceObstacle(p));
			Obstacle o = BoxFactory.creatObstacle(x, y);
			grid.addObstacle(o);
			grid.setBox(x,y,o);
			grid.setBoxAtFree(x, y, false);
		}
		
	}
	
	/**
	 * this methode random Food positions
	 */
	
	public void randomPositionFood() {		
		int x,y;
		Point p;
		for(int i = 0 ; i< foodDansity; i++ )
		{
			do
			{
				x= Random.randomInt(1,SelectPlayModeGui.getDimension()-2);
				y= Random.randomInt(1,SelectPlayModeGui.getDimension()-2);
				p=new Point(x,y);
				
			}while(!grid.getBoxAt(x, y).getIsFree() || !grid.prefDistanceFood(p));
			Food f = BoxFactory.creatFood(x, y, "Start");
			grid.addFood(f);
			grid.setBox(x,y,f);
			grid.setBoxAtFree(x, y, false);
		}
	}
	
	/**
	 * this methode random Mouse positions
	 */
	
	public void randomPositionMouse() {	
		int x,y;
		for(int i = 0 ; i< numberOfMice; i++ )
		{
			do
			{	
			// my friends i use this buckle for avoid the repetition of the point generated 
				x= Random.randomInt(1,SelectPlayModeGui.getDimension()-2);
				y= Random.randomInt(1,SelectPlayModeGui.getDimension()-2);
			}
			while(!grid.getBoxAt(x, y).getIsFree());

				Point p = new Point(x,y);
				Mouse m = MiceFactory.creatRandomType(p,30);
				grid.addMouse(m);
		}		
	}
}