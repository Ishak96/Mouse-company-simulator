package used;

/**
 * This class is used to locate each compoment of the grid
 * @author Rova
 *
 */
public class Point{
	
	private int abscisse;
	private int ordonne;
	
	private final static float PowRacine = 0.5f;

	
	public Point(int abscisse, int ordonne) {
		this.abscisse = abscisse;
		this.ordonne = ordonne;
	}
	
	public void deplacer(int dx, int dy){	
		abscisse += dx;
		ordonne += dy;
	}
	public int getAbscisse() {
		return abscisse;
		
	}
	
	public void setAbscisse(int abscisse) {
		this.abscisse = abscisse;
	}
	
	public int getOrdonne() {
	
		return ordonne;
	}
	
	public void setOrdonne(int ordonne){	
		this.ordonne = ordonne;
	}
	
	public double distance (Point p1) {
		
		float x,y;
		double X,Y;
		
		
		x = this.getAbscisse() - p1.getAbscisse();

		y = this.getOrdonne() - p1.getOrdonne();
		
		X = Math.pow(x, 2);
		
		Y = Math.pow(y, 2);
		
		
			return Math.pow((X+Y),PowRacine);
		
	}
	
	public boolean equals(Object obj) {
		boolean egalite = false;
		if(obj instanceof Point) {
			egalite =( this.abscisse == ((Point)obj).getAbscisse() ) && ( this.ordonne == ((Point)obj).getOrdonne() );	
		}
		return egalite;
	}
	
	public String toString() {
		return "("+abscisse+","+ordonne+")";
	}
}
	