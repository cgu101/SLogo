package sharedobjects;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Observable;

public class TurtleContainer extends Observable {

	private Map<Integer, Turtle> allTurtles;
	private List<Turtle> activeTurtles;
	private List<Turtle> tempTurtles;
	private Turtle currentTurtle;

	public TurtleContainer() {
		allTurtles = new HashMap<Integer, Turtle>();
		activeTurtles = new LinkedList<Turtle>();
		tempTurtles = new LinkedList<Turtle>();
		activeTurtles.add(addNewTurtle(1));
	}

	public Turtle addNewTurtle(int id) {
		Turtle turt = new Turtle(id);
		allTurtles.put(id, turt);
		currentTurtle = turt;
		this.setChanged();
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

	public double executeOnAllActiveTurtles(ITurtleLambda lambda) {
		List<Turtle> turtles = (getTempTurtles().size() > 0) ? getTempTurtles() : getActiveTurtles();

		double response = 0;
		for (Turtle turtle : turtles) {
			response = lambda.run(turtle);
		}
		setChanged();
		notifyObservers("turtle");
		return response;
	}
	
	public double executeOnCurrentTurtle(ITurtleLambda lambda){
		Double back = lambda.run(currentTurtle); 
		setChanged();
		notifyObservers();
		return back;
	}

	public void setTempTurtles(int[] ids) {
		Map<Integer, Turtle> allTurtles = getAllTurtles();
		List<Turtle> tempTurtles = new LinkedList<Turtle>();
		for (int id = 0; id < ids.length; id++) {
			if (allTurtles.containsKey(ids[id])) {
				Turtle temp = allTurtles.get(ids[id]);
				temp.activate();
				tempTurtles.add(temp);
			} else {
				Turtle temp = addNewTurtle(ids[id]);
				tempTurtles.add(temp);
			}
			currentTurtle = tempTurtles.get(id);
		}
		setTempTurtles(tempTurtles);
		setChanged();
		notifyObservers("turtle");
	}

	public int tellTurtles(int[] ids) {
		Map<Integer, Turtle> allTurtles = getAllTurtles();
		getActiveTurtles().clear();
		List<Turtle> nextActiveList = new LinkedList<Turtle>();
		for (int id = 0; id < ids.length; id++) {
			if (allTurtles.containsKey(ids[id])) {
				Turtle temp = allTurtles.get(ids[id]);
				temp.activate();
				
				nextActiveList.add(temp);
			} else {
				Turtle temp = addNewTurtle(ids[id]);
				activeTurtles.add(temp);
				nextActiveList.add(temp);
			}
			currentTurtle = nextActiveList.get(id);
		}
		setActiveTurtles(nextActiveList);
		setChanged();
		notifyObservers("turtle");
		return ids[ids.length - 1];	
	}
	
	public void tellDuvall2Dance(){
		notifyObservers("dancingDuvall");
	}
}
