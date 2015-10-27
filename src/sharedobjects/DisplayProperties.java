/**
 * 
 */
package sharedobjects;

import java.util.HashMap;
import java.util.Map;
import java.util.Observable;
import java.util.ResourceBundle;
import exceptions.WontImplementException;
import javafx.scene.paint.Color;

/**
 * @author loganrooper
 *
 */
public class DisplayProperties extends Observable {
	private static final String DEFAULT_GUI_RESOURCE = "GUI.view";
	protected static ResourceBundle myResource;
	private Map<Integer, Color> colorPalette;
	private int myPenColor;
	private double penThickness;
	private String penState;
	private Color bgColor;
	private Boolean penDown;

	public DisplayProperties() {
		// Defaults
		myResource = ResourceBundle.getBundle(DEFAULT_GUI_RESOURCE);
		myPenColor = 0; // Color.valueOf(myResource.getString("defaultPenColor"));
		penThickness = Integer.parseInt(myResource.getString("defaultPenThickness"));
		setPenState(myResource.getString("defaultPenState"));
		bgColor = Color.valueOf(myResource.getString("defaultBackgroundColor"));
		colorPalette = new HashMap<Integer, Color>();
		createDefaultPalette();
		setPenDown(true);
		this.setChanged();
	}

	public void setPenColor(int index) {
		myPenColor = index;
		penUpdate();
	}

	public Color getPenColor() { 
		return colorPalette.get(myPenColor);
	}

	public void setPenThickness(double t) {
		penThickness = t;
		penUpdate();
	}

	public double getPenThickness() {
		return penThickness;
	}

	public String getPenState() {
		return penState;
	}

	public void setPenState(String state) {
		this.penState = state;
		penUpdate();
	}

	private void createDefaultPalette() {
		String[] defColors = myResource.getString("defaultPalette").split(",");
		for (int i = 0; i < defColors.length; i++) {
			colorPalette.put(i, Color.valueOf(defColors[i]));
		}
		paletteUpdate();
	}

	public void newPaletteColor(int index, double r, double g, double b) {
		Color color = new Color(r, g, b, .99);
		colorPalette.put(index, color);
		paletteUpdate();
	}
	
	public Map<Integer, Color> getPalette() {
		return colorPalette;
	}

	public Color getPaletteColor(double index) {
		 Color t = colorPalette.get((int) index);
		 return t;
	}

	public void setBgColor(int index) {
		bgColor = colorPalette.get(index);
		bgChange();
	}

	public Color getBgColor() {
		return bgColor;
	}

	private void penUpdate() {
		this.setChanged();
		notifyObservers("pen");
	}

	private void bgChange() {
		this.setChanged();
		notifyObservers("bg");
	}

	private void paletteUpdate() {
		this.setChanged();
		notifyObservers("palette");
	}
	
	public void clear() {
		this.setChanged();
		notifyObservers("clear");
	}

	public Boolean getPenDown() {
		return penDown;
	}

	public void setPenDown(Boolean penDown) {
		this.penDown = penDown;
	}

	public double getPenColorID() {
		return myPenColor;
	}

	
	public void setPenShape(int index) {
		throw new WontImplementException();	
	}
	
	public double getPenShape() {
		throw new WontImplementException();
   }
}
