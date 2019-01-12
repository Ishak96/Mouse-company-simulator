package grid;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Enumerates the types of Food.
 * @author ishak
 */

public enum FoodType {

	Food1{
		public String toString() {
	        return "food1";
	    }
	}
	, Food2{
		public String toString() {
	        return "food2";
	    }
	}
	, Food3{
			public String toString() {
		        return "food3";
		    }
	};
	
	private static final List<FoodType> VALUES = Collections.unmodifiableList(Arrays.asList(values()));
	
	  public static FoodType randomType(String simulationTurn)  {
		  FoodType food = null;
		  	switch (simulationTurn) {
		  		case "Start":
		  			food = VALUES.get(0);
		  			break;
		  		case "Midel":
		  			food = VALUES.get(2);
		  			break;
		  		case "Final":
		  			food = VALUES.get(1);
		  			break;
		  	}
		  return food;
	  }
		  
		  public static FoodType getType(int i){
			return VALUES.get(i);  
		  }
}
