// This entire file is part of my masterpiece.
// Inan Tainwala

package sharedobjects;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

public class HandleObservation {

	// map of Observable (String name) to their Observers (List<String name>)
	@SuppressWarnings("serial")
        private static final Map<String, List<String>> observableMap = new HashMap<String, List<String>>() {
                {
			put("TurtleContainer", new LinkedList<String>(Arrays.asList("TurtleGroupObserver", "CanvasObserver", "TurtleStateBoxObserver")));
			put("Success", new LinkedList<String>(Arrays.asList("MessageDisplayBoxObserver")));
			put("UserInput", new LinkedList<String>(Arrays.asList("Parser")));
			put("Variables", new LinkedList<String>(Arrays.asList("FunctionVariableObserver")));
			put("Functions", new LinkedList<String>(Arrays.asList("FunctionVariableObserver")));
			put("DisplayProperties", new LinkedList<String>(Arrays.asList("CanvasObserver", "BackgroundRectangleObserver")));
		}
	};
	
	 private static List<Observable> myObservables = new ArrayList<Observable>();
	 private static List<Observer> myObservers = new ArrayList<Observer>();
	    

	public static void handleObservers(List<Observable> observablesOList, List<Observer> observersOList) {
	        for (Observable observableO : observablesOList) {
			String observableName = observableO.getClass().getSimpleName();
			List<String> observersSList = observableMap.get(observableName);
			if (observersSList != null) {
				for (Observer observerO : observersOList) {
					String observerName = observerO.getClass().getSimpleName();
					if (observersSList.contains(observerName)) {
						linkObserverableObserver(observableO, observerO);
					}
				}
			}
		}
	        myObservables.addAll(observablesOList);
                myObservers.addAll(observersOList);
        }

	//Perform Observer/Observable Linking
        private static void linkObserverableObserver(Observable observableO, Observer observerO) {
                observableO.addObserver(observerO);
                System.out.println(observerO.getClass().getSimpleName() + " : " + observableO.getClass().getSimpleName());
                System.out.println("got to link");
        }
    
        public void registerNewObservers(String observableName, List<Observer> observersList){
                if(observableMap.containsKey(observableName)){
                    for(Observable observable : myObservables){
                        if(observable.getClass().getSimpleName().equals(observableName)){
                            for (Observer observer : observersList) {
                                observable.addObserver(observer);
                                observableMap.get(observableName).add(observer.getClass().getSimpleName());
                            }
                            myObservers.addAll(observersList);
                        }
                    }
                }
                else{
                    System.out.println("Observable not Found");
                }
        }
}