package engine;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import grid.BoxFactory;
import grid.Food;
import grid.Grid;
import grid.Obstacle;
import gui.SelectPlayModeGui;
import log.LogWriter;
import mouses.Behavior;
import mouses.Cooperative;
import mouses.Memory;
import mouses.MiceFactory;
import mouses.Mouse;
import mouses.Selfish;
import stat.Statistics;
import used.Direction;
import used.GenerateGameMode;
import used.Point;
import used.Random;

public class Simulation {
	
	/**
	 * this class maneg the whole simulation
	 * 
	 * @author ishak
	 */
	
	/*********	attributs	*********/
	private Grid grid;
	private GridParameters parameters;
	private static int simulationNumberOfTurn,foodAddFreq;
	private Statistics stat = Statistics.getInstance();
	private LogWriter logWr = LogWriter.getInstance();
	private ArrayList<Mouse> mouseToKill=new ArrayList<Mouse>();
	private ArrayList<Mouse> mouseToAdd=new ArrayList<Mouse>();
	private ArrayList<Food> foodPosition=new ArrayList<Food>();
	private ArrayList<Food> foodInGrid;
	public static ArrayList<String>boysNames=new ArrayList<String>();
	public static ArrayList<String>girlsNames=new ArrayList<String>();
	private int foodFreq = GridParameters.getInstance().getFreqFood();
	/********	construct	*********/
	
	/**
	 * mice name are generated when the simulation are launched
	 * @param parameters
	 */
	
	public Simulation(GridParameters parameters) {
		generMiceName("/miceName/miceNames.txt");
		this.parameters=parameters;
		simulationNumberOfTurn=1;
		foodAddFreq=foodGenerFreq();
	}
	/********	methodes	*********/
			//getters
	
	public GridParameters getParameters() {
		return parameters;
	}
	
	public Grid getGrid() {
		return grid;
	}
	
	public static int getSimulationTurn() {
		return simulationNumberOfTurn;
	}
	
	public ArrayList<Mouse> getMousesToKill(){
		return mouseToKill;
	}
			//setters
	
	public void setGridParametes(GridParameters parameters) {
		this.parameters=parameters;
	}
	
	public void setGrid(Grid grid) {
		this.grid=grid;
	}
			//others
	
	/*************************		Simulation engine	**************************/
	
	/**
	 * generer of grid and upload statistics
	 */
	
	public void generatGrid() {
		
		GridBuilder buildGrid = new GridBuilder(parameters);
		
		grid = buildGrid.getGrid();
		
		stat.setAliveMice(grid.getMouses().size());
		stat.setNbrFoodSources(grid.getFood().size());
		stat.setDeadMice(mouseToKill.size());
	}
	
	/**
	 * hill up a new turn of simulation
	 */
	
	public void simulationNextTurn() {
		
		logWr.writeInfos("____________________________________________________\n Round "+simulationNumberOfTurn+":");
		
		foodInGrid=grid.getFood();
			
		for(Mouse m : grid.getMouses()) {
			if(!m.isDead()) {
				mouseToDo(m);
			}
		}
		
		KillMouses();
		bornMouse();
		
		foodAddFreq--;
		foodGeneration();
		
		for(int i=0; i<foodInGrid.size(); i++) {
			Food food=foodInGrid.get(i);
			
			foodAction(food);
		}
		// update stat:
		
		stat.setNbrFoodSources(grid.getFood().size());
		stat.setAliveMice(grid.getMouses().size());
		stat.setDeadMice(mouseToKill.size());
		
		simulationNumberOfTurn++;
		
	}
	/*************************		Mice & Chose Direction Methodes		**************************/
	
	/**
	 * mouse action.
	 * @param m Mouse
	 */
	
