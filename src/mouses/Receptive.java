package mouses;

import java.util.ArrayList;
import java.util.Iterator;

import gui.SelectPlayModeGui;
import used.Point;
import used.Random;

public class Receptive extends Mouse{

	public Receptive(Point position, Memory memory, Behavior behavior, int age, String gender, int trust) {
		super(position, memory, behavior, age, gender,trust);
	}

	@Override
	public boolean isReciptive() {
		return true;
	}

	@Override
	public boolean isNihilist() {
		return false;
	}

	@Override
	public void giveInformations(Mouse mouse,boolean information) {
		ArrayList<Point> foodPosition = null;
		if(information) {
			foodPosition = mouse.getMemory().getFoodLocationStock();
		}
		else {
			foodPosition = randomFakeFoodLocation(mouse);
		}
		for(Iterator<Point>it = foodPosition.iterator(); it.hasNext();) {
			Point p = it.next();
				
			this.getMemory().addFoodLocationGivenByOthers(p);
		}
	}
	
	public ArrayList<Point> randomFakeFoodLocation(Mouse mouse){
		ArrayList<Point> FakeFoodLocation = new ArrayList<Point>();
		int x,y,numberOfFakeFoodPosition;
		Point p;
		
		numberOfFakeFoodPosition = Random.randomInt(0, 10);
		for(int i=0; i<numberOfFakeFoodPosition; i++)
			do{
				x= Random.randomInt(1,SelectPlayModeGui.getDimension()-2);
				y= Random.randomInt(1,SelectPlayModeGui.getDimension()-2);
				p=new Point(x,y);
				
			}while(mouse.getMemory().getFoodLocationStock().contains(p) && mouse.getMemory().getAllFoodLocation().contains(p));
		
		return FakeFoodLocation;
	}
}
