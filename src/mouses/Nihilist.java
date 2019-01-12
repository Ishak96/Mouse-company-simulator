package mouses;

import used.Point;

public class Nihilist extends Mouse{

	public Nihilist(Point position, Memory memory, Behavior behavior, int age, String gender, int trust) {
		super(position, memory, behavior, age, gender, trust);
	}

	@Override
	public boolean isReciptive() {
		return false;
	}

	@Override
	public boolean isNihilist() {
		return true;
	}

	@Override
	public void giveInformations(Mouse mouse,boolean information) {
		
	}

}