	public void mouseToDo(Mouse m) {
		Point pos = m.getPosition();
		
		if(!m.getIsTalking()) {
			m.mousesEvolu();
			lookAround(m);
		}
		
		else {
			m.talk();
		}
		
		if(m.getAge()>100 && m.getLife()>=70 && !m.isPragant() && m.getGender().equals("Female")) {
			m.setIsPragnant(true);
			stat.incrementPregnantMouse();
			logWr.writeMouseAction(m, "pregnant");
		}
		
		if(inFoodPosition(pos) && m.mouseStayInPositionToEat()) {
			if(grid.getBoxAt(pos.getAbscisse(), pos.getOrdonne()).getGroundType().setQuantity()) {
				m.mouseEat();
				logWr.writeMouseAction(m,"eat");
			}else {
				Food f = grid.getFoodAt(pos.getAbscisse(), pos.getOrdonne());
				foodAction(f);
			}
			
		}
		else if(!m.getIsTalking()){
			m.setIsEating(false);
			if(!m.getTakeControle()) {
				m.getVision().foundFood(m.getPosition().getAbscisse(), m.getPosition().getOrdonne(), grid);
				if(m.getLife()<30) {
					
					m.getMemory().setFoodLocation(m.getVision().getFoodLocationVision());
					m.getMemory().setObstacleLocation(m.getVision().getObstacleLocationVision());
					m.getMemory().orderFoodMermory(m.getPosition(),m.getName());
					m.moveMouseToFoodLocation();
					moveMouse(m);
					
					if(m.getPosition().equals(m.getMemory().getfoodLocation(m.getPosition()))) {
						if(m.getMemory().getFoodLocationGivenByOthers().contains(m.getPosition())) {
							if(grid.isFoodPosition(m.getPosition())) {
								m.incrementTrust();
								m.getMemory().addFoodLocationStock(m.getMemory().getfoodLocation(m.getPosition()));
							}
							else {
								m.setFakePosition(true);
								m.getMemory().delatFoodLocationGivenByOthers(pos);
								m.decrementTrust();
							}
						}
						else {
							m.getMemory().addFoodLocationStock(m.getMemory().getfoodLocation(m.getPosition()));
						}
					}
					
				
				}	
				else {
					moveTo(m,m.getLastDirection());
				}
			}
			else {
				canWeMoveit(m.getPosition(),m);
			}
		}
		
		if(m.isDead()) {	
			m.getBehavior().setPicture("Dead1",m.getAge());
			logWr.writeMouseAction(m,"dead");
			mouseToKill.add(m);
		}
		
		if(m.bornBabyMouse()) {
			birthMouse(m);
			m.setIsPragnant(false);
			stat.decrementPregnantMouse();
			m.setPregnancyTime(50);
		}
		

		if(inFoodPosition(pos)) {
			m.getMemory().addFoodLocationStock(pos);
		}
		
		if(!inFoodPosition(pos) && m.getMemory().getFoodLocationStock().contains(pos)) {
			m.getMemory().getFoodLocationStock().remove(pos);
		}
		
		if(!inFoodPosition(pos) && m.getMemory().getFoodLocationGivenByOthers().contains(pos)) {
			m.getMemory().getFoodLocationGivenByOthers().remove(pos);
		}
		
		m.getVision().removeFoodLocationVision();
		m.getMemory().removeFoodLocation();
		
	}
	
	/**
	 * Move a mouse.
	 * @param m
	 */
	
	public void moveMouse(Mouse m) {
		Direction dir = m.getLastDirection();
		Point mPos = m.getPosition();
		Point nextPos;
		
		if (dir == Direction.Left) {
			nextPos = new Point(mPos.getAbscisse() - 1, mPos.getOrdonne());
			choseTypeOfMove(nextPos,m);
		}else if (dir == Direction.Right) {
			nextPos = new Point(mPos.getAbscisse() + 1, mPos.getOrdonne());
			choseTypeOfMove(nextPos,m);
		}else if (dir == Direction.Up) {
			nextPos = new Point(mPos.getAbscisse(), mPos.getOrdonne() - 1);
			choseTypeOfMove(nextPos,m);
		}else {
			nextPos = new Point(mPos.getAbscisse(), mPos.getOrdonne() + 1);
			choseTypeOfMove(nextPos,m);
		}
	}
	
	/**
	 * check if its a food location
	 * @param pos
	 * @return
	 */
	
	public boolean inFoodPosition(Point pos) {
		return grid.getBoxAt(pos.getAbscisse(), pos.getOrdonne()).getGroundType().isFood();
	}
	
	public boolean canWeMoveit(Point nextPos, Mouse m) {
		boolean move=false;
		
		if(grid.getBoxAt(nextPos.getAbscisse(), nextPos.getOrdonne()) != null
				&& grid.getBoxAt(nextPos.getAbscisse(), nextPos.getOrdonne()).getGroundType().isFood()) {
			
			m.getBehavior().setPicture("Food",m.getAge());
			
			move = true;
		}else if (grid.getBoxAt(nextPos.getAbscisse(), nextPos.getOrdonne()) != null
				&& grid.getBoxAt(nextPos.getAbscisse(), nextPos.getOrdonne()).getIsFree()) {
			
			m.getBehavior().setPicture(m.getLastDirection().toString(),m.getAge());
				
			move = true;
		}
		return move;
	}
	
