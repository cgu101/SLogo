package sharedobjects;

import java.util.LinkedList;
import java.util.List;
import java.util.Observable;

import responses.Response;
import responses.Success;

public class Workspace {
	private DisplayProperties displayProp;
	private Variables vars;
	private Functions funcs;
	private Response response;
	private TurtleContainer turtleContainer;

	public Workspace() {
		displayProp = new DisplayProperties();
		vars = new Variables();
		funcs = new Functions();
		response = new Success("");
		turtleContainer = new TurtleContainer();
	}

	public void startWorkspace() {
		turtleContainer.notifyObservers("turtle");
	}
	
	public Variables getVars() {
		return vars;
	}

	public Functions getFuncs() {
		return funcs;
	}

	public Response getResponse() {
		return response;
	}

	public TurtleContainer getTurtleContainer() {
		return turtleContainer;
	}

	public DisplayProperties getDisplayProp() {
		return displayProp;
	}

	public void setDisplayProp(DisplayProperties displayProp) {
		this.displayProp = displayProp;
	}

	public List<Observable> getObservables() {
		List<Observable> observables = new LinkedList<Observable>();
		observables.add(turtleContainer);
		observables.add(displayProp);
		observables.add(response);
		observables.add(funcs);
		observables.add(vars);
		return observables;
	}

	public void setCurrentResponse(Response s) {
		response.updateValue(s.toString());
		response.notifyObservers("Response");
	}

	public double executeOnAllActiveTurtles(ITurtleLambda lambda) {
		return turtleContainer.executeOnAllActiveTurtles(lambda);
	}

	public double executeOnCurrentTurtle(ITurtleLambda lambda) {
		return turtleContainer.executeOnCurrentTurtle(lambda);
	}

	public void setTempTurtles(List<Turtle> turtles) {
		turtleContainer.setTempTurtles(turtles);

	}

	public void tellDuvall2Dance() {
		turtleContainer.tellDuvall2Dance();
	}

	public double executeOnAllTurtles(ITurtleLambda lambda) {
		return turtleContainer.executeOnAllTurtles(lambda);
	}

	public void changeMapType(MapType m) {
		turtleContainer.changeMapType(m);
	}

}