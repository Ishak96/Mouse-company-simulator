package used;

import java.util.ArrayList;

import grid.Grid;

public class Vision {
	
	/**********		attribut	*********/
	private int horizon;
	private ArrayList<Point> foodLocationVision ; 
	private ArrayList<Point> obstacleLocationVision;
	/***********	construct	*********/
	public Vision(int horizon) {
		this.horizon=horizon;
		foodLocationVision = new ArrayList<>();
		obstacleLocationVision = new ArrayList<>();
	}

	/***********	methodes	*********/
			//getters
	
	public int getHorizon() {
		return horizon;
	}
	
	
	public ArrayList<Point> getFoodLocationVision(){
		return foodLocationVision;
	}
	
	public ArrayList<Point> getObstacleLocationVision(){
		return obstacleLocationVision;
	}
			//setters
	public void setHorizon(int horizon) {
		this.horizon=horizon;
	}
			//others
	
	public void addFoodLocationVision( Point p) {
		if(!foodLocationVision.contains(p)) {
			foodLocationVision.add(p);
		}
	}
	
	public void addObstacleLocationVision( Point p) {
		if(!obstacleLocationVision.contains(p)) {
			obstacleLocationVision.add(p);
		}
	}
	
	public void foundFood(int xMouse , int yMouse , Grid grid) {
		
		for( int i=xMouse-horizon; i <= xMouse+horizon ; i++ ) {
			
			for( int j=yMouse-horizon; j <= yMouse+horizon; j++) {
				if(i<grid.getDimension()-1 && j<grid.getDimension()-1 && i>0 && j>0) {
					if(grid.getBoxAt(i,j).getGroundType().isFood()) {
						addFoodLocationVision(new Point(i,j));
					}
					if(grid.getBoxAt(i, j).getGroundType().isObstacle()) {
						addObstacleLocationVision(new Point(i,j));
					}
					if(grid.getBoxAt(i, j).getGroundType().isWall()) {
						addObstacleLocationVision(new Point(i,j));
					}
				}
			}
		}
		
	}
	
	public void removeFoodLocationVision() {
		foodLocationVision.removeAll(foodLocationVision);
	}
	public void removeObstacleLocationVision() {
		obstacleLocationVision.removeAll(obstacleLocationVision);
	}
}
