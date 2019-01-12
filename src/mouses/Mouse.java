package mouses;

import engine.Simulation;
import log.LogWriter;
import stat.Statistics;
import used.Direction;
import used.GenerateGameMode;
import used.Point;
import used.Random;
import used.Vision;

/**
 * This class represents each mouse present in the grid.
 * 
 * @author anis,ishak
 */
public abstract class Mouse {
	
	/**********		attribut		**********/
	private Point position;
	private String name;
	private String gender;
	private Memory memory;
	private Behavior behavior;
	private Vision vision;
	private Direction lastDirection;
	private boolean dead;
	private int life;
	private int age;
	private boolean isEating;
	private int pregnancyTime;
	private boolean isPregnant;
	private boolean takeControle = false;
	private boolean isTalking;
	private boolean meet;
	private boolean fakePosition = false;
	private int talkTime;
	private int trust;
	/**********		construct		**********/
	
	/**
	 * The constructor initializes the mouse.
	 * @param position position of the mouse
	 * @param memory the memory of the mouse
	 * @param behavior his behavior
	 * @param age age of the mouse
	 * @param gender gender of mouse
	 * @param trust the degree of trust
	 */
	
	public Mouse(Point position, Memory memory, Behavior behavior, int age, String gender, int trust) {
		this.position = position;
		this.gender = gender;
		this.name = mouseName();
		this.memory = memory;
		this.behavior = behavior;
		lastDirection=Direction.randomDirection();
		dead = false;
		isEating = false;
		life=GenerateGameMode.mouseLife();
		vision = new Vision(3);
		pregnancyTime=50;
		this.age=age;
		isPregnant=false;
		isTalking = false;
		meet = false;
		talkTime = 3;
		this.trust = trust;
	}
	
	/**********		methods		**********/
				//getters
	
	public int getTrust() {
		return trust;
	}
	
	public boolean getTakeControle() {
		return takeControle;
	}
	
	public int getTalkTime() {
		return talkTime;
	}
	
	public Point getPosition() {
		return position;
	}
	
	public String getName() {
		return name;
	}
	
	public Memory getMemory() {
		return memory;
	}
	
	public Behavior getBehavior() {
		return behavior;
	}
	
	public Direction getLastDirection() {
		return lastDirection;
	}
	
	public boolean isDead() {
		return dead;
	}
	
	public int getLife() {
		return life;
	}
	
	public int getAge() {
		return age;
	}
	
	public Vision getVision() {
		return vision;
	}
	
	public boolean getIsEating() {
		return isEating;
	}
	
	public int getPregancyTime() {
		return pregnancyTime;
	}
	
	public boolean isPragant() {
		return isPregnant;
	}
	
	public String getGender() {
		return gender;
	}
	
	public boolean getIsTalking() {
		return isTalking;
	}
	
	public boolean meet() {
		return meet;
	}
	
	public boolean fakePosition() {
		return fakePosition;
	}
				//setters
	
	public void setTrust(int trust) {
		this.trust = trust;
	}
	
	public void setTakeControle(boolean takeControle) {
		this.takeControle = takeControle;
	}
	
	public void setTalkTime(int talkTime) {
		this.talkTime = talkTime;
	}
	
	public void setPosition(Point position) {
		this.position = position;
	}
	
