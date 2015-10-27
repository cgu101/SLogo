package sharedobjects;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import backend.node.Node;
import datatransferobjects.ParsedCommandsTransferObject;
import javafx.scene.paint.Color;
import responses.Response;
import responses.Success;

public class Workspace {
	Map<Integer, Turtle> allTurtles;
	List<Turtle> activeTurtles;
	List<Turtle> tempTurtles;
	List<Color> colorPalette;
	ParsedCommands userInputsObservable = new ParsedCommands(); // need to change this to two different objects
	//Variables userInputVariableObservable = new Variables();
	//Functions userInputFunctionsObservable = new Functions();
	DisplayProperties displayProp;
	private Response response;

	public Workspace() {
		allTurtles = new HashMap<Integer, Turtle>();
		allTurtles.put(1, new Turtle(1));
		activeTurtles = new LinkedList<Turtle>();
		activeTurtles.add(allTurtles.get(1));
		tempTurtles = new LinkedList<Turtle>();
		displayProp = new DisplayProperties();
		response = new Success("");
	}

	public Turtle addNewTurtle(int id) {
		Turtle turt = new Turtle(id);
		allTurtles.put(id, turt);
		return turt;
	}

	public Map<Integer, Turtle> getAllTurtles() {
		return allTurtles;
	}

	public List<Turtle> getActiveTurtles() {
		return activeTurtles;
	}

	public void setActiveTurtles(List<Turtle> activeTurtles) {
		this.activeTurtles = activeTurtles;
	}

	public void setTempTurtles(List<Turtle> tempTurtles) {
		this.tempTurtles = tempTurtles;
	}

	public List<Turtle> getTempTurtles() {
		return tempTurtles;
	}

	// ****Commands and Variables Manipulation*****//
	public void addVariable(String v, Node n) {
		userInputsObservable.addVariable(v, n);
//		userInputsVariableObservable.addVariable(v,n);
		ParsedCommandsTransferObject uito = new ParsedCommandsTransferObject(false, v + " = " + n.getDoubleValue());
		userInputsObservable.notifyObservers(uito);
//		userInputsVariableObservable.notifyObservers(uito);
	}

	public Node getVariable(String v) {
		return userInputsObservable.getVariable(v);
//		userInputsVariableObservable.getVariable(v);

	}

	public void addCommand(String userInput, String c, Node n) {
		userInputsObservable.addCommand(c, n);
		ParsedCommandsTransferObject uito = new ParsedCommandsTransferObject(true, userInput);
		userInputsObservable.notifyObservers(uito);
	}

	public void addCommand(String c, Node n) {
		userInputsObservable.addCommand(c, n);
	}

	public Node getCommand(String c) {
		return userInputsObservable.getCommand(c);
	}

	public List<Observable> getObservables() {
		List<Observable> observables = new LinkedList<Observable>();
		for (Turtle t : activeTurtles) {
			System.out.println("here2");
			observables.add((Observable) t);
		}
		observables.add(userInputsObservable);
//		observables.add(userInputsVariableObservable); for variable
		observables.add(displayProp);
		observables.add(response);
		// observables.add(pen);
		// observables.add(functions);
		// observables.add(variables);
		// observables.add(response);
		return observables;
	}

	public void startWorkspace() {
		Turtle firstTurtle = activeTurtles.get(0);
		firstTurtle.notifyObservers("turtle");
		displayProp.notifyObservers();
		System.out.println("here1");
	}

	/**
	 * @param s
	 */
	public void setCurrentResponse(Response s) {
		response.updateValue(s.toString());
		response.notifyObservers("Response");
	}

}