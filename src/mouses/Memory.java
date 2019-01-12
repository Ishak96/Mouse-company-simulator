package mouses;

import java.util.ArrayList;
import java.util.HashMap;

import log.MouseEvent;
import used.Direction;
import used.Point;
import used.Random;

/**
 * This class manages mice' memory.
 * @author anis
 */
public class Memory {
	
	/**********		attributs		**********/
	private ArrayList<MouseEvent> careerEvents;
	private ArrayList<Point> foodLocation;
	private ArrayList<Point> obstacleLocation;
	private ArrayList<Point> foodLocationStock;
	private ArrayList<Point> foodLocationGivenByOthers;
	private ArrayList<Direction> pathFood;
	private HashMap<String,Integer> KnowlegMics;
	private Point foodToEat;
	/**********		construct		**********/
	
	public Memory() {
		careerEvents = new ArrayList<MouseEvent>();
		foodLocation=new ArrayList<Point>();
		obstacleLocation = new ArrayList<>();
		foodLocationStock = new ArrayList<>();
		pathFood = new ArrayList<>();
		foodLocationGivenByOthers = new ArrayList<>();
		KnowlegMics = new HashMap<String,Integer>();
		foodToEat = new Point(Random.randomInt(1, 19),Random.randomInt(1, 19));
	}
	
	/**********		methods		**********/
				//getters
	public void addEvent(String imageName,String descriptionText) {
		careerEvents.add(new MouseEvent(imageName, descriptionText));
	}
	
	public Point getFoodToEat() {
		return foodToEat;
	}
	
	public ArrayList<MouseEvent> getCareer(){
		return careerEvents;
	}
	
	public Point getfoodLocation(Point p) {
		
		Point food = null;
		Point foodMouse,foodStock;
		
		if(!foodLocation.isEmpty()) {
			
			foodStock = foodLocation.get(0);
			food = foodStock;
			double distance1 = foodStock.distance(p);
			
			if(!foodLocationGivenByOthers.isEmpty()) {
				
				foodMouse = foodLocationGivenByOthers.get(0);
				double distance2 = foodMouse.distance(p);
				
				if(distance2 < distance1)
					food = foodMouse;
			}
		}
		
		return food;
	}
	
	public ArrayList<Point> getAllFoodLocation(){
		return foodLocation;
	}
	
	public ArrayList<Point> getFoodLocationStock(){
		return foodLocationStock;
	}
	
	
	public ArrayList<Direction> getPathFood(){
		return pathFood;
	}
	
	public ArrayList<Point> getFoodLocationGivenByOthers(){
		return foodLocationGivenByOthers;
	}
	
	public ArrayList<Point> getObstacleLocation() {
		return obstacleLocation;
	}
			//setters
	public void setFoodLocation(ArrayList<Point> arrayList) {
		ArrayList<Point> alPoint = new ArrayList<>(); 
		
		for(int i=0; i<arrayList.size();i++) {
			alPoint.add(new Point(arrayList.get(i).getAbscisse(),arrayList.get(i).getOrdonne()));
		}
		this.foodLocation=alPoint;
	}
	
	public void setObstacleLocation(ArrayList<Point> arrayList) {
		obstacleLocation = arrayList;
	}
	
			//otheres
	
	public void delatFoodLocationGivenByOthers(Point p) {
		if(foodLocationGivenByOthers.contains(p))
			foodLocationGivenByOthers.remove(p);
	}
	
	/* this methode finds the mouse closest to her */
	public void orderFoodMermory(Point p,String nom) {
		
		foodToEat = new Point(Random.randomInt(1, 19),Random.randomInt(1, 19));
		
		double distance1 ;
		if(foodLocation.size()!=0) {
			foodToEat = foodLocation.get(0);
			distance1 = p.distance(foodToEat);
			for(int i = 0 ; i<foodLocation.size();i++) {
				Point p2 = foodLocation.get(i);
				double distance2 = p.distance(p2);
				if(distance1 > distance2) {
					foodToEat = p2;
					distance1 = p.distance(foodToEat);		
				}
			}
		}
		
		if(foodLocationStock.size()!=0) {
			if(!foodLocation.contains(foodToEat)) {
				foodToEat = foodLocationStock.get(0);
			}
			
			distance1 = p.distance(foodToEat);
			for(int i = 0 ; i<foodLocationStock.size();i++) {	
				Point p2 = foodLocationStock.get(i);
				double distance2 = p.distance(p2);
				if(distance1 > distance2) {
					foodToEat = p2;
					distance1 = p.distance(foodToEat);		
				}	
			}
		}
		
		if(foodLocationGivenByOthers.size()!=0) {
			if(!foodLocation.contains(foodToEat) && !foodLocationStock.contains(foodToEat)) {
				foodToEat = foodLocationGivenByOthers.get(0);
			}
			
			distance1 = p.distance(foodToEat);
			for(int i = 0 ; i<foodLocationGivenByOthers.size();i++) {
				Point p2 = foodLocationGivenByOthers.get(i);
				double distance2 = p.distance(p2);
				if(distance1 > distance2) {
					foodToEat = p2;
					distance1 = p.distance(foodToEat);		
				}	
			}
		}
	}
	public void addFoodLocationGivenByOthers(Point p) {
		if(!foodLocationGivenByOthers.contains(p))
			foodLocationGivenByOthers.add(p);
	}
	
	public void addFoodLocation(Point p) {
		if(!foodLocation.contains(p))
			foodLocation.add(p);
	}

	public void obstacleLocationRemoveAll() {
		obstacleLocation.removeAll(obstacleLocation);
	}
	
	public void addFoodLocationStock(Point p) {
		if(!foodLocationStock.contains(p))
			foodLocationStock.add(p);
	}
	
	public void iHadMeetThisMouse(Mouse m, int SimulationTurn) {
		KnowlegMics.put(m.getName(), SimulationTurn);
	}
	
	public boolean didiTalkWithThisMouse(Mouse m) {
		return KnowlegMics.containsKey(m.getName());
	}
	
	public void removeFoodLocation() {
		foodLocation.removeAll(foodLocation);
	}
	
	public int getTalkRound(Mouse m) {
		int talkRound = 0;
		
		if(didiTalkWithThisMouse(m)) {
			talkRound = KnowlegMics.get(m.getName());
		}
		
		return talkRound;
	}
}