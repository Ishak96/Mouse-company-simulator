package stat;

public class Statistics {
	
	private static Statistics instance = new Statistics();
	
	private int nbrFoodSources;
	private int aliveMice;
	private int deadMice;
	private int bornedMice;
	private int exchangedtrueInfo;
	private int exchangedfalseInfo;
	private int totalExchangedInfo;
	private int pregnantMouse;
	
	private int cooperative;
	private int selfish;
	private int reciptive;
	private int nihilist;
	
	
	private Statistics () {
		init();
	}
	
	public void init() {
		this.nbrFoodSources = 0;
		this.aliveMice = 0;
		this.deadMice = 0;
		this.bornedMice = 0;
		this.exchangedtrueInfo = 0;
		this.pregnantMouse = 0;
		this.exchangedfalseInfo = 0;
		this.totalExchangedInfo = 0;
		this.cooperative = 0;
		this.selfish = 0;
		this.reciptive = 0;
		this.nihilist = 0;
	}
	
	// getters:
	
	public static Statistics getInstance() {
		return instance;
	}

	public int getAliveMice() {
		return aliveMice;
	}

	public int getDeadMice() {
		return deadMice;
	}

	public int getBornedMice() {
		return bornedMice;
	}

	public int getNbrFoodSources() {
		return nbrFoodSources;
	}

	public int getExchangedTrueInfo() {
		return exchangedtrueInfo;
	}
	
	public int getExchangedflaseInfo() {
		return exchangedfalseInfo;
	}
	
	public int getTotalExchangeInfo() {
		return totalExchangedInfo;
	}
	
	public int getPregnantMice() {
		return pregnantMouse;
	}
	
	public int getCooperative() {
		return cooperative;
	}
	
	public int getSelfish() {
		return selfish;
	}
	
	public int getReciptive() {
		return reciptive;
	}
	
	public int getNihilist() {
		return nihilist;
	}
	
	// setters
	
	public void setAliveMice(int aliveMice) {
		this.aliveMice=aliveMice;
	}

	public void setDeadMice(int deadMice) {
		this.deadMice=deadMice;
	}
	
	public void setNbrFoodSources(int nbrFoodSources) {
		 this.nbrFoodSources=nbrFoodSources;
	}
	
	public void setBornedMice(int bornedMice) {
		 this.bornedMice=bornedMice;
	}
	
	public void setPregnantMouse(int pregnantMouse) {
		this.pregnantMouse=pregnantMouse;
	}
	//  incrementers:
		
	public void incrementDeadMice() {
		deadMice++;
	}
		
	public void incrementBornedMice() {
		bornedMice++;
	}
		
	public void incrementExchangedTrueInfo() {
		exchangedtrueInfo++;
	}
	
	public void incrementExchangedFalseInfo() {
		exchangedfalseInfo++;
	}
	
	public void incrementPregnantMouse() {
		pregnantMouse++;
	}
	
	public void incrementCooperatice() {
		cooperative++;
	}
	
	public void incrementSelfish() {
		selfish++;
	}
	
	public void incrementReciptive() {
		reciptive++;
	}
	
	public void incrementNihilist() {
		nihilist++;
	}
	
	public void incrementFoodSource() {
		nbrFoodSources++;
	}
	
	//decrementers
	
	public void decrementPregnantMouse() {
		pregnantMouse--;
	}
	
	public void decrementFoodSource() {
		nbrFoodSources--;
	}
	
	public void decrementCooperatice() {
		cooperative--;
	}
	
	public void decrementSelfish() {
		selfish--;
	}
	
	public void decrementReciptive() {
		reciptive--;
	}
	
	public void decrementNihilist() {
		nihilist--;
	}
}