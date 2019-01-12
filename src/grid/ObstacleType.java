package grid;

import java.util.*;

import engine.GridParameters;
import used.Random;

/**
 * Enumerates the types of obstacle.
 * @author ishak
 *
 */

public enum ObstacleType {

	Obstacle1{
		public String toString() {
	        return "Obstacle1";
	    }
	}
	, Obstacle2{
		public String toString() {
	        return "Obstacle2";
	    }
	}
	, Obstacle3{
		public String toString() {
	        return "Obstacle3";
	    }
	}
	, Obstacle4{
		public String toString() {
	        return "Obstacle4";
	    }
	};
	
	private static final List<ObstacleType> VALUES = Collections.unmodifiableList(Arrays.asList(values()));
	private static final int SIZE = VALUES.size();
	
	  public static ObstacleType randomType()  {
		    return VALUES.get(Random.randomInt1(SIZE));
		  }
		  
		  public static ObstacleType getType(int i){
			return VALUES.get(i);  
		  }
		  
		  public static String[] getTypeNames(){
			  String[] arr = new String[SIZE];
			  
			  	if(GridParameters.getInstance().getGround().equals("Grass")) {
			  		arr[0] = "Barrel";
				  	arr[1] = "Box";
				  	arr[2] = "Lake";
				  	arr[3] = "Wood";
			  	}
			  	else if(GridParameters.getInstance().getGround().equals("Snow")) {
			  		arr[0] = "Rock";
			  		arr[1] = "Iceberg";
			  		arr[2] = "Fir";
			  		arr[3] = "Snowman";
			  	}
			  	else {
			  		arr[0] = "Stone";
					arr[1] = "cactus";
					arr[2] = "Hole";
					arr[3] = "Rock";
			  	}
			  return arr;
		  }
}
