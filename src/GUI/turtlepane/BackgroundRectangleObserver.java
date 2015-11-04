package GUI.turtlepane;

import java.util.Observable;
import java.util.Observer;
import java.util.ResourceBundle;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import sharedobjects.DisplayProperties;

/**
 * @author Inan
 *
 */
public class BackgroundRectangleObserver extends Rectangle implements Observer{

    private static final String TURTLE_RESOURCE_PACKAGE = "GUI.view";
    protected static ResourceBundle myResource = ResourceBundle.getBundle(TURTLE_RESOURCE_PACKAGE);

    private Color myBackgroundColor;

    public BackgroundRectangleObserver() {
        super(Integer.parseInt(myResource.getString("canvasWidth")), Integer.parseInt(myResource.getString("canvasHeight")));
        myBackgroundColor = Color.valueOf(myResource.getString("defaultBackgroundColor"));
        this.setFill(myBackgroundColor);
    }

    public void setBackgroundColor(String color){
        this.myBackgroundColor = Color.valueOf(color);
        this.setFill(myBackgroundColor);
    }

    @Override
    public void update(Observable o, Object arg) {
//        if (((String) arg).equals("turtle")) {
//            DisplayProperties t = (DisplayProperties) o;                // Do we need this if case ??
//            this.setFill(t.getBgColor());
        if (((String) arg).equals("bg")) {
            DisplayProperties t = (DisplayProperties) o;
            setBackgroundColor(t.getBgColor().toString());
        }
    }
}


