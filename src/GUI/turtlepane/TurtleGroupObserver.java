package GUI.turtlepane;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.ResourceBundle;

import javafx.animation.FadeTransition;
import javafx.animation.ParallelTransition;
import javafx.animation.PathTransition;
import javafx.animation.RotateTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.CubicCurveTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.util.Duration;
import sharedobjects.Turtle;
import sharedobjects.TurtleContainer;

/**
 * @author Inan
 *
 */
public class TurtleGroupObserver extends Group implements Observer{

	private static final String DEFAULT_GUI_RESOURCE = "GUI.view";

	private List<Integer> myTurtleIDs;
	private Image myTurtleImage;
	private double width,height;
	private double inactiveOpacity;
	private ResourceBundle myResource;

	public TurtleGroupObserver () {
		super();
		this.myResource = ResourceBundle.getBundle(DEFAULT_GUI_RESOURCE);
		this.myTurtleImage = new Image(getClass().getClassLoader().getResourceAsStream(myResource.getString("defaultTurtle")));
		this.myTurtleIDs = new ArrayList<Integer>();
		width = Integer.parseInt(myResource.getString("canvasWidth"));
		height = Integer.parseInt(myResource.getString("canvasHeight"));
		inactiveOpacity = Double.parseDouble(myResource.getString("inactiveTurtleOpacity"));
	}

	public void setImage(Image newImage){
		this.myTurtleImage = newImage;
	}

	public void clear(){
		this.getChildren().removeAll();
	}

	public void changeOpacity(double value){
		this.setOpacity(value);
	}

	private void drawTurtle(Turtle turtle, List<Turtle> activeTurtles) {
		myTurtleIDs.add(turtle.getID());
		ImageView turtleImage = new ImageView(myTurtleImage);
		turtleImage.setX(turtle.getPosition()[0]+width/2.0-(myTurtleImage.getWidth()/2.0));
		turtleImage.setY(turtle.getPosition()[1]+height/2.0-(myTurtleImage.getHeight()/2.0));
		turtleImage.setVisible(turtle.isShowing());
		turtleImage.setRotate(90-turtle.getHeading());
		if(!activeTurtles.contains(turtle)){
			turtleImage.setOpacity(inactiveOpacity);
		}
		this.getChildren().add(turtleImage);
	}

	@Override
	public void update (Observable o, Object arg) {
		if(((String)arg).equals("dancingDuvall")){
			this.getChildren().clear();
			dance(o, arg);
		}
		else{
			TurtleContainer turtleContainer = (TurtleContainer) o;
			for(Turtle t: turtleContainer.getAllTurtles().values()){
				Iterator<Integer> it = myTurtleIDs.iterator();
				while(it.hasNext()){
					if(it.next() == t.getID()){
						this.getChildren().remove(myTurtleIDs.indexOf(t.getID()));
						it.remove();
					}
				}
				drawTurtle(t, turtleContainer.getActiveTurtles());
			}
		}
	}

	//	-------------------------- EASTER EGG --------------------------

	private void dance(Observable o, Object arg) {
		Image d = new Image(getClass().getClassLoader().getResourceAsStream(myResource.getString("dancingDuvall")));
		ImageView duvall = new ImageView(d);
		this.getChildren().add(duvall);
		makeHimDance(duvall);

	}

	private void makeHimDance(ImageView duvall) {
		System.out.println("sup");
		FadeTransition ft = new FadeTransition(Duration.millis(3000), duvall);
		ft.setFromValue(1.0);
		ft.setToValue(0.1);
		ft.setCycleCount(Timeline.INDEFINITE);
		ft.setAutoReverse(true);
		ft.play();

		Path path = new Path();
		path.getElements().add(new MoveTo(width,height));
		path.getElements().add(new CubicCurveTo(380, 0, 380, 120, 200, 120));

		path.getElements().add(new CubicCurveTo(0, 120, 0, 240, 380, 240));
		PathTransition pathTransition = new PathTransition();
		pathTransition.setDuration(Duration.millis(4000));
		pathTransition.setPath(path);
		pathTransition.setNode(duvall);
		pathTransition.setOrientation(PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT);
		pathTransition.setCycleCount(Timeline.INDEFINITE);
		pathTransition.setAutoReverse(true);
		pathTransition.play();

		FadeTransition fadeTransition = 
				new FadeTransition(Duration.millis(3000), duvall);
		fadeTransition.setFromValue(1.0f);
		fadeTransition.setToValue(0.3f);
		fadeTransition.setCycleCount(2);
		fadeTransition.setAutoReverse(true);
		TranslateTransition translateTransition =
				new TranslateTransition(Duration.millis(2000), duvall);
		translateTransition.setFromX(50);
		translateTransition.setToX(350);
		translateTransition.setCycleCount(2);
		translateTransition.setAutoReverse(true);
		RotateTransition rotateTransition = 
				new RotateTransition(Duration.millis(3000), duvall);
		rotateTransition.setByAngle(180f);
		rotateTransition.setCycleCount(4);
		rotateTransition.setAutoReverse(true);
		ScaleTransition scaleTransition = 
				new ScaleTransition(Duration.millis(2000), duvall);
		scaleTransition.setToX(2f);
		scaleTransition.setToY(2f);
		scaleTransition.setCycleCount(2);
		scaleTransition.setAutoReverse(true);

		ParallelTransition parallelTransition = new ParallelTransition();
		parallelTransition.getChildren().addAll(
				fadeTransition,
				translateTransition,
				rotateTransition,
				scaleTransition
				);
		parallelTransition.setCycleCount(Timeline.INDEFINITE);
		parallelTransition.play();

	}

}