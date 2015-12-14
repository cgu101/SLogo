package GUI.turtlepane;

import java.util.HashMap;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;
import java.util.ResourceBundle;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import sharedobjects.DisplayProperties;
import sharedobjects.Turtle;
import sharedobjects.TurtleContainer;

public class CanvasObserver extends Canvas implements Observer {

	private GraphicsContext myGC;
	private static final String DEFAULT_GUI_RESOURCE = "GUI.view";
	protected static ResourceBundle myResource;
	int myWidth, myHeight;
	private Color myPenColor;
	private boolean isDrawing;
	private Map<Integer, Color> colorPalette;

	public CanvasObserver() {
		super();
		myResource = ResourceBundle.getBundle(DEFAULT_GUI_RESOURCE);
		myWidth = Integer.parseInt(myResource.getString("canvasWidth"));
		myHeight = Integer.parseInt(myResource.getString("canvasHeight"));
		this.setWidth(myWidth);
		this.setHeight(myHeight);
		setDrawing(true);
		colorPalette = new HashMap<Integer, Color>();
		myGC = this.getGraphicsContext2D();
		myGC.setFill(Color.TRANSPARENT);
		myGC.fillRect(0, 0, myWidth, myHeight);
		setDefaultPenAndStroke();
	}

	private void setPalette(Map<Integer, Color> colorPalette) {
		this.colorPalette = colorPalette;
	}

	private void setDefaultPenAndStroke() {
		setPenColor(myResource.getString("defaultPenColor"));
		setLineType(myResource.getString("defaultPenState"));
		setPenWidth(Double.parseDouble(myResource.getString("defaultPenThickness")));
		myGC.setStroke(myPenColor);
	}

	public void clear() {
		myGC.clearRect(0, 0, myWidth, myHeight);
	}

	public void setPenColor(String newPenColor) {
		myPenColor = Color.valueOf(newPenColor);
		myGC.setStroke(myPenColor);
	}

	public void drawLine(double[] startLoc, double[] endLoc) {
		if (isDrawing()) {
			myGC.strokeLine(startLoc[0] + myWidth / 2.0, startLoc[1] + myHeight / 2.0, endLoc[0] + myWidth / 2.0,
					endLoc[1] + myHeight / 2.0);
		}
	}

	public void penUpDown() {
		System.out.println("check");
		myGC.clearRect(0, 0, myWidth, myHeight);
	}

	public void setLineType(String line) {
		if (line.equalsIgnoreCase("solid")) {
			myGC.setLineWidth(1);
		} else if (line.equalsIgnoreCase("dashed")) {
			myGC.setLineDashOffset(5f);
			myGC.setLineDashes(5f, 5f);
		} else if (line.equalsIgnoreCase("dotted")) {
			myGC.setLineDashOffset(4f);
			myGC.setLineDashes(2f, 2f, 2f, 2f);
		}
	}

	public void setPenWidth(Double penWidth) {
		myGC.setLineWidth(penWidth);
	}

	public double getPenWidth(){
		return myGC.getLineWidth();
	}

	@Override
	public void update(Observable o, Object arg) {

		if(((String)arg).equals("dancingDuvall")){
			clear();
		}

		if (((String) arg).equals("turtle")) {
			TurtleContainer turtleContainer = (TurtleContainer) o;
			for(Turtle t: turtleContainer.getAllTurtles().values()){
				drawLine(t.getOldPosition(), t.getPosition());
			}
		} else if (((String) arg).equals("pen")) {
			DisplayProperties t = (DisplayProperties) o;
			setPenColor(t.getPaletteColor(t.getPenColorID()).toString());
			setLineType(t.getPenState());
			setPenWidth(t.getPenThickness());
			setDrawing(t.getPenDown());
		} else if (((String) arg).equals("palette")) {
			DisplayProperties t = (DisplayProperties) o;
			setPalette(t.getPalette());
		} else if (((String) arg).equals("clear")) {
			clear();
		}
	}

	public boolean isDrawing() {
		return isDrawing;
	}

	public void setDrawing(boolean isDrawing) {
		this.isDrawing = isDrawing;
	}
}