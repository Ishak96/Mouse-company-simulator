package used;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
/**
 * This enumeration contains the directions used by the mice to move.
 */
public enum Direction {
	Left{
		public String toString() {
	        return "Left";
	    }
	}
	, Up{
		public String toString() {
	        return "Up";
	    }
	}
	, Right{
		public String toString() {
	        return "Right";
	    }
	}
	, Down{
		public String toString() {
	        return "Down";
	    }
	};
	
	
	
	 private static final List<Direction> VALUES =
			    Collections.unmodifiableList(Arrays.asList(values()));
			  private static final int SIZE = VALUES.size();
	
			  public static Direction randomDirection()  {
			    return VALUES.get(Random.randomInt1(SIZE));
			  }
}