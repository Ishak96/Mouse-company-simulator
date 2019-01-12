package log;

import engine.Simulation;
import grid.Food;
import mouses.Mouse;

/**
 * This class allows to record event of the simulation.
 *
 * @author Rassem
 */

public class LogWriter {
	
	private static LogWriter instance = new LogWriter();
	private StringBuffer logBuffer = new StringBuffer();
	
	private LogWriter() {}
	
	public static LogWriter getInstance() {
		return instance;
	}
	
	public String getInfos() {
		return logBuffer.toString();
	}
	
	public void clear() {
		logBuffer.delete(0,logBuffer.length());
	}
	
	// writers:
	
	public void writeInfos(String infos) {
		logBuffer.append(infos+"\n");
	}
		
	public void writeMouseAction(Mouse mouse,String action) {
		int turn = Simulation.getSimulationTurn();
		String pos = "("+mouse.getPosition().getAbscisse()+","+mouse.getPosition().getOrdonne()+")";
		switch(action) {
			case "eat":
				writeInfos(" \""+mouse.getName()+"\" eat in the box "+pos);
				mouse.getMemory().addEvent("eat"," In round "+turn+", I eated in the box "+pos);
				break;
			case "born":
				writeInfos(" \""+mouse.getName()+"\" was borned in the box "+pos);
				mouse.getMemory().addEvent("none","I was borned in the round "+turn+", box "+pos);
				break;
			case "dead":
				writeInfos(" \""+mouse.getName()+"\" is dead in the box "+pos);
				mouse.getMemory().addEvent("dead","In the round "+turn+", \""+mouse.getName()+"\"\n is dead in the box "+pos);
				break;
			case "pregnant":
				writeInfos(" \""+mouse.getName()+"\" is pregnant ");
				mouse.getMemory().addEvent("none"," I was pregnant in the box "+pos+" ,round "+turn);
				break;
			case "obstacle":
				writeInfos(" \""+mouse.getName()+"\" has found ");
				mouse.getMemory().addEvent("obstacle"," In round "+turn+", I have found an barrier in the box "+pos);
				break;
		}	
	}
	
	public void writePregnant(Mouse mouse) {
		int turn = Simulation.getSimulationTurn();
		String pos = "("+mouse.getPosition().getAbscisse()+","+mouse.getPosition().getOrdonne()+")";
		writeInfos(" \""+mouse.getName()+"\" is pregnant in the box "+pos);
		mouse.getMemory().addEvent("walk"," I was pregnant in the box "+pos+" ,round "+turn);
	}
	
	public void writeMove(Mouse mouse,String direction) {
		int turn = Simulation.getSimulationTurn();
		writeInfos(" \""+mouse.getName()+"\" mouves to "+direction);
		mouse.getMemory().addEvent("walk"," In the round "+turn+", I moved to "+direction);	
	}
	
	public void writecommunication(Mouse mouse1,Mouse mouse2) {
		String pos = "("+mouse1.getPosition().getAbscisse()+","+mouse1.getPosition().getOrdonne()+")";
		writeInfos(" \""+mouse1.getName()+"\" communicationmunicate with his friend \""+mouse1.getName()+"\""+" in the box "+pos);		
		mouseSpeech(mouse1,mouse2);
		mouseSpeech(mouse2,mouse1);
	}
	private void mouseSpeech(Mouse transmiter,Mouse receiver) {
		
		int turn = Simulation.getSimulationTurn();
		String pos = "("+transmiter.getPosition().getAbscisse()+","+transmiter.getPosition().getOrdonne()+")";
		String selfishSpeech = " I met "+transmiter.getName()+" in the box "+pos+", round "+turn+ ",I didn't get any information from this mouse";
		String cooperativeSpeech = " I met "+transmiter.getName()+" in the box "+pos+", round "+turn+" she gives me some informations about food location ";
		
		if(transmiter.getBehavior().transmitInformation()) {
			receiver.getMemory().addEvent("com",cooperativeSpeech);
		}else {
			receiver.getMemory().addEvent("com",selfishSpeech);
		}
		
	}
	
	
	public void writeFoodAction(Food food,String action) {
		String pos = "("+food.getAbscisse()+","+food.getOrdonne()+")";
		switch(action) {
			case "generation":writeInfos(" a food source is generated in the box "+pos);break;
			case "expiration":writeInfos(" a food source is expirated in the box "+pos);break;
		}
	}
	
		
}
