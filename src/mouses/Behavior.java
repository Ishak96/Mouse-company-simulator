package mouses;

import java.awt.Image;

public abstract class Behavior {

	public abstract boolean transmitInformation();
	public abstract Image getPicture();
	public abstract void setPicture(String mouseToWhere,int age);
	public abstract String type();
}
