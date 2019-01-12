package used;

import engine.GridParameters;

public class GenerateGameMode {
	
	/********	methodes	**********/
	
	public static int mouseLife() {
		int lifeOfMouse=0;
		String ground=GridParameters.getInstance().getGround();
		
		switch (ground) {
			case "Grass":
				lifeOfMouse=60;
				break;
			case "Desert":
				lifeOfMouse=30;
				break;
			case "Snow":
				lifeOfMouse=50;
				break;
		}
		return lifeOfMouse;
	}
	
	public static int deminuLife() {
		int dimnu=0;
		String ground=GridParameters.getInstance().getGround();
		
		switch (ground) {
			case "Grass":
				int i = Random.randomInt(1, 2);
				if(i%2==0)
					dimnu=1;
				else
					dimnu=0;
				break;
			case "Desert":
				dimnu=1;
				break;
			case "Snow":
				dimnu=1;
				break;
		}
		return dimnu;
	}
	
	public static int eatLifePlus() {
		int plus=0;
		String ground=GridParameters.getInstance().getGround();
		
		switch (ground) {
			case "Grass":
				plus=4;
				break;
			case "Desert":
				plus=2;
				break;
			case "Snow":
				plus=3;
				break;
		}
		return plus;
	}

	public static int foodQuantity(String simulationTurn) {
		int quantity=0;
		String ground=GridParameters.getInstance().getGround();
		
		switch (ground) {
			case "Grass":
				if(simulationTurn.equals("Start"))
					quantity=10;
				else if(simulationTurn.equals("Midel"))
					quantity=5;
				else
					quantity=3;
				break;
			case "Desert":
				if(simulationTurn.equals("Start"))
					quantity=5;
				else if(simulationTurn.equals("Midel"))
					quantity=3;
				else
					quantity=1;
				break;
			case "Snow":
				if(simulationTurn.equals("Start"))
					quantity=10;
				else if(simulationTurn.equals("Midel"))
					quantity=5;
				else
					quantity=3;
				break;
		}
		return quantity;
	}
	
	public static int foodTime(String simulationTurn) {
		int time=0;
		
		switch (simulationTurn) {
			case "Start":
				time=600;
				break;
			case "Midel":
				time=400;
				break;
			case "Final":
				time=200;
				break;
		}

		return time;
	}
	
	public static String genderOfMice(int rand) {
		if(rand==1)
			return "Male";
		else
			return "Female";
	}
	
	public static int threadMap(String ground) {
		if(ground.equals("Snow"))
			return 800;
		else
			return 500;
	}
}