	public void setBehavior(Behavior behavior) {
		this.behavior = behavior;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setLastDirection(Direction lastDirection) {
		this.lastDirection=lastDirection;
	}
	
	public void killMouse() {
		if(isNihilist())
			Statistics.getInstance().decrementNihilist();
		else
			Statistics.getInstance().decrementReciptive();
		
		if(getBehavior().type().equals("Selfish"))
			Statistics.getInstance().decrementSelfish();
		else
			Statistics.getInstance().decrementCooperatice();
		
		dead=true;
	}
	
	public void setLife(int life) {
		this.life=life;
	}
	
	public void setIsEating(boolean isEating) {
		this.isEating=isEating;
	}
	
	public void setIsPragnant(boolean isPregnant) {
			this.isPregnant=isPregnant;
	}
	
	public void setPregnancyTime(int pregnancyTime) {
		this.pregnancyTime=pregnancyTime;
	}
	
	public void setIsTalking(boolean isTalking) {
		this.isTalking=isTalking;
	}
	
	public void setMeet(boolean meet) {
		this.meet = meet;
	}
	
	public void setFakePosition(boolean fakePosition) {
		this.fakePosition = fakePosition;
	}
				//others
	
	public void decrementTrust() {
		if(trust == 0) {
			if(getBehavior().type().equals("Cooperative")) {
				setBehavior(new Selfish(age));
				Statistics.getInstance().decrementCooperatice();
				Statistics.getInstance().incrementSelfish();
			}
			trust = 5;
		}
		trust--;
	}
	
	public void incrementTrust() {
		if(!behavior.type().equals("Cooperative")) {
			setBehavior(new Cooperative(age));
			Statistics.getInstance().decrementSelfish();
			Statistics.getInstance().incrementCooperatice();
		}
		trust++;
	}
	
	public void talkWith(Mouse mouse) {
		LogWriter.getInstance().writecommunication(mouse, this);
		if(mouse.getBehavior().transmitInformation()) {
			Statistics.getInstance().incrementExchangedTrueInfo();
			giveInformations(mouse,true);
		}else {
			Statistics.getInstance().incrementExchangedFalseInfo();
			giveInformations(mouse, false);
		}
	}
	
	public void talk() {
		talkTime--;
		if(talkTime<=0) {
			isTalking = false;
			meet = false;
			talkTime = 3;
		}
	}
	
	public String mouseName() {
		int index = Random.randomInt(0,39);
		if(!Simulation.boysNames.isEmpty() && !Simulation.girlsNames.isEmpty())
			if(gender.equals("Male"))
				return Simulation.boysNames.get(index);
			else
				return Simulation.girlsNames.get(index);
		else
			return"mouse";
	}
	public void mouseBecomBlind() {
		vision.setHorizon(vision.getHorizon()-1);
	}
	
	public void mouseEat(){
		life+=GenerateGameMode.eatLifePlus();
		mouseStockFoodLocation();
		setIsEating(true);
	}
	
	/**
	 * evolution of the mouse
	 */
	
	public void mousesEvolu() {

		if((life<=0 || age>=250)) {
			
			if(isPregnant)
				pregnancyTime = 0;
			
			life = 0;
			killMouse();
			
		}else {
			if(!isEating)
				life-=GenerateGameMode.deminuLife();
			age++;
		}
		
		if (isPregnant) {
			pregnancyTime--;
		}
		
	}
	
	public boolean bornBabyMouse() {
		return pregnancyTime<=0;
	}
	
	/**
	 * check if a mouse needs to stay to eat
	 */
	
	public boolean mouseStayInPositionToEat() {
		if(!behavior.transmitInformation()) {
			return (life<60);
		}
		else {
			return (life<40);
		}
	}
	
	public void mouseStockFoodLocation() {
		memory.addFoodLocation(position);
	}
	
	/**
	 *this method moves the mouse to a food source if the mouse is hunger.
	 */

	public void moveMouseToFoodLocation() {
		int xMouse = position.getAbscisse();
		int yMouse = position.getOrdonne();
		int xFood = memory.getFoodToEat().getAbscisse();
		int yFood = memory.getFoodToEat().getOrdonne();
		Point nextPos;
		if(xMouse < xFood) {
			if(yMouse == yFood ) {
				lastDirection = Direction.Right;
				nextPos = new Point(position.getAbscisse() + 1, position.getOrdonne());
				if(memory.getObstacleLocation().contains(nextPos)) {
					lastDirection = Direction.Down;
				}
			}
			else {
				if(yMouse < yFood) {		
					lastDirection = Direction.Down;
					nextPos = new Point(position.getAbscisse() , position.getOrdonne()+1);
					if(memory.getObstacleLocation().contains(nextPos)){
						lastDirection = Direction.Right;	
					}
				}
				else {
					lastDirection = Direction.Right;
					nextPos = new Point(position.getAbscisse()+1, position.getOrdonne());
					if(memory.getObstacleLocation().contains(nextPos)) {
						lastDirection = Direction.Up;
					}
				}
			}
		}
		else{
			if(xMouse > xFood) {
				if(yMouse == yFood ) {
					lastDirection = Direction.Left;
					nextPos = new Point(position.getAbscisse()-1, position.getOrdonne());
					if(memory.getObstacleLocation().contains(nextPos)) {
						lastDirection = Direction.Up;
					}
				}
				else {
					if(yMouse < yFood) {
						lastDirection = Direction.Left;
						nextPos = new Point(position.getAbscisse()-1, position.getOrdonne());
						if(memory.getObstacleLocation().contains(nextPos)) {
							lastDirection = Direction.Down;
						}	
					}
					else {
						lastDirection = Direction.Up;
						nextPos = new Point(position.getAbscisse(), position.getOrdonne()-1);
						if(memory.getObstacleLocation().contains(nextPos)) {
							lastDirection = Direction.Left;							
						}
					}
				}
			}
			else
			{
				if(yMouse < yFood) {
					lastDirection = Direction.Down;
					nextPos = new Point(position.getAbscisse(), position.getOrdonne()+1);
					if(memory.getObstacleLocation().contains(nextPos)){
						lastDirection = Direction.Left;
					}					
				}
				if(yMouse > yFood) {
					lastDirection = Direction.Up;
					nextPos = new Point(position.getAbscisse(), position.getOrdonne()-1);
					if(memory.getObstacleLocation().contains(nextPos)) {
						lastDirection = Direction.Right;						
					}
				}
			}
		}
	}

	public abstract void giveInformations(Mouse mouse,boolean information);
	public abstract boolean isReciptive();
	public abstract boolean isNihilist();
}