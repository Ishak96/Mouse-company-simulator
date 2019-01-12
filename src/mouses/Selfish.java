package mouses;

import java.awt.*;
import javax.swing.*;

public class Selfish extends Behavior{

	/**********		attributs		**********/
	public final static String SELFISH_PICTURES_LOCATION="/images/sourisIgo/";
	private Image selfishPicture;
	/**********		construct		**********/
	public Selfish(int age) {
		ImageIcon img;
		if(age>=30)
			img = new ImageIcon(getClass().getResource(Selfish.SELFISH_PICTURES_LOCATION+PicturesMouses.MOUSE_DOWN));
		else
			img = new ImageIcon(getClass().getResource(Selfish.SELFISH_PICTURES_LOCATION+PicturesMouses.YOUNG_MOUSE_DOWN));
		selfishPicture = img.getImage();
	}
	/**********		methods			**********/
				//getters
	public Image getPicture() {
		return selfishPicture;
	}
				//setters
	public void setPicture(String mouseToWhere, int age) {
		ImageIcon img = null;
		switch(mouseToWhere) {
			case "Left":
				if(age>=30)
					img = new ImageIcon(getClass().getResource(Selfish.SELFISH_PICTURES_LOCATION
																	+PicturesMouses.MOUSE_TO_LEFT));
				else
					img = new ImageIcon(getClass().getResource(Selfish.SELFISH_PICTURES_LOCATION
																	+PicturesMouses.YOUNG_MOUSE_TO_LEFT));
				break;
			case "Up":
				if(age>=30)
					img = new ImageIcon(getClass().getResource(Selfish.SELFISH_PICTURES_LOCATION
																	+PicturesMouses.MOUSE_UP));
				else
					img = new ImageIcon(getClass().getResource(Selfish.SELFISH_PICTURES_LOCATION
																	+PicturesMouses.YOUNG_MOUSE_UP));
				 break;
			case "Right":
				if(age>=30)
					img = new ImageIcon(getClass().getResource(Selfish.SELFISH_PICTURES_LOCATION
																	+PicturesMouses.MOUSE_TO_RIGHT));
				else
					img = new ImageIcon(getClass().getResource(Selfish.SELFISH_PICTURES_LOCATION
																	+PicturesMouses.YOUNG_MOUSE_TO_RIGHT));
				break;
			case "Down":
				if(age>=30)
					img = new ImageIcon(getClass().getResource(Selfish.SELFISH_PICTURES_LOCATION
																	+PicturesMouses.MOUSE_DOWN));
				else
					img = new ImageIcon(getClass().getResource(Selfish.SELFISH_PICTURES_LOCATION
																	+PicturesMouses.YOUNG_MOUSE_DOWN));
				break;
			case "Food":
				if(age>=30)
					img = new ImageIcon(getClass().getResource(Selfish.SELFISH_PICTURES_LOCATION
																	+PicturesMouses.MOUSE_FIND_FOOD));
				else
					img = new ImageIcon(getClass().getResource(Selfish.SELFISH_PICTURES_LOCATION
																	+PicturesMouses.YOUNG_MOUSE_FIND_FOOD));
				break;
			case "Dead1":
				img = new ImageIcon(getClass().getResource(PicturesMouses.MOUSE_DEAD_ONE));
				
				break;
			case "Dead2":
				img = new ImageIcon(getClass().getResource(PicturesMouses.MOUSE_DEAD_TWO));
				
				break;
		}
		
		selfishPicture = img.getImage();
	}
				//others
	public boolean transmitInformation() {
		return false;
	}
	
	@Override
	public String type() {
		return "Selfish";
	}

}
