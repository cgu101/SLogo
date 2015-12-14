import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import GUI.SlogoView;
import GUI.TurtleStatusView;
import backend.parser.Parser;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import observers.ObserverFactory;
import sharedobjects.HandleObservation;
import sharedobjects.ManipulateController;
import sharedobjects.Workspace;

public class Main extends Application {
	public static final String TITLE = "Slogo";
	private static SlogoView slogoViewFrontEnd;
	private TurtleStatusView turtleView;
	Scene scene;

	public void start(Stage stage) throws Exception {
		stage.setTitle(TITLE);
		stage.setScene(slogoViewFrontEnd.getScene());
		stage.setResizable(false);
		stage.sizeToScene();
		stage.show();
		turtleView = new TurtleStatusView();
	}

	public static void main(String[] args) {
		ObserverFactory observerFactory = new ObserverFactory();
		slogoViewFrontEnd = new SlogoView(observerFactory.getGuiCanvas(), observerFactory.getGuiTurtleGroup());
		observerFactory.createObserversWithGUIDependancies(slogoViewFrontEnd);

		try {
			Workspace currWorkspace = new Workspace();
			ManipulateController manipulateController = new ManipulateController(currWorkspace);
			Parser parser = new Parser(manipulateController);
			List<Observable> observables = new ArrayList<Observable>();
			observables.addAll(currWorkspace.getObservables());
			observables.addAll(slogoViewFrontEnd.getObservables());
			List<Observer> observers = new ArrayList<Observer>();
			observers.addAll(observerFactory.getObservers());
			observers.add(parser);
			HandleObservation.handleObservers(observables, observers);
			currWorkspace.startWorkspace();
		} catch (Exception e) {
			e.printStackTrace();
			slogoViewFrontEnd.showError(e);
		}
		launch(args);
	}
}
