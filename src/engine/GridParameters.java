package engine;

public class GridParameters {
	
	/**
	 * This class is used to stock informations about the grid.
	 * @author ishak
	 *
	 */
	
	/**********		attributs		**********/
	private int freqObstacle,
				freqFood,
				nbMouse,
				dimension;
	private String ground;
	
	private static GridParameters instance = new GridParameters(25, 25, 10, 21, "Grass");
	/**********		construct		**********/
	
	/**
	 * This contructor initializes the parameters to specified values.
	 * @param freqObstacle obstacle frequency
	 * @param freqFood food frequency
	 * @param nbMouses number of mice
	 * @param dimension dimension of grid
	 * @param ground ground type
	 */
	
	private GridParameters(int freqObstacle, int freqFood, int nbMouses, int dimension, String ground) {
		this.freqObstacle=freqObstacle;
		this.freqFood=freqFood;
		this.nbMouse=nbMouses;
		this.ground=ground;
		this.dimension=dimension;
	}
	/**********		methodes		**********/
			//getters
	public int getDimension() {
		return dimension;
	}
	public String getGround() {
		return ground;
	}
	public int getFreqObstacle() {
		return freqObstacle;
	}
	
	/**
	 * return the unique instance of this class
	 * @return GridParameters
	 */
	
	public static GridParameters getInstance() {
		return instance;
	}
	
	public int getFreqFood() {
		return freqFood;
	}
	public int getNbMouse() {
		return nbMouse;
	}
			//setters
	public void setDimension(int dimension) {
		this.dimension=dimension;
	}
	public void setFreqObstacle(int freqObstacle) {
		this.freqObstacle = freqObstacle;
	}
	public void setFreqFood(int freqFood) {
		this.freqFood = freqFood;
	}
	public void setNbMouse(int nbMouse) {
		this.nbMouse = nbMouse;
	}
	public void setGroung(String ground) {
		this.ground = ground;
	}
	
	/**
	 * set all parameters of the grid
	 * @param freqObstacle
	 * @param freqFood
	 * @param nbMouse
	 * @param dimension
	 * @param ground
	 */
	
	public void setAll(int freqObstacle, int freqFood, int nbMouse, int dimension,String ground) {
		this.freqObstacle = freqObstacle;
		this.freqFood = freqFood;
		this.nbMouse = nbMouse;
		this.ground = ground;
		this.dimension=dimension;
	}

}