	/**
	 * mouse move to a random directionmove randomly a mouse.
	 * @param m
	 */
	
	public void moveRandomly(Mouse m) {
		 moveTo(m, Direction.randomDirection());
	}
	
	/**
	 * move mouse.
	 * @param m
	 * @param dir
	 */
	
	public void moveTo(Mouse m, Direction dir) {
		m.setLastDirection(dir);
		Point mPos = m.getPosition();
		Point nextPos;
		
		if (dir == Direction.Left) {
			nextPos = new Point(mPos.getAbscisse() - 1, mPos.getOrdonne());
		}else if (dir == Direction.Right) {
			nextPos = new Point(mPos.getAbscisse() + 1, mPos.getOrdonne());
		}else if (dir == Direction.Up) {
			nextPos = new Point(mPos.getAbscisse(), mPos.getOrdonne() - 1);
		}else if(dir == Direction.Down){
			nextPos = new Point(mPos.getAbscisse(), mPos.getOrdonne() + 1);
		}else {
			nextPos = new Point(mPos.getAbscisse(), mPos.getOrdonne());
		}
		
		if(canWeMoveit(nextPos,m)) {
			m.setPosition(nextPos);
			logWr.writeMove(m, dir.toString());
		}else {
			moveRandomly(m);
			logWr.writeMouseAction(m,"obstacle");
		}
	}
	
	public boolean choseTypeOfMove(Point nextPos, Mouse m) {
		if(canWeMoveit(nextPos,m)) {
			m.setPosition(nextPos);
			return true;
		}else {
			moveRandomly(m);
			return false;
		}
	}
	
	/**
	 * move to up a dead mouse.
	 * @param m
	 * @return
	 */
	
	public boolean moveDeadMouse(Mouse m) {
		Point mPos = m.getPosition();
		Point nextPos;
		
		nextPos = new Point(mPos.getAbscisse(), mPos.getOrdonne() - 1);
		
		if(grid.getBoxAt(nextPos.getAbscisse(), nextPos.getOrdonne()) != null
				&& !grid.getBoxAt(nextPos.getAbscisse(), nextPos.getOrdonne()).getGroundType().isWall()) {
			if(simulationNumberOfTurn%2==0) {
				m.getBehavior().setPicture("Dead1",m.getAge());
			}else {
				m.getBehavior().setPicture("Dead2",m.getAge());
			}
			m.setPosition(nextPos);
			return true;
		}
		return false;
	}
	
	/**
	 * deletes mice which where killed in the current turn.
	 */
	
	public void KillMouses() {
		for(int i=0; i< mouseToKill.size(); i++) {
			Mouse m = mouseToKill.get(i);
			if(!moveDeadMouse(m)) 
				grid.deleteMouse(m);	
		}
	}
	
	/**
	 * add new mice to the grid.
	 */
	
	public void bornMouse() {
		for(int i=0; i<mouseToAdd.size(); i++) {
			Mouse m = mouseToAdd.get(i);
			if(grid.getMouses().size() <= 60)
				grid.addMouse(m);
		}
		mouseToAdd.clear();
	}
	
	/**
	 * associate the same behavior of the parent to the new mice
	 * @param m mouse parent
	 */
	
	public void birthMouse(Mouse m) {
		Point pos = m.getPosition();
		Behavior behavior = m.getBehavior();
		
		int randGender = Random.randomInt(1, 2);
			
		if(behavior.type().equals("Selfish")) {
			stat.incrementSelfish();
			if(m.isReciptive()) {
				mouseToAdd.add(MiceFactory.creatReciptiveMouse(pos,new Memory(),new Selfish(0),0,GenerateGameMode.genderOfMice(randGender),5));
				stat.incrementReciptive();
			}else {
				mouseToAdd.add(MiceFactory.creatNehilistMouse(pos,new Memory(),new Selfish(0),0,GenerateGameMode.genderOfMice(randGender),5));
				stat.incrementNihilist();
			}
		}else {
			stat.incrementCooperatice();
			if(m.isReciptive()) {
				mouseToAdd.add(MiceFactory.creatReciptiveMouse(pos,new Memory(),new Cooperative(0),0,GenerateGameMode.genderOfMice(randGender),10));
				stat.incrementReciptive();
			}else {
				mouseToAdd.add(MiceFactory.creatNehilistMouse(pos,new Memory(),new Cooperative(0),0,GenerateGameMode.genderOfMice(randGender),10));
				stat.incrementNihilist();
			}
		}
		if(!m.getName().equals("added by user"))
			stat.incrementBornedMice();
	}
	
