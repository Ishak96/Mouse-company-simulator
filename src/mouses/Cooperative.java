package mouses;

import java.awt.Image;

import javax.swing.ImageIcon;

public class Cooperative extends Behavior{
	
	/**********		attributs		**********/
	public final static String COOPERATIVE_PICTURES_LOCATION="/images/sourisNr/";
	private Image cooperativePicture;
	/**********		construct		**********/
	public Cooperative(int age) {
		ImageIcon img;
		if(age >=30)
			img = new ImageIcon(getClass().getResource(Cooperative.COOPERATIVE_PICTURES_LOCATION+PicturesMouses.MOUSE_UP));
		else
			img = new ImageIcon(getClass().getResource(Cooperative.COOPERATIVE_PICTURES_LOCATION+PicturesMouses.YOUNG_MOUSE_UP));
		cooperativePicture = img.getImage();
	}
	/**********		methods			**********/
				//getters
	public Image getPicture() {
		return cooperativePicture;
	}
				//setters
	public void setPicture(String mouseToWhere, int age) {
		ImageIcon img = null;
		switch(mouseToWhere) {
			case "Left":
				if(age>=30)
					img = new ImageIcon(getClass().getResource(Cooperative.COOPERATIVE_PICTURES_LOCATION
																		+PicturesMouses.MOUSE_TO_LEFT));
				else
					img = new ImageIcon(getClass().getResource(Cooperative.COOPERATIVE_PICTURES_LOCATION
																		+PicturesMouses.YOUNG_MOUSE_TO_LEFT));
				break;
			case "Up":
				if(age>=30)
					img = new ImageIcon(getClass().getResource(Cooperative.COOPERATIVE_PICTURES_LOCATION
						 												+PicturesMouses.MOUSE_UP));
				else
					img = new ImageIcon(getClass().getResource(Cooperative.COOPERATIVE_PICTURES_LOCATION
																		+PicturesMouses.YOUNG_MOUSE_UP));
				 break;
			case "Right":
				if(age>=30)
					img = new ImageIcon(getClass().getResource(Cooperative.COOPERATIVE_PICTURES_LOCATION
																		+PicturesMouses.MOUSE_TO_RIGHT));
				else
					img = new ImageIcon(getClass().getResource(Cooperative.COOPERATIVE_PICTURES_LOCATION
																		+PicturesMouses.YOUNG_MOUSE_TO_RIGHT));
				break;
			case "Down":
				if(age>=30)
					img = new ImageIcon(getClass().getResource(Cooperative.COOPERATIVE_PICTURES_LOCATION
																		+PicturesMouses.MOUSE_DOWN));
				else
					img = new ImageIcon(getClass().getResource(Cooperative.COOPERATIVE_PICTURES_LOCATION
																		+PicturesMouses.YOUNG_MOUSE_DOWN));
				break;
			case "Food":
				if(age>=30)
					img = new ImageIcon(getClass().getResource(Cooperative.COOPERATIVE_PICTURES_LOCATION
																		+PicturesMouses.MOUSE_FIND_FOOD));
				else
					img = new ImageIcon(getClass().getResource(Cooperative.COOPERATIVE_PICTURES_LOCATION
																		+PicturesMouses.YOUNG_MOUSE_FIND_FOOD));
				break;
			case "Dead1":
				img = new ImageIcon(getClass().getResource(PicturesMouses.MOUSE_DEAD_ONE));
				
				break;
			case "Dead2":
				img = new ImageIcon(getClass().getResource(PicturesMouses.MOUSE_DEAD_TWO));
				
				break;
		}
		
		cooperativePicture = img.getImage();
	}
				//others	
	public boolean transmitInformation() {
		return true;
	}
	
	public String type() {
		return "Cooperative";
	}
}