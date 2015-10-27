package sharedobjects;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

public class HandleObservation {

	// map of Observable (String name) to their Observers (List<String name>)
	private static final Map<String, List<String>> observableMap = new HashMap<String, List<String>>() {
		/**
         * 
         */
        private static final long serialVersionUID = 1L;

        {
			put("TurtleContainer", new LinkedList<String>(Arrays.asList("TurtleGroupObserver", "CanvasObserver", "TurtleStateBoxObserver")));
			put("Success", new LinkedList<String>(Arrays.asList("MessageDisplayBoxObserver")));
			put("UserInput", new LinkedList<String>(Arrays.asList("Parser")));
			put("Variables", new LinkedList<String>(Arrays.asList("FunctionVariableObserver")));
			put("Functions", new LinkedList<String>(Arrays.asList("FunctionVariableObserver")));
			put("DisplayProperties", new LinkedList<String>(Arrays.asList("CanvasObserver", "BackgroundRectangleObserver")));
		}
	};

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
    }

	//Perform Observer/Observable Linking
    private static void linkObserverableObserver(Observable observableO, Observer observerO) {
        observableO.addObserver(observerO);
        System.out.println(observerO.getClass().getSimpleName() + " : " + observableO.getClass().getSimpleName());
        System.out.println("got to link");
    }

}