package grid;

import grid.Ground;

/**
 * This class designates a Box of which the grid is composed
 * @author ishak
 *
 */

public class Box {
	
	/**********		attributes		***********/
	private boolean isFree;
	private Ground groundType;
	/**********		construc		**********/
	
	public Box(Ground groundType) {
		isFree = true;
		this.groundType = groundType;
	}
	/**********		methodes		**********/
				//getters
	public Ground getGroundType() {
		return groundType;
	}
	
	public boolean getIsFree() {
		return isFree;
	}
				//setters
	public void setGroundType(Ground groundType) {
		this.groundType = groundType;
	}
	
	public void setIsFree(boolean isFree) {
		this.isFree = isFree;
	}
				//orthers
}