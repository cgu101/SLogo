package sharedobjects;

public class Turtle {
	private int ID;
	private double[] position;
	private double[] oldPosition;
	private double heading;
	private boolean showing;
	private boolean active;
	private double width;
	private double height;
	private MapType mapType;
	
	public Turtle(int id){
		setID(id);
		position = new double[]{0.0,0.0};
		oldPosition = new double[]{0.0,0.0};
		heading = 90; 
		showing = true;
		active = true;
		mapType = MapType.WINDOW;
		
		width = PropertiesManager.getDouble("canvasWidth");
		height = PropertiesManager.getDouble("canvasHeight");
	}

	public void setID(int i){
		ID = i;
	}
	
	public int getID(){
		return ID;
	}
	
	public void setPosition(double[] p){
	        oldPosition = position;
	        position = mapType.getNextPosition(p, width, height);
	}

	public double[] getPosition(){
		return position;
	}
	
	public double[] getOldPosition(){
            return oldPosition;
        }

	public void setHeading(double h){
		heading = h;
	}
	
	public double getHeading(){
		return heading;
	}
	
	public void show(){
		showing = true;
	}
	
	public void hide(){
		showing = false;
	}
	
	public boolean isShowing(){
		return showing;
	}
	
	public boolean isActive(){
		return active;
	}
	
	public void activate(){
		active = true;
	}
	
	public void deativate(){
		active = false;
	}
	
	public void setMapType(MapType m){
		mapType = m;
	}
}
