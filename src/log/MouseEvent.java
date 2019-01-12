package log;

public class MouseEvent {
	private String imageName;
	private String descriptionText;
	public MouseEvent(String imageName,String descriptionText) {
		this.imageName=imageName;
		this.descriptionText=descriptionText;
	}
	public String getImageName() {
		return imageName;
	}
	public String getDescriptionText() {
		return descriptionText;
	}
}
