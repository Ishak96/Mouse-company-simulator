package mouses;

import log.LogWriter;
import stat.Statistics;
import used.GenerateGameMode;
import used.Point;
import used.Random;

public class MiceFactory {
	
	private static LogWriter logWr = LogWriter.getInstance();
	
	public static Mouse creatReciptiveMouse(Point position, Memory memory, Behavior behavior, int age, String gender, int trust) {
		Mouse mouse = new Receptive(position, memory, behavior, age, gender, trust);
		logWr.writeMouseAction(mouse,"born");
		return mouse;
	}
	
	public static Mouse creatNehilistMouse(Point position, Memory memory, Behavior behavior, int age, String gender, int trust) {
		Mouse mouse = new Nihilist(position, memory, behavior, age, gender, trust);
		logWr.writeMouseAction(mouse,"born");
		return mouse;
	}
	
	public static Mouse creatRandomType(Point position, int age) {
		
		int type = Random.randomInt(1, 2);
		int comportement = Random.randomInt(1, 2);
		int randGender=Random.randomInt(1,2);
		
		Mouse m=null;
		
		if(type%2 == 0) {
			Statistics.getInstance().incrementReciptive();
			if(comportement%2 == 0) {
				Statistics.getInstance().incrementCooperatice();
				m = new Receptive(position, new Memory(), new Cooperative(age), age, GenerateGameMode.genderOfMice(randGender), 10);
			}
			else {
				Statistics.getInstance().incrementSelfish();
				m = new Receptive(position, new Memory(), new Selfish(age), age, GenerateGameMode.genderOfMice(randGender), 5);
			}
		}
		else {
			Statistics.getInstance().incrementNihilist();
			if(comportement%2 == 0) {
				Statistics.getInstance().incrementCooperatice();
				m = new Nihilist(position, new Memory(), new Cooperative(age), age, GenerateGameMode.genderOfMice(randGender), 10);
			}
			else {
				Statistics.getInstance().incrementSelfish();
				m = new Nihilist(position, new Memory(), new Selfish(age), age, GenerateGameMode.genderOfMice(randGender), 5);
			}
		}
		logWr.writeMouseAction(m,"born");
		return m;
	}

}