	/**
	 * makes a mouse see around it.
	 * @param m
	 */
	
	public void lookAround(Mouse m) {
		Mouse m1 = null;
		Point northPosition = new Point(m.getPosition().getAbscisse(),m.getPosition().getOrdonne()-1);
		Point southPosition = new Point(m.getPosition().getAbscisse(),m.getPosition().getOrdonne()+1);
		Point eastPosition = new Point(m.getPosition().getAbscisse()+1,m.getPosition().getOrdonne());
		Point westPosition = new Point(m.getPosition().getAbscisse()-1,m.getPosition().getOrdonne());
		
		if(!m.isDead())
			if(grid.isMousePosition(northPosition)) {
				m1 = grid.getMouseAt(northPosition);
				if(!m1.getIsTalking() && !m1.isDead() && m.getMemory().getTalkRound(m1)+20 <= simulationNumberOfTurn)
					setTalkingPicture(m,m1,"north");
			}
			else if(grid.isMousePosition(southPosition)) {
				m1 = grid.getMouseAt(southPosition);
				if(!m1.getIsTalking() && !m1.isDead() && m.getMemory().getTalkRound(m1)+20 <= simulationNumberOfTurn)	
					setTalkingPicture(m,m1,"south");
			}
			else if(grid.isMousePosition(eastPosition)) {
				m1 = grid.getMouseAt(eastPosition);
				if(!m1.getIsTalking() && !m1.isDead() && m.getMemory().getTalkRound(m1)+20 <= simulationNumberOfTurn)
				setTalkingPicture(m,m1,"east");
			}
			else if(grid.isMousePosition(westPosition)) {
				m1 = grid.getMouseAt(westPosition);
				if(!m1.getIsTalking() && !m1.isDead() && m.getMemory().getTalkRound(m1)+20 <= simulationNumberOfTurn)
					setTalkingPicture(m,m1,"west");
			}
		
		if(m1 != null && !m.getIsTalking() && !m1.isDead() && m.getMemory().getTalkRound(m1)+20 <= simulationNumberOfTurn) {
			if(!m1.getIsTalking()) {
				m.setIsTalking(true);
				m1.setIsTalking(true);
				m.talkWith(m1);
				m.getMemory().iHadMeetThisMouse(m1, simulationNumberOfTurn);
				m1.getMemory().iHadMeetThisMouse(m, simulationNumberOfTurn);
				if(m1.getAge()>20 && m.getAge()>20)
					if(m1.getGender().equals("Male") && m.getGender().equals("Female") && !m.isPragant()) {
						m1.setMeet(true);
						m.setMeet(true);
						m.setIsPragnant(true);
						stat.incrementPregnantMouse();
						logWr.writeMouseAction(m, "pregnant");
					}
					else if(m1.getGender().equals("Female") && m.getGender().equals("Male") && !m1.isPragant()) {
						m1.setMeet(true);
						m.setMeet(true);
						m1.setIsPragnant(true);
						stat.incrementPregnantMouse();
						logWr.writeMouseAction(m1, "pregnant");
					}
			}
		}
	}
	
	public void setTalkingPicture(Mouse mouse1, Mouse mouse2, String where) {
		
		switch (where) {
		case "north":
			mouse1.getBehavior().setPicture("Up", mouse1.getAge());
			mouse2.getBehavior().setPicture("Down", mouse2.getAge());
			mouse1.setLastDirection(Direction.Up);
			mouse2.setLastDirection(Direction.Down);
			break;
		case "south":
			mouse1.getBehavior().setPicture("Down", mouse1.getAge());
			mouse2.getBehavior().setPicture("Up", mouse2.getAge());
			mouse1.setLastDirection(Direction.Down);
			mouse2.setLastDirection(Direction.Up);
			break;
		case "east":
			mouse1.getBehavior().setPicture("Right", mouse1.getAge());
			mouse2.getBehavior().setPicture("Left", mouse2.getAge());
			mouse1.setLastDirection(Direction.Right);
			mouse2.setLastDirection(Direction.Left);
			break;
		case "west":
			mouse1.getBehavior().setPicture("Left", mouse1.getAge());
			mouse2.getBehavior().setPicture("Right", mouse2.getAge());
			mouse1.setLastDirection(Direction.Left);
			mouse2.setLastDirection(Direction.Right);
			break;
		}
		
	}
	
	
	/*************************		Food & Grid Methodes		**************************/
	
	/**
	 * add new food source to the grid.
	 */
	
	public void foodGeneration() {
		if(foodAddFreq <= 0 && grid.getFood().size() < 30) {
			simulationTurnAndFood(simulationNumberOfTurn);
			for(Food food : foodPosition) {
				grid.addFood(food);
				logWr.writeFoodAction(food,"generation");
			}
			foodAddFreq=foodFreq;
			foodPosition.clear();
		}
	}
	
	/**
	 * gives the number of food source to add by the frequency
	 * @return number of food to add
	 */
	
	public int foodGenerFreq() {
		int foodAdd = 0;
			switch (getParameters().getFreqFood()) {
			case 25:
				foodAdd=15;
				break;
			case 15:
				foodAdd=20;
				break;
			case 10:
				foodAdd=25;
				break;
		}
		return foodAdd;
	}
	
	
	public void adjustFoodToAdd(String simulationTurn) {		

		int foodToAdd;
		
		if(getParameters().getFreqFood()==25) {
			foodToAdd=Random.randomInt(5, 15);
		}else if(getParameters().getFreqFood()==15) {
			foodToAdd=Random.randomInt(5, 10);
		}else {
			foodToAdd=Random.randomInt(3, 6);;
		}
		
		randomFoodPosition(simulationTurn,foodToAdd);
		
	}
	
	/**
	 * random food position to add.
	 * @param simulationTurn
	 * @param foodNumber
	 */
	
	public void randomFoodPosition(String simulationTurn,int foodNumber) {
		int x,y;
		for(int i = 0 ; i<foodNumber ; i++ ) {
			
			do
			{
				 x= Random.randomInt(1,getParameters().getDimension()-2);
				 y= Random.randomInt(1,getParameters().getDimension()-2);
			}
			while(!grid.getBoxAt(x, y).getIsFree() && !grid.isFoodPosition(new Point(x,y)));
			
				Food f = BoxFactory.creatFood(x, y, simulationTurn);
				grid.getBoxAt(x, y).setGroundType(f);
				foodPosition.add(f);
		}		
	}
	
	/**
	 * random obstacle position to add.
	 * @param obstaclesNumber
	 */
	
	public void addObstaclesRandomly(int obstaclesNumber) {

		int x,y;
		Point p;
		for(int i = 0 ; i< obstaclesNumber ; i++ ) {
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

	public void simulationTurnAndFood(int simulationTurn) {
		if(simulationTurn<200) {
			adjustFoodToAdd("Start");
		}else if(simulationTurn<700) {
			adjustFoodToAdd("Midel");
		}else {
			adjustFoodToAdd("Final");
		}
	}
	
	/**
	 * delete the food sources that have expired
	 * @param food
	 */
	
	public void foodAction(Food food) {

		food.foodToConsumeTime();
		
		if(food.obsoleteFood() || food.isempty()) {
			grid.deleteFood(food);
			logWr.writeFoodAction(food,"expiration");
			int x = food.getAbscisse();
			int y = food.getOrdonne();
			grid.getBoxAt(x, y).setIsFree(true);
			grid.getBoxAt(x, y).setGroundType(BoxFactory.creatGrass(x, y, parameters.getGround()));
		}
	}
	
	/**********		others methodes		*************/
	
	/**
	 * generate the mouse names
	 * @param title
	 */
	
	public void generMiceName(String title) {
		
		URL url = getClass().getResource(title);
		String ligne;
		try {
			URLConnection ucon = url.openConnection();
			BufferedReader read = new BufferedReader(new InputStreamReader(ucon.getInputStream()));
			while((ligne=read.readLine()) != null) {
				if(ligne.contains("-"))
					boysNames.add(ligne.replace("-",""));
				else
					girlsNames.add(ligne);
			}
			read.close();
		} catch (Exception e) {

		}
	}
}